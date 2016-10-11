package de.fakeller.palladio.environment.util;

import de.fakeller.palladio.environment.PCMResourceSetPartitionFactory;
import de.fakeller.palladio.environment.PalladioEclipseEnvironment;
import org.junit.Before;
import org.junit.Test;
import org.palladiosimulator.analyzer.workflow.blackboard.PCMResourceSetPartition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PalladioResourceRepositoryImplTest {

    private PalladioResourceRepository res;

    @Before
    public void setUp() throws Exception {
        // setup eclipse environment and resource repo wrapper
        PalladioEclipseEnvironment.INSTANCE.setup();
        final PCMResourceSetPartition rsp = new PCMResourceSetPartitionFactory.DefaultFactory().create();
        this.res = new PalladioResourceRepositoryImpl(rsp.getResourceTypeRepository());
    }

    @Test
    public void cpu() throws Exception {
        assertNotNull(this.res.cpu());
        assertEquals("CPU", this.res.cpu().getEntityName());
    }

    @Test
    public void hdd() throws Exception {
        assertNotNull(this.res.hdd());
        assertEquals("HDD", this.res.hdd().getEntityName());
    }

    @Test
    public void delay() throws Exception {
        assertNotNull(this.res.delay());
        assertEquals("DELAY", this.res.delay().getEntityName());
    }

    @Test
    public void lan() throws Exception {
        assertNotNull(this.res.lan());
        assertEquals("LAN", this.res.lan().getEntityName());
    }

    @Test
    public void policyDelay() throws Exception {
        assertNotNull(this.res.policyDelay());
        assertEquals("Delay", this.res.policyDelay().getEntityName());
    }

    @Test
    public void policyProcessorSharing() throws Exception {
        assertNotNull(this.res.policyProcessorSharing());
        assertEquals("Processor Sharing", this.res.policyProcessorSharing().getEntityName());
    }

    @Test
    public void policyFCFS() throws Exception {
        assertNotNull(this.res.policyFCFS());
        assertEquals("First-Come-First-Serve", this.res.policyFCFS().getEntityName());
    }

}