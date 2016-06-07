package de.fabiankeller.palladio.config;

import java.util.Properties;

/**
 * Config parameters to locate PCM models on the file system.
 */
public class LoadPcmFromFileSystemConfig {
    private Properties config;

    private static final String PROPERTY_USAGE_MODEL = "Filename_UsageModel";
    private static final String PROPERTY_ALLOCATION_MODEL = "Filename_AllocationModel";

    public LoadPcmFromFileSystemConfig() {
        this(new Properties());
    }

    public LoadPcmFromFileSystemConfig(Properties config) {
        this.config = config;
    }
    
    public String getUsageModel() {
        return config.getProperty(PROPERTY_USAGE_MODEL);
    }

    public void setUsageModel(String usageModel) {
        config.setProperty(PROPERTY_USAGE_MODEL, usageModel);
    }

    public String getAllocationModel() {
        return config.getProperty(PROPERTY_ALLOCATION_MODEL);
    }

    public void setAllocationModel(String allocationModel) {
        config.setProperty(PROPERTY_ALLOCATION_MODEL, allocationModel);
    }
}
