package de.fakeller.palladio.config;

import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

/**
 * Created by Fabian on 25.09.2016.
 */
public class PcmModelConfigTest {

    @Test
    public void getAllocationModel() throws Exception {
        final Properties properties = new Properties();
        properties.setProperty(PcmModelConfig.PROPERTY_ALLOCATION_MODEL, "alloc1234");
        Assert.assertSame("alloc1234", new PcmModelConfig(properties).getAllocationModel());
    }

    @Test
    public void getUsageModel() throws Exception {
        final Properties properties = new Properties();
        properties.setProperty(PcmModelConfig.PROPERTY_USAGE_MODEL, "usage1234");
        Assert.assertSame("usage1234", new PcmModelConfig(properties).getUsageModel());
    }

    @Test(expected = RuntimeException.class)
    public void getUsageModel_withoutValue_throwsException() throws Exception {
        final PcmModelConfig cfg = new PcmModelConfig();
        cfg.getUsageModel();
    }

    @Test(expected = RuntimeException.class)
    public void getAllocationModel_withoutValue_throwsException() throws Exception {
        final PcmModelConfig cfg = new PcmModelConfig();
        cfg.getAllocationModel();
    }

}