package de.fakeller.palladio.analysis.pcm2lqn.runner;

import org.junit.Assert;
import org.junit.Test;

public class PcmLqnsAnalyzerConfigTest {
    @Test
    public void defaultConfig() throws Exception {
        final PcmLqnsAnalyzerConfig cfg = PcmLqnsAnalyzerConfig.defaultConfig();
        Assert.assertTrue(cfg.getOutputPath().toString().contains("lqns-analysis"));
    }

    @Test()
    public void getOutputPath() throws Exception {
        final PcmLqnsAnalyzerConfig cfg = new PcmLqnsAnalyzerConfig();
        cfg.setOutputPath("sample");
        Assert.assertSame("sample", cfg.getOutputPath());
    }

    @Test(expected = RuntimeException.class)
    public void getOutputPath_withoutValue_throwsException() throws Exception {
        final PcmLqnsAnalyzerConfig cfg = new PcmLqnsAnalyzerConfig();
        cfg.getOutputPath();
    }

}