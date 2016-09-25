package de.fabiankeller.palladio.analysis.pcm2lqn.results;

import de.fabiankeller.palladio.analysis.result.PerformanceResultWriter;
import de.fabiankeller.palladio.analysis.tracing.PcmModelTrace;
import de.fabiankeller.palladio.environment.PalladioEclipseEnvironment;
import org.junit.Before;
import org.junit.Test;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.seff.AbstractAction;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class Pcm2LqnResultsParserTest {

    public static final URL RESULTS_FILE = Thread.currentThread()
            .getContextClassLoader()
            .getResource("lqns-sample-output/simple-tactics.out.lqxo");

    @Before
    public void setUp() throws Exception {
        PalladioEclipseEnvironment.INSTANCE.setup();
    }

    @Test
    public void analyze() throws URISyntaxException {
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
        Pcm2LqnResultsParser.parse(trace, rw, Paths.get(RESULTS_FILE.toURI()));

        // lower bound of result objects being generated for the sample file
        verify(rw, atLeast(40)).attachUtilization(any(), any());
        verify(rw, atLeast(40)).attachServiceTime(any(), any());

        // throughput is not extracted yet
        verify(rw, atMost(0)).attachThroughout(any(), any());
    }
}