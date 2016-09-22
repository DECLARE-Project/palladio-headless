package de.fabiankeller.palladio.analysis.pcm2lqn.runner;

import de.fabiankeller.palladio.config.PcmModelConfig;

import java.util.Properties;

/**
 * Config parameters to locate PCM models on the file system.
 */
public class Pcm2LqnAnalysisConfig extends PcmModelConfig {

    private static final String PROPERTY_STORAGE_PATH = "Storage_Path";
    private static final String PROPERTY_OUTPUT_PATH = "Output_Path";

    public Pcm2LqnAnalysisConfig() {
        super();
    }

    public Pcm2LqnAnalysisConfig(final Properties config) {
        super(config);
    }


    public String getStoragePath() {
        return this.getPropertyNotNull(PROPERTY_STORAGE_PATH);
    }

    public void setStoragePath(final String storagePath) {
        this.setProperty(PROPERTY_STORAGE_PATH, storagePath);
    }

    public String getOutputPath() {
        return this.getPropertyNotNull(PROPERTY_OUTPUT_PATH);
    }

    public void setOutputPath(final String outputPath) {
        this.setProperty(PROPERTY_OUTPUT_PATH, outputPath);
    }
}
