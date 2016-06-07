package de.fabiankeller.palladio;

import de.fabiankeller.palladio.analysis.PcmProvider;
import de.fabiankeller.palladio.analysis.provider.FileSystemProvider;
import de.fabiankeller.palladio.analysis.runner.pcm2lqn.Pcm2LqnAnalysisConfig;
import de.fabiankeller.palladio.analysis.runner.pcm2lqn.Pcm2LqnRunner;
import de.fabiankeller.palladio.config.EnvironmentConfig;
import de.fabiankeller.palladio.config.PcmModelConfig;
import de.fabiankeller.palladio.environment.PalladioEclipseEnvironment;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Run the Palladio LQNS solver headless by creating a launch configuration and calling the respective API.
 */
public class RunLQNS implements Runnable {

    private static final String RUNNER_CONFIG_DEFAULT = "src/main/resources/config.properties";

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
        PalladioEclipseEnvironment.INSTANCE.setup(new EnvironmentConfig(this.runnerConfig));

        PcmProvider provider = new FileSystemProvider(new PcmModelConfig(this.runnerConfig));
        Pcm2LqnRunner runner = new Pcm2LqnRunner(new Pcm2LqnAnalysisConfig(this.runnerConfig));
        runner.analyze(provider.provide());
    }
}
