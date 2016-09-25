package de.fabiankeller.palladio.analysis.pcm2lqn.runner;

import de.fabiankeller.palladio.analysis.AnalysisCapability;
import de.fabiankeller.palladio.analysis.PerformanceAnalyzer;
import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.transformations.pcm2lqn.Pcm2LqnStrategy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Runs the {@link Pcm2LqnStrategy} analysis.
 */
public class PcmLqnsAnalyzer implements PerformanceAnalyzer<PCMInstance, PcmLqnsAnalyzerContext> {

    private static final Logger log = Logger.getLogger(PcmLqnsAnalyzer.class.getName());

    private final PcmLqnsAnalyzerConfig config;

    /**
     * Setup a default configuration for the PcmLqns analyzer.
     */
    public PcmLqnsAnalyzer() {
        this(PcmLqnsAnalyzerConfig.defaultConfig());
    }

    /**
     * Set a specific configuration.
     */
    public PcmLqnsAnalyzer(final PcmLqnsAnalyzerConfig config) {
        this.config = config;
    }

    @Override
    public Set<AnalysisCapability> capabilities() {
        return new HashSet<>(Arrays.asList(AnalysisCapability.FAST_EXECUTION));
    }

    @Override
    public boolean supports(final PCMInstance instance) {
        return true;
    }

    @Override
    public PcmLqnsAnalyzerContext analyze(final PCMInstance instance) {
        log.info(String.format("Setup PcmLqnsAnalyzerContext for PCMInstance '%s'", instance.toString()));
        return new PcmLqnsAnalyzerContext(instance, this.config);
    }

}
