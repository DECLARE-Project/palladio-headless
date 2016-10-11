package de.fakeller.palladio.analysis.pcm2lqn.results;

import de.fakeller.palladio.analysis.tracing.PcmModelTrace;
import de.fakeller.palladio.environment.PalladioEclipseEnvironment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.solver.models.PCMInstance;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

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
        final Pcm2LqnResult rw = new Pcm2LqnResult(mock(PCMInstance.class));

        when(trace.find(any())).then(args -> {
            final UUID uuid = args.getArgument(0);
            if (Arrays.asList(
                    UUID.fromString("85c78b64-4931-48dd-842e-f8af14e05e87"),
                    UUID.fromString("d8b56ec3-0321-4c36-b545-a886ded56403"),
                    UUID.fromString("f008a997-befe-4dc3-bfd7-3e8132e2493a")
            ).contains(uuid)) {
                return Optional.of(mock(ResourceContainer.class));
            } else {
                return Optional.of(mock(AbstractAction.class));
            }
        });
        Pcm2LqnResultsParser.parse(trace, rw, Paths.get(RESULTS_FILE.toURI()));

        // lower bound of result objects being generated for the sample file
        Assert.assertTrue(rw.getResults().size() > 80);
    }
}