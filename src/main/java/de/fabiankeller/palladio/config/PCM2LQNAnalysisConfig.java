package de.fabiankeller.palladio.config;

import java.util.Properties;

/**
 * Config parameters to locate PCM models on the file system.
 */
public class PCM2LQNAnalysisConfig {
    private Properties config;

    private static final String PROPERTY_STORAGE_PATH = "Storage_Path";
    private static final String PROPERTY_OUTPUT_PATH = "Output_Path";

    public PCM2LQNAnalysisConfig() {
        this(new Properties());
    }

    public PCM2LQNAnalysisConfig(Properties config) {
        this.config = config;
    }


    public String getStoragePath() {
        return config.getProperty(PROPERTY_STORAGE_PATH);
    }

    public void setStoragePath(String storagePath) {
        config.setProperty(PROPERTY_STORAGE_PATH, storagePath);
    }

    public String getOutputPath() {
        return config.getProperty(PROPERTY_OUTPUT_PATH);
    }

    public void setOutputPath(String outputPath) {
        config.setProperty(PROPERTY_OUTPUT_PATH, outputPath);
    }
}
