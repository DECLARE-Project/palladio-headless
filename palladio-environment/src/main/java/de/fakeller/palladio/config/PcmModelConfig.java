package de.fakeller.palladio.config;

import java.util.Properties;

/**
 * Config parameters to locate certain PCM models.
 */
public class PcmModelConfig extends AbstractPropertiesConfig {
    public static final String PROPERTY_USAGE_MODEL = "Filename_UsageModel";
    public static final String PROPERTY_ALLOCATION_MODEL = "Filename_AllocationModel";

    public PcmModelConfig() {
        super();
    }

    public PcmModelConfig(final Properties config) {
        super(config);
    }

    public String getUsageModel() {
        return this.getPropertyNotNull(PROPERTY_USAGE_MODEL);
    }

    public void setUsageModel(final String usageModel) {
        this.setProperty(PROPERTY_USAGE_MODEL, usageModel);
    }

    public String getAllocationModel() {
        return this.getPropertyNotNull(PROPERTY_ALLOCATION_MODEL);
    }

    public void setAllocationModel(final String allocationModel) {
        this.setProperty(PROPERTY_ALLOCATION_MODEL, allocationModel);
    }
}
