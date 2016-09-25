package de.fabiankeller.palladio.analysis.pcm2lqn.runner;

import de.fabiankeller.palladio.analysis.PalladioPerformanceAnalysis;
import de.uka.ipd.sdq.workflow.launchconfig.AbstractWorkflowConfigurationBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.palladiosimulator.analyzer.workflow.configurations.PCMWorkflowConfigurationBuilder;
import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.runconfig.PCMSolverConfigurationBasedConfigBuilder;
import org.palladiosimulator.solver.runconfig.PCMSolverWorkflowRunConfiguration;
import org.palladiosimulator.solver.transformations.pcm2lqn.Pcm2LqnStrategy;

import java.util.logging.Logger;

/**
 * Runs the {@link Pcm2LqnStrategy} analysis.
 */
public class Pcm2LqnRunner implements PalladioPerformanceAnalysis {

    private static final Logger log = Logger.getLogger(Pcm2LqnRunner.class.getName());

    private final Pcm2LqnAnalysisConfig config;

    public Pcm2LqnRunner(final Pcm2LqnAnalysisConfig config) {
        this.config = config;
    }

    @Override
    public void analyze(final PCMInstance pcmInstance) {
        final PCMSolverWorkflowRunConfiguration config = buildConfigWithBuilder();
        log.info("Created workflow config");

        final Pcm2LqnStrategy strategy = new Pcm2LqnStrategy(config);
        log.info("Created PCM2LQN strategy");

        strategy.transform(pcmInstance);
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
}
