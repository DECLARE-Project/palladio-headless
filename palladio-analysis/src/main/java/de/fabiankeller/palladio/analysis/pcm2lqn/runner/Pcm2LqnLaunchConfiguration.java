package de.fabiankeller.palladio.analysis.pcm2lqn.runner;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Custom implementation of the launch configuration as the one's provided by Palladio are too restrictive.
 */
public class Pcm2LqnLaunchConfiguration implements ILaunchConfiguration {

    private final Map<String, Object> attr = new HashMap<>();

    private final Logger log = Logger.getLogger(Pcm2LqnLaunchConfiguration.class.getName());

    private Pcm2LqnLaunchConfiguration(final Map<String, Object> attr) {
        this.attr.putAll(attr);
        this.log.info("Using ILaunchConfiguration from " + this.getClass().getName() + " with attributes:");
        this.log.info(this.toString());
    }

    public static ILaunchConfiguration asIsFromExample() {
        final Map<String, Object> attr = new HashMap<>();
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
        return new Pcm2LqnLaunchConfiguration(attr);
    }

    public static ILaunchConfiguration adjusted(final PcmLqnsAnalyzerConfig config) {
        final Map<String, Object> attr = new HashMap<>();
        attr.put("LINEDebug", true);
        attr.put("LINEOutputDir", "/Volumes/Daten/PalladioTests/Eclipse.app/Contents/MacOS");
        attr.put("LINEPropFile", "/Volumes/Daten/PalladioTests/Eclipse.app/Contents/MacOSLINE.properties");
        attr.put("LQNS Stop On Message Loss", true);
        attr.put("LQSIM Stop On Message Loss", true);
        attr.put("SREOutputDir", "D:\\Programme\\eclipse\\eclipse-dsl-luna-RC3-win32");
        attr.put("SREUseInputModel", false);
        attr.put("accuracyQualityAnnotationFile", "");
        attr.put("allocationFile", config.getAllocationModel());
        attr.put("blocks", "");
        attr.put("clear", true);
        attr.put("convValue", "0.001");
        attr.put("de.uka.ipd.sdq.workflowengine.debuglevel", 2);
        attr.put("eventMiddlewareRepositoryFile", "pathmap://PCM_MODELS/default_event_middleware.repository");
        attr.put("infiniteTaskMultiplicity", true);
        attr.put("itLimit", "50");
        attr.put("lqnPragmas", "");
        attr.put("lqnsOutputDir", config.getOutputPath());
        attr.put("lqsimOutputDir", "D:\\Programme\\eclipse\\eclipse-dsl-luna-RC3-win32");
        attr.put("lqsimoutput", "Human Readable Output");
        attr.put("maxDomain", 256);
        attr.put("mwRepositoryFile", "pathmap://PCM_MODELS/Glassfish.repository");
        attr.put("outpath", "de.uka.ipd.sdq.temporary");
        attr.put("output", "XML Output");
        attr.put("printInt", "10");
        attr.put("psQuantum", "0.001");
        attr.put("rmiRepositoryFile", "pathmap://PCM_MODELS/Glassfish.repository");
        attr.put("runTime", "");
        attr.put("samplingDist", "1.0");
        attr.put("simulateAccuracy", false);
        attr.put("solver", "LQNS (Layered Queueing Network Solver)");
        attr.put("underCoeff", "0.5");
        attr.put("usageFile", config.getUsageModel());
        return new Pcm2LqnLaunchConfiguration(attr);
    }

    @Override
    public String toString() {
        return this.attr.entrySet().stream().map((e) -> {
            return String.format("|- %s: %s", e.getKey(), e.getValue().toString());
        }).collect(Collectors.joining("\n"));
    }

    @Override
    public boolean contentsEqual(final ILaunchConfiguration iLaunchConfiguration) {
        return iLaunchConfiguration.contentsEqual(this);
    }

    @Override
    public ILaunchConfigurationWorkingCopy copy(final String s) throws CoreException {
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

    private <T> T get(final String key, final T defaultValue) {
        final Object val = this.attr.get(key);
        if (null == val) {
            return defaultValue;
        } else {
            return (T) val;
        }
    }

    @Override
    public boolean getAttribute(final String s, final boolean b) throws CoreException {
        return this.get(s, b);
    }

    @Override
    public int getAttribute(final String s, final int i) throws CoreException {
        return this.get(s, i);
    }

    @Override
    public List<String> getAttribute(final String s, final List<String> list) throws CoreException {
        return this.get(s, list);
    }

    @Override
    public Set<String> getAttribute(final String s, final Set<String> set) throws CoreException {
        return this.get(s, set);
    }

    @Override
    public Map<String, String> getAttribute(final String s, final Map<String, String> map) throws CoreException {
        return this.get(s, map);
    }

    @Override
    public String getAttribute(final String s, final String s1) throws CoreException {
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
    public ILaunchDelegate getPreferredDelegate(final Set<String> set) throws CoreException {
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
    public boolean hasAttribute(final String s) throws CoreException {
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
    public ILaunch launch(final String s, final IProgressMonitor iProgressMonitor) throws CoreException {
        throw new RuntimeException("NYI");
    }

    @Override
    public ILaunch launch(final String s, final IProgressMonitor iProgressMonitor, final boolean b) throws CoreException {
        throw new RuntimeException("NYI");
    }

    @Override
    public ILaunch launch(final String s, final IProgressMonitor iProgressMonitor, final boolean b, final boolean b1) throws CoreException {
        throw new RuntimeException("NYI");
    }

    @Override
    public void migrate() throws CoreException {
        throw new RuntimeException("NYI");
    }

    @Override
    public boolean supportsMode(final String s) throws CoreException {
        throw new RuntimeException("NYI");
    }

    @Override
    public boolean isReadOnly() {
        throw new RuntimeException("NYI");
    }

    @Override
    public Object getAdapter(final Class aClass) {
        throw new RuntimeException("NYI");
    }
}
