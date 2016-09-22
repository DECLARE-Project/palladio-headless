package de.fabiankeller.palladio.analysis.tracing;

import de.fabiankeller.palladio.builder.PcmBuilder;
import de.fabiankeller.palladio.environment.PalladioEclipseEnvironment;
import org.junit.Before;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.solver.models.PCMInstance;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.Assert.*;

public class PcmModelTraceTest {

    private PCMInstance pcm;

    @Before
    public void setUp() throws Exception {
        PalladioEclipseEnvironment.INSTANCE.setup();

        final PcmBuilder builder = new PcmBuilder();
        builder.repository()
                .withComponent("SampleComponent")
                .end()
                .withInterface("SampleInterface");
        builder.resourceEnvironment()
                .createContainer("CPU1");
        this.pcm = builder.build();

    }

    @Test
    public void trace() throws Exception {
        final RepositoryComponent cmp = this.pcm.getRepositories().get(0).getComponents__Repository().get(0);
        final ResourceContainer cpu = this.pcm.getResourceEnvironment().getResourceContainer_ResourceEnvironment().get(0);

        assertEquals("SampleComponent", cmp.getEntityName());
        assertEquals("CPU1", cpu.getEntityName());

        final PcmModelTrace trace = PcmModelTrace.trace(this.pcm);

        assertNotEquals("SampleComponent", cmp.getEntityName());
        assertTrue(cmp.getEntityName().contains("__TRACE["));
        assertNotEquals("CPU", cpu.getEntityName());
    }

    @Test
    public void load() throws Exception {
        final RepositoryComponent cmp = this.pcm.getRepositories().get(0).getComponents__Repository().get(0);
        final ResourceContainer cpu = this.pcm.getResourceEnvironment().getResourceContainer_ResourceEnvironment().get(0);
        PcmModelTrace.trace(this.pcm);

        final PcmModelTrace trace = PcmModelTrace.load(this.pcm);

        assertSame(cmp, trace.find(PcmModelTrace.extractTrace(cmp.getEntityName())));
        assertSame(cpu, trace.find(PcmModelTrace.extractTrace(cpu.getEntityName())));
    }

    @Test
    public void untrace() throws Exception {
        final RepositoryComponent cmp = this.pcm.getRepositories().get(0).getComponents__Repository().get(0);
        final ResourceContainer cpu = this.pcm.getResourceEnvironment().getResourceContainer_ResourceEnvironment().get(0);
        final PcmModelTrace trace = PcmModelTrace.trace(this.pcm);

        assertNotEquals("SampleComponent", cmp.getEntityName());
        assertNotEquals("CPU", cpu.getEntityName());

        PcmModelTrace.untrace(this.pcm);

        assertEquals("SampleComponent", cmp.getEntityName());
        assertEquals("CPU1", cpu.getEntityName());

    }

    @Test
    public void extractTrace() throws Exception {
        assertEquals(UUID.fromString("85c78b64-4931-48dd-842e-f8af14e05e87"), PcmModelTrace.extractTrace("Server1__TRACE[85c78b64-4931-48dd-842e-f8af14e05e87]_CPU_Processor"));
        assertNull(PcmModelTrace.extractTrace("Server1__TRACE[invalid]_CPU_Processor"));
        assertNull(PcmModelTrace.extractTrace("Server1__CPU_Processor_NOTRACE"));
    }

    @Test
    public void find() throws Exception {
        final RepositoryComponent cmp = this.pcm.getRepositories().get(0).getComponents__Repository().get(0);
        final ResourceContainer cpu = this.pcm.getResourceEnvironment().getResourceContainer_ResourceEnvironment().get(0);
        final PcmModelTrace trace = PcmModelTrace.trace(this.pcm);

        assertSame(cmp, trace.find(PcmModelTrace.extractTrace(cmp.getEntityName())));
        assertSame(cpu, trace.find(PcmModelTrace.extractTrace(cpu.getEntityName())));
    }

    @Test(expected = NoSuchElementException.class)
    public void find_nonExistingUUID_throwsException() throws Exception {
        final PcmModelTrace trace = PcmModelTrace.trace(this.pcm);
        trace.find(UUID.randomUUID());
    }


}