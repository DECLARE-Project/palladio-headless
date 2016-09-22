package de.fabiankeller.palladio.analysis.pcm2lqn.results;

import de.fabiankeller.palladio.analysis.result.PerformanceResultWriter;
import de.fabiankeller.palladio.analysis.result.exception.InvalidResultException;
import de.fabiankeller.palladio.analysis.tracing.PcmModelTrace;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.solver.lqn.*;
import org.palladiosimulator.solver.transformations.pcm2lqn.LqnXmlHandler;

/**
 * Parses the output of the Palladio PCM2LQN Analysis tool and maps it to the PCM instance objects.
 */
public class Pcm2LqnResultsParser {

    /**
     * parsed results file
     */
    private final LqnModelType loadedModel;

    /**
     * Stores the traces that are referred to in the results file.
     */
    private final PcmModelTrace trace;

    /**
     * Write the actual results to.
     */
    private final PerformanceResultWriter<NamedElement> rw;

    public Pcm2LqnResultsParser(final PcmModelTrace trace, final PerformanceResultWriter<NamedElement> resultWriter, final String resultsFile) {
        this.trace = trace;
        this.rw = resultWriter;

        this.loadedModel = LqnXmlHandler.loadModelFromXMI(resultsFile);
        this.extractSolverParams(this.loadedModel.getSolverParams());
        this.loadedModel.getProcessor().forEach(this::extractProcessor);
    }

    private void extractSolverParams(final SolverParamsType solverParams) {
        if (null == solverParams.getResultGeneral()) {
            throw new InvalidResultException("Missing <result-general/> tag in results file.");
        }
        if (!solverParams.getResultGeneral().isSetValid()) {
            throw new InvalidResultException("Missing <result-general valid='...'> attribute in results file.");
        }
        if (solverParams.getResultGeneral().getValid() == ValidType.NO) {
            throw new InvalidResultException("Found <result-general valid='NO'> attribute in results file, result seems not to be valid!");
        }
    }

    private void extractProcessor(final ProcessorType proc) {
        proc.getTask().forEach(this::extractTask);
    }

    private void extractTask(final TaskType task) {
        task.getEntry().forEach(this::extractEntry);
    }

    private void extractEntry(final EntryType entry) {

    }


}
