package de.fabiankeller.palladio;

import de.fabiankeller.palladio.analysis.PcmProvider;
import de.fabiankeller.palladio.analysis.provider.FileSystemProvider;
import de.fabiankeller.palladio.analysis.runner.pcm2lqn.Pcm2LqnAnalysisConfig;
import de.fabiankeller.palladio.analysis.runner.pcm2lqn.Pcm2LqnRunner;
import de.fabiankeller.palladio.config.PcmModelConfig;
import de.fabiankeller.palladio.environment.PalladioEclipseEnvironment;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Run the Palladio LQNS solver headless by creating a launch configuration and calling the respective API.
 */
public class RunLQNS implements Runnable {

    protected static final String RUNNER_CONFIG_DEFAULT = "palladio-analysis/src/main/resources/config.properties";

    protected static final Logger log = Logger.getLogger(RunLQNS.class.getName());

    /**
     * Stores all configuration required by this runner
     */
    protected final Properties runnerConfig;

    public static void main(final String[] args) throws IOException {
        final Properties runnerConfig = loadConfig(args);
        new RunLQNS(runnerConfig).run();
    }

    protected static Properties loadConfig(final String[] args) {
        final Properties runnerConfig = new Properties();
        String configFile = RUNNER_CONFIG_DEFAULT;
        if (args.length > 0) {
            configFile = args[0];
        }
        log.info("Loading runner configuration from: " + configFile);

        try {
            final BufferedInputStream stream = new BufferedInputStream(new FileInputStream(configFile));
            runnerConfig.load(stream);
            stream.close();
            return runnerConfig;
        } catch (final IOException e) {
            e.printStackTrace();
            log.severe("Could not load runner config: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public RunLQNS(final Properties runnerConfig) {
        this.runnerConfig = runnerConfig;
    }

    /**
     * {@inheritDoc}
     */
    public void run() {
        log.info("Launching LQNS headless");
        PalladioEclipseEnvironment.INSTANCE.setup();

        final PcmProvider provider = new FileSystemProvider(new PcmModelConfig(this.runnerConfig));
        final Pcm2LqnRunner runner = new Pcm2LqnRunner(new Pcm2LqnAnalysisConfig(this.runnerConfig));
        runner.analyze(provider.provide());
    }
}
