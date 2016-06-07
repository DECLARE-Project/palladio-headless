package de.fabiankeller.palladio.config;

import java.util.Properties;

/**
 * Settings to tweak the Eclipse Palladio environment.
 */
public class EnvironmentConfig {
    
    private Properties config;

    private static final String PROPERTY_PCM_MODELS = "PCM_Models_Location";

    public EnvironmentConfig() {
        this(new Properties());
    }

    public EnvironmentConfig(Properties config) {
        this.config = config;
    }

    private String getPropertyNotNull(String name) {
        String value = config.getProperty(name);
        if (value == null) {
            throw new RuntimeException(String.format("Missing config key %s in EnvironmentConfig!", name));
        }
        return value;
    }

    public String getPCMModelsLocation() {
        return this.getPropertyNotNull(PROPERTY_PCM_MODELS);
    }

    public void setPCMModelsLocation(String pcmModels) {
        this.config.setProperty(PROPERTY_PCM_MODELS, pcmModels);
    }
}
