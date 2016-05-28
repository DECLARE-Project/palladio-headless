package de.fabiankeller.palladio;

import de.uka.ipd.sdq.workflow.launchconfig.AbstractWorkflowConfigurationBuilder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.*;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.palladiosimulator.analyzer.workflow.blackboard.PCMResourceSetPartition;
import org.palladiosimulator.analyzer.workflow.configurations.AbstractPCMWorkflowRunConfiguration;
import org.palladiosimulator.analyzer.workflow.configurations.PCMWorkflowConfigurationBuilder;
import org.palladiosimulator.pcm.allocation.util.AllocationResourceFactoryImpl;
import org.palladiosimulator.pcm.core.composition.util.CompositionResourceFactoryImpl;
import org.palladiosimulator.pcm.core.entity.util.EntityResourceFactoryImpl;
import org.palladiosimulator.pcm.core.util.CoreResourceFactoryImpl;
import org.palladiosimulator.pcm.parameter.util.ParameterResourceFactoryImpl;
import org.palladiosimulator.pcm.protocol.util.ProtocolResourceFactoryImpl;
import org.palladiosimulator.pcm.qosannotations.qos_performance.util.QosPerformanceResourceFactoryImpl;
import org.palladiosimulator.pcm.qosannotations.qos_reliability.util.QosReliabilityResourceFactoryImpl;
import org.palladiosimulator.pcm.qosannotations.util.QosannotationsResourceFactoryImpl;
import org.palladiosimulator.pcm.reliability.util.ReliabilityResourceFactoryImpl;
import org.palladiosimulator.pcm.repository.util.RepositoryResourceFactoryImpl;
import org.palladiosimulator.pcm.resourceenvironment.util.ResourceenvironmentResourceFactoryImpl;
import org.palladiosimulator.pcm.resourcetype.util.ResourcetypeResourceFactoryImpl;
import org.palladiosimulator.pcm.seff.seff_performance.util.SeffPerformanceResourceFactoryImpl;
import org.palladiosimulator.pcm.seff.seff_reliability.util.SeffReliabilityResourceFactoryImpl;
import org.palladiosimulator.pcm.seff.util.SeffResourceFactoryImpl;
import org.palladiosimulator.pcm.subsystem.util.SubsystemResourceFactoryImpl;
import org.palladiosimulator.pcm.system.util.SystemResourceFactoryImpl;
import org.palladiosimulator.pcm.usagemodel.util.UsagemodelResourceFactoryImpl;
import org.palladiosimulator.pcm.util.PcmResourceFactoryImpl;
import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.runconfig.PCMSolverConfigurationBasedConfigBuilder;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;
import org.palladiosimulator.solver.transformations.pcm2lqn.Pcm2LqnStrategy;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Run the Palladio LQNS solver headless by creating a launch configuration and calling the respective API.
 */
public class RunLQNS implements Runnable {

    private static final String RUNNER_CONFIG_DEFAULT = "src/main/resources/config.properties";

    private static final String PROPERTY_STORAGE_PATH = "Storage_Path";
    private static final String PROPERTY_USAGE_MODEL = "Filename_UsageModel";
    private static final String PROPERTY_ALLOCATION_MODEL = "Filename_AllocationModel";
    private static final String PROPERTY_OUTPUT_PATH = "Output_Path";

    public static final String PCM_MODELS = "PCM_Models";

    private static final Logger log = Logger.getLogger(RunLQNS.class.getName());

    /**
     * Stores all configuration required by this runner
     */
    private final Properties runnerConfig;

    public static void main(String[] args) throws IOException {
        Properties runnerConfig = new Properties();
        String configFile = RUNNER_CONFIG_DEFAULT;
        if (args.length > 0) {
            configFile = args[0];
        }
        log.info("Loading runner configuration from: " + configFile);

        try {
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(configFile));
            runnerConfig.load(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.severe("Could not load runner config: " + e.getMessage());
            throw new RuntimeException(e);
        }

        new RunLQNS(runnerConfig).run();
    }

    public RunLQNS(Properties runnerConfig) {
        this.runnerConfig = runnerConfig;
    }

