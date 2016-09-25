package de.fabiankeller.palladio;

import de.fabiankeller.palladio.analysis.pcm2lqn.runner.PcmLqnsAnalyzer;
import de.fabiankeller.palladio.analysis.pcm2lqn.runner.PcmLqnsAnalyzerContext;
import de.fabiankeller.palladio.analysis.provider.SimpleTacticsProvider;
import de.fabiankeller.palladio.analysis.result.PerformanceResult;
import de.fabiankeller.palladio.analysis.result.Result;
import de.fabiankeller.palladio.builder.PcmBuilder;
import de.fabiankeller.palladio.environment.PalladioEclipseEnvironment;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.solver.models.PCMInstance;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Tries to create a {@link PCMInstance} with the help of the {@link PcmBuilder}.
 */
public class RunLqnsWithBuilder {

    private static final Logger log = Logger.getLogger(RunLqnsWithBuilder.class.getName());

    public static void main(final String[] args) throws IOException {
        new RunLqnsWithBuilder().run();
    }

    public void run() {
        log.info("Launching LQNS headless");
        PalladioEclipseEnvironment.INSTANCE.setup();

        final PCMInstance instance = new SimpleTacticsProvider().provide();

        final PcmLqnsAnalyzer analyzer = new PcmLqnsAnalyzer();
        final PcmLqnsAnalyzerContext ctx = analyzer.analyze(instance);
        final PerformanceResult<NamedElement> result = ctx.run();
        ctx.untrace();

        for (final Result<? extends NamedElement> r : result.getResults()) {
            log.info(String.format("Result for '%s': %s", r.attachedTo().getEntityName(), r.value().toHumanReadable()));
        }

        // WARNING: saving the files actually removes them from the PCMResourceSetPartition! therefore the model can
        // only be saved AFTER the analysis has been performed!
        // instance.saveToFiles("palladio-headless");
    }
}
