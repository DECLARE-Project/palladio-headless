package de.fabiankeller.palladio.analysis.pcm2lqn.runner;

import de.fabiankeller.palladio.analysis.AnalysisCapability;
import de.fabiankeller.palladio.analysis.AnalysisContext;
import de.fabiankeller.palladio.analysis.PerformanceAnalyzer;
import de.fabiankeller.palladio.analysis.pcm2lqn.results.Pcm2LqnResult;
import de.fabiankeller.palladio.analysis.pcm2lqn.results.Pcm2LqnResultsParser;
import de.fabiankeller.palladio.analysis.result.PerformanceResult;
import de.fabiankeller.palladio.analysis.tracing.PcmModelTrace;
import de.uka.ipd.sdq.workflow.launchconfig.AbstractWorkflowConfigurationBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.palladiosimulator.analyzer.workflow.configurations.PCMWorkflowConfigurationBuilder;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.runconfig.PCMSolverConfigurationBasedConfigBuilder;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;
import org.palladiosimulator.solver.transformations.pcm2lqn.Pcm2LqnStrategy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Runs the {@link Pcm2LqnStrategy} analysis.
 */
public class PcmLqnsAnalyzer implements PerformanceAnalyzer<PCMInstance, PcmLqnsAnalyzer.Pcm2LqnContext> {

    private static final Logger log = Logger.getLogger(PcmLqnsAnalyzer.class.getName());

    private final PcmLqnsAnalyzerConfig config;

    /**
     * Setup a default configuration for the PcmLqns analyzer.
     */
    public PcmLqnsAnalyzer() {
        this.config = new PcmLqnsAnalyzerConfig();
        final Path storage;
        try {
            storage = Files.createTempDirectory("lqns-analysis");
        } catch (final IOException e) {
            throw new RuntimeException("Cannot create temporary directory for LQNS results", e);
        }
        this.config.setStoragePath(storage.toString());
        this.config.setOutputPath(storage.toString());
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
    public Pcm2LqnContext analyze(final PCMInstance instance) {

        return new Pcm2LqnContext(instance, this.config);
    }

    class Pcm2LqnContext implements AnalysisContext<PCMInstance> {


        private final PCMInstance pcmInstance;
        private final PcmLqnsAnalyzerConfig config;

        public Pcm2LqnContext(final PCMInstance pcmInstance, final PcmLqnsAnalyzerConfig config) {
            this.pcmInstance = pcmInstance;
            this.config = config;
        }

        public PerformanceResult<NamedElement> run() {
            // setup trace
            final PcmModelTrace trace = PcmModelTrace.trace(this.pcmInstance);

            // run
            executePalladio();

            // determine results file
            final List<Path> lqnsResultsFile;
            try {
                lqnsResultsFile = Files.list(new File(this.config.getOutputPath()).toPath())
                        .filter(path -> path.toString().toLowerCase().endsWith(".out.lqxo"))
                        .collect(Collectors.toList());
            } catch (final IOException e) {
                throw new RuntimeException("Could not read results directory: " + this.config.getOutputPath(), e);
            }
            if (lqnsResultsFile.isEmpty()) {
                throw new RuntimeException("Analysis failed. No LQNS result files in directory: " + this.config.getOutputPath());
            }

            // extract result
            final Pcm2LqnResult results = new Pcm2LqnResult(this.pcmInstance);
            Pcm2LqnResultsParser.parse(trace, results, lqnsResultsFile.get(lqnsResultsFile.size() - 1));
            return results;
        }

        private void executePalladio() {
            final PCMSolverWorkflowRunConfiguration config = buildConfigWithBuilder();
            log.info("Created workflow config");

            final Pcm2LqnStrategy strategy = new Pcm2LqnStrategy(config);
            log.info("Created PCM2LQN strategy");

            strategy.transform(this.pcmInstance);
            log.info("Transformed pcm instance");

            strategy.solve();
            log.info("Solved pcm instance");

            strategy.storeTransformedModel("sample.transformed");
            log.info("Solved transformed model");
        }

        private PCMSolverWorkflowRunConfiguration buildConfigWithBuilder() {
            final PCMSolverWorkflowRunConfiguration solverConfiguration = new PCMSolverWorkflowRunConfiguration();
            final ILaunchConfiguration configuration = Pcm2LqnLaunchConfiguration.adjusted(this.config);
            try {
                AbstractWorkflowConfigurationBuilder builder;
                builder = new PCMWorkflowConfigurationBuilder(configuration, "run");
                builder.fillConfiguration(solverConfiguration);
                builder = new PCMSolverConfigurationBasedConfigBuilder(configuration, "run");
                builder.fillConfiguration(solverConfiguration);
            } catch (final CoreException e) {
                throw new RuntimeException("Could not build launch config.", e);
            }

            return solverConfiguration;
        }

        /**
         * For the {@link PcmLqnsAnalyzer} to work, the {@link PCMInstance} under analysis was modified by adding trace
         * information. Call this method to undo those changes and restore the previous state.
         */
        public void untrace() {
            PcmModelTrace.untrace(this.pcmInstance);
        }

        /**
         * Returns the PCM instance under analysis.
         */
        public PCMInstance getPcmInstance() {
            return this.pcmInstance;
        }
    }
}