    /**
     * {@inheritDoc}
     */
    public void run() {
        log.info("Launching LQNS headless");
        initEmfFactories();

        PCMResourceSetPartition resourceSetPartition = new PCMResourceSetPartition();
        resourceSetPartition.getResourceSet().setURIConverter(new ExtensibleURIConverterImpl() {
            @Override
            public org.eclipse.emf.common.util.URI normalize(org.eclipse.emf.common.util.URI uri) {
                org.eclipse.emf.common.util.URI normalized = doNormalize(uri);
                log.info(String.format("Normalize uri '%s' to '%s'", uri, normalized));
                return normalized;
            }

            private org.eclipse.emf.common.util.URI doNormalize(org.eclipse.emf.common.util.URI uri) {
                if (uri.toString().startsWith("pathmap://")) {
                    String unPrefixed = uri.toString().replace("pathmap://", "");
                    if (unPrefixed.startsWith("PCM_MODELS/")) {
                        org.eclipse.emf.common.util.URI converted = org.eclipse.emf.common.util.URI.createURI(unPrefixed.replace("PCM_MODELS/", runnerConfig.getProperty(PCM_MODELS)));
                        if (!new java.io.File(converted.toString()).exists()) {
                            log.warning("Normalized URI is not a file: " + converted);
                        }
                        return converted;
                    }
                    throw new RuntimeException("Unknown pathmap: " + uri);
                }
                return super.normalize(uri);
            }
        });
        try {
            Field field = ExtensibleURIConverterImpl.class.getField("INSTANCE");
            field.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, resourceSetPartition.getResourceSet().getURIConverter());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        ArrayList<String> fileList = new ArrayList<String>();
        fileList.add(runnerConfig.getProperty("Filename_UsageModel"));
        fileList.add(runnerConfig.getProperty("Filename_AllocationModel"));
        resourceSetPartition.initialiseResourceSetEPackages(AbstractPCMWorkflowRunConfiguration.PCM_EPACKAGES);
        for (String modelFile : fileList) {
            resourceSetPartition.loadModel(modelFile);
        }
        resourceSetPartition.resolveAllProxies();

//        Map<String, Object> protocolToFactoryMap = Resource.Factory.Registry.INSTANCE.getProtocolToFactoryMap();
//        protocolToFactoryMap.put("file", )

        PCMInstance pcmInstance = new PCMInstance(resourceSetPartition);
        log.info("Created PCM Instance");

        PCMSolverWorkflowRunConfiguration config = buildConfigWithBuilder();
        log.info("Created workflow config");

        Pcm2LqnStrategy strategy = new Pcm2LqnStrategy(config);
        log.info("Created PCM2LQN strategy");

        strategy.transform(pcmInstance);
        log.info("Transformed pcm instance");

        strategy.solve();
        log.info("Solved pcm instance");

        strategy.storeTransformedModel("sample.transformed");
        log.info("Solved transformed model");
    }


    private PCMSolverWorkflowRunConfiguration buildConfigWithBuilder() {
        PCMSolverWorkflowRunConfiguration solverConfiguration = new PCMSolverWorkflowRunConfiguration();
        ILaunchConfiguration configuration = CustomLaunchConfiguration.adjusted(this.runnerConfig);
        try {
            AbstractWorkflowConfigurationBuilder builder;
            builder = new PCMWorkflowConfigurationBuilder(configuration, "run");
            builder.fillConfiguration(solverConfiguration);
            builder = new PCMSolverConfigurationBasedConfigBuilder(configuration, "run");
            builder.fillConfiguration(solverConfiguration);
        } catch (CoreException e) {
            e.printStackTrace();
        }

        return solverConfiguration;
    }


