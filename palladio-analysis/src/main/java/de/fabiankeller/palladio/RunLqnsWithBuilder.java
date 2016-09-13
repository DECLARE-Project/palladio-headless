package de.fabiankeller.palladio;

import de.fabiankeller.palladio.analysis.provider.SimpleTacticsProvider;
import de.fabiankeller.palladio.analysis.runner.pcm2lqn.Pcm2LqnAnalysisConfig;
import de.fabiankeller.palladio.analysis.runner.pcm2lqn.Pcm2LqnRunner;
import de.fabiankeller.palladio.builder.PcmBuilder;
import de.fabiankeller.palladio.config.PcmModelConfig;
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
        this.runnerConfig.setProperty(PcmModelConfig.PROPERTY_USAGE_MODEL, "default.usagemodel");
        this.runnerConfig.setProperty(PcmModelConfig.PROPERTY_ALLOCATION_MODEL, "default.allocation");
        PalladioEclipseEnvironment.INSTANCE.setup();

        final PCMInstance instance = new SimpleTacticsProvider().provide();

        final Pcm2LqnRunner runner = new Pcm2LqnRunner(new Pcm2LqnAnalysisConfig(this.runnerConfig));
        runner.analyze(instance);

        // WARNING: saving the files actually removes them from the PCMResourceSetPartition! therefore the model can
        // only be saved AFTER the analysis has been performed!
        instance.saveToFiles("palladio-headless");
    }
}
