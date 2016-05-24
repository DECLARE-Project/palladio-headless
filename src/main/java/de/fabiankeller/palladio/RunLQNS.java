package de.fabiankeller.palladio;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.analyzer.workflow.configurations.AbstractPCMWorkflowRunConfiguration;
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
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;
import org.palladiosimulator.solver.transformations.pcm2lqn.Pcm2LqnStrategy;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Run the Palladio LQNS solver headless by creating a launch configuration and calling the respective API.
 */
public class RunLQNS implements Runnable {

    private static final String RUNNER_CONFIG_DEFAULT = "src/main/resources/config.properties";

    private static final String PROPERTY_STORAGE_PATH = "Storage_Path";
    private static final String PROPERTY_USAGE_MODEL = "Filename_UsageModel";
    private static final String PROPERTY_ALLOCATION_MODEL = "Filename_AllocationModel";
    private static final String PROPERTY_OUTPUT_PATH = "Output_Path";

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

        PCMInstance pcmInstance = new PCMInstance(this.runnerConfig);
        log.info("Created PCM Instance");

        PCMSolverWorkflowRunConfiguration config = buildConfig();
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

    /**
     * Creates the launch configuration for the PCM Solver.
     */
    private PCMSolverWorkflowRunConfiguration buildConfig() {
        PCMSolverWorkflowRunConfiguration config = new PCMSolverWorkflowRunConfiguration();

        config.setStopOnMessageLossLQNS(true);
        List<String> allocationFiles = new ArrayList<>();
        allocationFiles.add(this.runnerConfig.getProperty(this.runnerConfig.getProperty(PROPERTY_ALLOCATION_MODEL)));
        config.setAllocationFiles(allocationFiles);
        config.setConvValue("0.001");
        config.setDebugLevel(2);
        config.setInfiniteTaskMultiplicity(true);
        config.setItLimit("50");
        config.setLqnsOutputDir(this.runnerConfig.getProperty(PROPERTY_OUTPUT_PATH));
        config.setPrintInt("10");
        config.setPsQuantum("0.001");
        config.setSolver("LQNS (Layered Queueing Network Solver)");
        config.setUnderCoeff("0.5");
        config.setUsageModelFile(this.runnerConfig.getProperty(PROPERTY_USAGE_MODEL));
        return config;
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
}
