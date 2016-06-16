package de.fabiankeller.palladio;

import de.fabiankeller.palladio.analysis.provider.SimpleTacticsProvider;
import de.fabiankeller.palladio.analysis.runner.pcm2lqn.Pcm2LqnAnalysisConfig;
import de.fabiankeller.palladio.analysis.runner.pcm2lqn.Pcm2LqnRunner;
import de.fabiankeller.palladio.builder.PcmBuilder;
import de.fabiankeller.palladio.config.EnvironmentConfig;
import de.fabiankeller.palladio.environment.PalladioEclipseEnvironment;
import org.palladiosimulator.solver.models.PCMInstance;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Tries to create a {@link PCMInstance} with the help of the {@link PcmBuilder}.
 */
public class RunLqnsWithBuilder extends RunLQNS {

    private static final Logger log = Logger.getLogger(RunLQNS.class.getName());

    public static void main(final String[] args) throws IOException {
        final Properties runnerConfig = loadConfig(args);
        new RunLqnsWithBuilder(runnerConfig).run();
    }

    public RunLqnsWithBuilder(final Properties runnerConfig) {
        super(runnerConfig);
    }


    @Override
    public void run() {
        log.info("Launching LQNS headless");
        PalladioEclipseEnvironment.INSTANCE.setup(new EnvironmentConfig(this.runnerConfig));

        final PCMInstance instance = new SimpleTacticsProvider().provide();
        instance.saveToFiles("palladio-headless");

        final Pcm2LqnRunner runner = new Pcm2LqnRunner(new Pcm2LqnAnalysisConfig(this.runnerConfig));
        runner.analyze(instance);
    }
}
