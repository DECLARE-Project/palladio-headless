package de.fabiankeller.palladio.config;

import java.util.Properties;

/**
 * Config parameters to locate certain PCM models.
 */
public class PcmModelConfig extends AbstractPropertiesConfig {
    private static final String PROPERTY_USAGE_MODEL = "Filename_UsageModel";
    private static final String PROPERTY_ALLOCATION_MODEL = "Filename_AllocationModel";

    public PcmModelConfig() {
        super();
    }

    public PcmModelConfig(Properties config) {
        super(config);
    }

    public String getUsageModel() {
        return this.getPropertyNotNull(PROPERTY_USAGE_MODEL);
    }

    public void setUsageModel(String usageModel) {
        this.setProperty(PROPERTY_USAGE_MODEL, usageModel);
    }

    public String getAllocationModel() {
        return this.getPropertyNotNull(PROPERTY_ALLOCATION_MODEL);
    }

    public void setAllocationModel(String allocationModel) {
        this.setProperty(PROPERTY_ALLOCATION_MODEL, allocationModel);
    }
}