    /**
     * Manually register factories for EMF.
     */
    private static void initEmfFactories() {
        for (final EPackage ePackage : AbstractPCMWorkflowRunConfiguration.PCM_EPACKAGES) {
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(ePackage.getNsURI(), ePackage);
        }
        log.info("Initialized EMF factories");

        // register factories: http://wiki.eclipse.org/EMF/FAQ#How_do_I_use_EMF_in_standalone_applications_.28such_as_an_ordinary_main.29.3F
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("pcm", new PcmResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("core", new CoreResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("entity", new EntityResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("composition", new CompositionResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("usagemodel", new UsagemodelResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("repository", new RepositoryResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("resourcetype", new ResourcetypeResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("protocol", new ProtocolResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("parameter", new ParameterResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("reliability", new ReliabilityResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("seff", new SeffResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("seffperformance", new SeffPerformanceResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("seffreliability", new SeffReliabilityResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("qosannotations", new QosannotationsResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("qosperformance", new QosPerformanceResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("qosreliability", new QosReliabilityResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("system", new SystemResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("resourceenvironment", new ResourceenvironmentResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("allocation", new AllocationResourceFactoryImpl());
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("subsystem", new SubsystemResourceFactoryImpl());
        log.info("Initialized resource factories");
    }


    private static class CustomLaunchConfiguration implements ILaunchConfiguration {

        private Map<String, Object> attr = new HashMap<>();

        private Logger log = Logger.getLogger(CustomLaunchConfiguration.class.getName());

        public CustomLaunchConfiguration(Map<String, Object> attr) {
            this.attr = attr;
            log.info("Using ILaunchConfiguration from " + this.getClass().getName() + " with attributes:");
            log.info(this.toString());
        }

        public static ILaunchConfiguration asIsFromExample() {
            Map<String, Object> attr = new HashMap<>();
            attr.put("LINEDebug", true);
            attr.put("LINEOutputDir", "/Volumes/Daten/PalladioTests/Eclipse.app/Contents/MacOS");
            attr.put("LINEPropFile", "/Volumes/Daten/PalladioTests/Eclipse.app/Contents/MacOSLINE.properties");
            attr.put("LQNS Stop On Message Loss", true);
            attr.put("LQSIM Stop On Message Loss", true);
            attr.put("SREOutputDir", "D:\\Programme\\eclipse\\eclipse-dsl-luna-RC3-win32");
            attr.put("SREUseInputModel", false);
            attr.put("accuracyQualityAnnotationFile", "");
            attr.put("allocationFile", "platform:/resource/Simple Tactics Example/default.allocation");
            attr.put("blocks", "");
            attr.put("clear", true);
            attr.put("convValue", "0.001");
            attr.put("de.uka.ipd.sdq.workflowengine.debuglevel", 2);
            attr.put("eventMiddlewareRepositoryFile", "pathmap://PCM_MODELS/default_event_middleware.repository");
            attr.put("infiniteTaskMultiplicity", true);
            attr.put("itLimit", "50");
            attr.put("lqnPragmas", "");
            attr.put("lqnsOutputDir", "/Volumes/Daten/PalladioTests/workspace/Output");
            attr.put("lqsimOutputDir", "D:\\Programme\\eclipse\\eclipse-dsl-luna-RC3-win32");
            attr.put("lqsimoutput", "Human Readable Output");
            attr.put("maxDomain", 256);
            attr.put("mwRepositoryFile", "pathmap://PCM_MODELS/Glassfish.repository");
            attr.put("outpath", "de.uka.ipd.sdq.temporary");
            attr.put("output", "Human Readable Output");
            attr.put("printInt", "10");
            attr.put("psQuantum", "0.001");
            attr.put("rmiRepositoryFile", "pathmap://PCM_MODELS/Glassfish.repository");
            attr.put("runTime", "");
            attr.put("samplingDist", "1.0");
            attr.put("simulateAccuracy", false);
            attr.put("solver", "LQNS (Layered Queueing Network Solver)");
            attr.put("underCoeff", "0.5");
            attr.put("usageFile", "platform:/resource/Simple Tactics Example/default.usagemodel");
            return new CustomLaunchConfiguration(attr);
        }

        public static ILaunchConfiguration adjusted(Properties runnerConfig) {
            Map<String, Object> attr = new HashMap<>();
            attr.put("LINEDebug", true);
            attr.put("LINEOutputDir", "/Volumes/Daten/PalladioTests/Eclipse.app/Contents/MacOS");
            attr.put("LINEPropFile", "/Volumes/Daten/PalladioTests/Eclipse.app/Contents/MacOSLINE.properties");
            attr.put("LQNS Stop On Message Loss", true);
            attr.put("LQSIM Stop On Message Loss", true);
            attr.put("SREOutputDir", "D:\\Programme\\eclipse\\eclipse-dsl-luna-RC3-win32");
            attr.put("SREUseInputModel", false);
            attr.put("accuracyQualityAnnotationFile", "");
            attr.put("allocationFile", runnerConfig.getProperty(PROPERTY_ALLOCATION_MODEL));
            attr.put("blocks", "");
            attr.put("clear", true);
            attr.put("convValue", "0.001");
            attr.put("de.uka.ipd.sdq.workflowengine.debuglevel", 2);
            attr.put("eventMiddlewareRepositoryFile", "pathmap://PCM_MODELS/default_event_middleware.repository");
            attr.put("infiniteTaskMultiplicity", true);
            attr.put("itLimit", "50");
            attr.put("lqnPragmas", "");
            attr.put("lqnsOutputDir", runnerConfig.getProperty(PROPERTY_OUTPUT_PATH));
            attr.put("lqsimOutputDir", "D:\\Programme\\eclipse\\eclipse-dsl-luna-RC3-win32");
            attr.put("lqsimoutput", "Human Readable Output");
            attr.put("maxDomain", 256);
            attr.put("mwRepositoryFile", "pathmap://PCM_MODELS/Glassfish.repository");
            attr.put("outpath", "de.uka.ipd.sdq.temporary");
            attr.put("output", "Human Readable Output");
            attr.put("printInt", "10");
            attr.put("psQuantum", "0.001");
            attr.put("rmiRepositoryFile", "pathmap://PCM_MODELS/Glassfish.repository");
            attr.put("runTime", "");
            attr.put("samplingDist", "1.0");
            attr.put("simulateAccuracy", false);
            attr.put("solver", "LQNS (Layered Queueing Network Solver)");
            attr.put("underCoeff", "0.5");
            attr.put("usageFile", runnerConfig.getProperty(PROPERTY_USAGE_MODEL));
            return new CustomLaunchConfiguration(attr);
        }

        @Override
        public String toString() {
            return this.attr.entrySet().stream().map((e) -> {
                return String.format("|- %s: %s", e.getKey(), e.getValue().toString());
            }).collect(Collectors.joining("\n"));
        }

        @Override
        public boolean contentsEqual(ILaunchConfiguration iLaunchConfiguration) {
            return iLaunchConfiguration.contentsEqual(this);
        }

        @Override
        public ILaunchConfigurationWorkingCopy copy(String s) throws CoreException {
            throw new RuntimeException("NYI");
        }

        @Override
        public void delete() throws CoreException {
            throw new RuntimeException("NYI");
        }

        @Override
        public boolean exists() {
            return false;
        }

        private <T> T get(String key, T defaultValue) {
            Object val = this.attr.get(key);
            if (null == val) {
                return defaultValue;
            } else {
                return (T) val;
            }
        }

        @Override
        public boolean getAttribute(String s, boolean b) throws CoreException {
            return this.get(s, b);
        }

        @Override
        public int getAttribute(String s, int i) throws CoreException {
            return this.get(s, i);
        }

        @Override
        public List<String> getAttribute(String s, List<String> list) throws CoreException {
            return this.get(s, list);
        }

        @Override
        public Set<String> getAttribute(String s, Set<String> set) throws CoreException {
            return this.get(s, set);
        }

        @Override
        public Map<String, String> getAttribute(String s, Map<String, String> map) throws CoreException {
            return this.get(s, map);
        }

        @Override
        public String getAttribute(String s, String s1) throws CoreException {
            return this.get(s, s1);
        }

        @Override
        public Map<String, Object> getAttributes() throws CoreException {
            return this.attr;
        }

        @Override
        public String getCategory() throws CoreException {
            throw new RuntimeException("NYI");
        }

        @Override
        public IFile getFile() {
            throw new RuntimeException("NYI");
        }

        @Override
        public IPath getLocation() {
            throw new RuntimeException("NYI");
        }

        @Override
        public IResource[] getMappedResources() throws CoreException {
            throw new RuntimeException("NYI");
        }

        @Override
        public String getMemento() throws CoreException {
            throw new RuntimeException("NYI");
        }

        @Override
        public String getName() {
            throw new RuntimeException("NYI");
        }

        @Override
        public Set<String> getModes() throws CoreException {
            throw new RuntimeException("NYI");
        }

        @Override
        public ILaunchDelegate getPreferredDelegate(Set<String> set) throws CoreException {
            throw new RuntimeException("NYI");
        }

        @Override
        public ILaunchConfigurationType getType() throws CoreException {
            throw new RuntimeException("NYI");
        }

        @Override
        public ILaunchConfigurationWorkingCopy getWorkingCopy() throws CoreException {
            throw new RuntimeException("NYI");
        }

        @Override
        public boolean hasAttribute(String s) throws CoreException {
            return this.attr.containsKey(s);
        }

        @Override
        public boolean isLocal() {
            throw new RuntimeException("NYI");
        }

        @Override
        public boolean isMigrationCandidate() throws CoreException {
            throw new RuntimeException("NYI");
        }

        @Override
        public boolean isWorkingCopy() {
            throw new RuntimeException("NYI");
        }

        @Override
        public ILaunch launch(String s, IProgressMonitor iProgressMonitor) throws CoreException {
            throw new RuntimeException("NYI");
        }

        @Override
        public ILaunch launch(String s, IProgressMonitor iProgressMonitor, boolean b) throws CoreException {
            throw new RuntimeException("NYI");
        }

        @Override
        public ILaunch launch(String s, IProgressMonitor iProgressMonitor, boolean b, boolean b1) throws CoreException {
            throw new RuntimeException("NYI");
        }

        @Override
        public void migrate() throws CoreException {
            throw new RuntimeException("NYI");
        }

        @Override
        public boolean supportsMode(String s) throws CoreException {
            throw new RuntimeException("NYI");
        }

        @Override
        public boolean isReadOnly() {
            throw new RuntimeException("NYI");
        }

        @Override
        public Object getAdapter(Class aClass) {
            throw new RuntimeException("NYI");
        }
    }
}
