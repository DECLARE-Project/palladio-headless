package de.fabiankeller.palladio.analysis.pcm2lqn.results;

import de.fabiankeller.palladio.analysis.result.PerformanceResultWriter;
import de.fabiankeller.palladio.analysis.result.type.ServiceTime;
import de.fabiankeller.palladio.analysis.result.type.Throughput;
import de.fabiankeller.palladio.analysis.result.type.Utilization;
import de.fabiankeller.palladio.analysis.tracing.PcmModelTrace;
import de.fabiankeller.palladio.environment.PalladioEclipseEnvironment;
import org.junit.Before;
import org.junit.Test;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.seff.AbstractAction;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class Pcm2LqnResultsParserTest {

    public static final String RESULTS_FILE = Thread.currentThread()
            .getContextClassLoader()
            .getResource("lqns-sample-output/simple-tactics.out.lqxo")
            .getPath();

    @Before
    public void setUp() throws Exception {
        PalladioEclipseEnvironment.INSTANCE.setup();
    }

    @Test
    public void analyze() {
        final PcmModelTrace trace = mock(PcmModelTrace.class);
        final PerformanceResultWriter<NamedElement> rw = mock(PerformanceResultWriter.class);

        when(trace.findByString(any())).then(args -> {
            final String name = args.<String>getArgument(0);
            if (name.toLowerCase().contains("server")) {
                return Optional.of(mock(ResourceContainer.class));
            } else {
                return Optional.of(mock(AbstractAction.class));
            }
        });
        Pcm2LqnResultsParser.parse(trace, rw, RESULTS_FILE);

        // lower bound of result objects being generated for the sample file
        verify(rw, atLeast(40)).attach(any(Utilization.class));
        verify(rw, atLeast(40)).attach(any(ServiceTime.class));

        // throughput is not extracted yet
        verify(rw, atMost(0)).attach(any(Throughput.class));
    }
}