package de.fabiankeller.palladio.config;

import java.util.Properties;

/**
 * Provides a base class for {@link Properties} based config.
 */
abstract public class AbstractPropertiesConfig {
    private Properties config = new Properties();

    public AbstractPropertiesConfig() {
    }

    public AbstractPropertiesConfig(Properties config) {
        this.config = config;
    }

    protected String getProperty(String name) {
        return config.getProperty(name);
    }

    protected String getPropertyNotNull(String name) {
        String value = this.getProperty(name);
        if (value == null) {
            throw new RuntimeException(String.format("Missing config key %s in %s!", name, this.getClass().getName()));
        }
        return value;
    }

    protected void setProperty(String key, String value) {
        this.config.setProperty(key, value);
    }
}
