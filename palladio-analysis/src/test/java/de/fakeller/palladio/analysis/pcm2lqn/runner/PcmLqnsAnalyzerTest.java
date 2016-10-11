package de.fakeller.palladio.analysis.pcm2lqn.runner;

import org.junit.Before;
import org.junit.Test;
import org.palladiosimulator.solver.models.PCMInstance;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by Fabian on 25.09.2016.
 */
public class PcmLqnsAnalyzerTest {

    private PcmLqnsAnalyzer analyzer;

    @Before
    public void setUp() throws Exception {
        this.analyzer = new PcmLqnsAnalyzer();
    }

    @Test
    public void capabilities() throws Exception {
        assertTrue(this.analyzer.capabilities().size() > 0);
    }

    @Test
    public void supports() throws Exception {
        assertTrue(this.analyzer.supports(mock(PCMInstance.class)));
    }

    @Test
    public void setupAnalysis() throws Exception {
        assertNotNull(this.analyzer.setupAnalysis(mock(PCMInstance.class)));
    }

}