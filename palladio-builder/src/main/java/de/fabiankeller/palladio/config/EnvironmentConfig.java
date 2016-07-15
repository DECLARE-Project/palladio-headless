package de.fabiankeller.palladio.config;

import java.util.Properties;

/**
 * Settings to tweak the Eclipse Palladio environment.
 */
public class EnvironmentConfig  extends AbstractPropertiesConfig {


    private static final String PROPERTY_PCM_MODELS = "PCM_Models_Location";

    public EnvironmentConfig() {
        super();
    }

    public EnvironmentConfig(Properties config) {
        super(config);
    }

    public String getPCMModelsLocation() {
        return this.getPropertyNotNull(PROPERTY_PCM_MODELS);
    }

    public void setPCMModelsLocation(String pcmModels) {
        this.setProperty(PROPERTY_PCM_MODELS, pcmModels);
    }
}
