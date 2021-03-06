package de.fakeller.palladio.analysis.pcm2lqn.results;

import de.fakeller.palladio.analysis.tracing.PcmModelTrace;
import de.fakeller.performance.analysis.result.Attach;
import de.fakeller.performance.analysis.result.PerformanceResultWriter;
import de.fakeller.performance.analysis.result.exception.InvalidResultException;
import de.fakeller.performance.analysis.result.valueobject.Duration;
import de.fakeller.performance.analysis.result.valueobject.Percentage;
import de.fakeller.performance.analysis.result.valueobject.statistics.Variance;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.solver.lqn.*;
import org.palladiosimulator.solver.transformations.pcm2lqn.LqnXmlHandler;

import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Parses the output of the Palladio PCM2LQN Analysis tool and maps it to the PCM instance objects.
 */
public class Pcm2LqnResultsParser {

    private static final Logger log = Logger.getLogger(Pcm2LqnResultsParser.class.getName());

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

    private Pcm2LqnResultsParser(final PcmModelTrace trace, final PerformanceResultWriter<NamedElement> resultWriter, final Path resultsFile) {
        this.trace = trace;
        this.rw = resultWriter;

        this.loadedModel = LqnXmlHandler.loadModelFromXMI(resultsFile.toString());
        this.extractSolverParams(this.loadedModel.getSolverParams());
        this.loadedModel.getProcessor().forEach(this::extractProcessor);
    }

    /**
     * Parses the LQNS results file containing trace information with the help of the given {@link PcmModelTrace} and
     * stores all performance prediction results in the {@link PerformanceResultWriter}.
     */
    public static void parse(final PcmModelTrace trace, final PerformanceResultWriter<NamedElement> resultWriter, final Path resultsFile) {
        new Pcm2LqnResultsParser(trace, resultWriter, resultsFile);
    }

    /**
     * Sanity checks for results file
     */
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
        // in case the processor is a {@link ResourceContainer}, we'll attach the utilization result
        if (proc.getResultProcessor().size() == 1) {
            this.<ResourceContainer>traceElement(proc.getName(), ResourceContainer.class).ifPresent(resourceContainer -> {
                final OutputResultType result = proc.getResultProcessor().get(0);
                this.rw.attach(Attach.<NamedElement>to(resourceContainer).utilization(Percentage.of(result.getUtilization())).mean());
            });
        }

        // extract tasks
        proc.getTask().forEach(this::extractTask);
    }

    private void extractTask(final TaskType task) {
        task.getEntry().forEach(this::extractEntry);
        if (null != task.getTaskActivities()) {
            task.getTaskActivities().getActivity().forEach(this::extractActivity);
        }
    }

    private void extractEntry(final EntryType entry) {
        final PhaseActivities phaseActivities = entry.getEntryPhaseActivities();
        if (null != phaseActivities) {
            phaseActivities.getActivity().forEach(this::extractActivity);
        }
    }

    private void extractActivity(final ActivityDefBase activity) {
        if (activity.getResultActivity().size() == 1) {
            // currently we only know the semantics of a single <result-activity>
            this.extractActivityResult(activity, activity.getResultActivity().get(0));
        }
    }

    private void extractActivityResult(final ActivityDefBase activity, final OutputResultType result) {
        assert null != result;

        // get the action the result applies to
        final Optional<AbstractAction> optionalAction = this.<AbstractAction>traceElement(activity.getName(), AbstractAction.class);
        if (!optionalAction.isPresent()) {
            return;
        }
        final AbstractAction action = optionalAction.get();

        // extract utilization
        if (result.isSetUtilization()) {
            this.rw.attach(Attach.<NamedElement>to(action).utilization(Percentage.of(result.getUtilization())).mean());
        }

        // extract service time
        if (result.isSetServiceTime()) {
            final Duration serviceTime = Duration.ofMilliseconds(result.getServiceTime());
            if (result.isSetServiceTimeVariance()) {
                final Variance serviceTimeVariance = new Variance(result.getServiceTimeVariance());
                this.rw.attach(Attach.<NamedElement>to(action).serviceTime(serviceTime).normalDistribution(serviceTimeVariance));
            } else {
                this.rw.attach(Attach.<NamedElement>to(action).serviceTime(serviceTime).mean());
            }
        }

        /*
            TODO: extract the following result types:
            - proc-waiting
            - throughput
            - throughput-bound
            - proc-utilization
            - proc-waiting
         */
    }


    /**
     * Calls {@link PcmModelTrace::findByString} with the given name and tries to cast the found element to the given
     * class.
     * <p>
     * Note: If the cast is not successful, an empty optional will be returned.
     *
     * @throws NoSuchElementException in case the trace does not contain an element identified by the given name
     */
    private <T extends NamedElement> Optional<T> traceElement(final String name, final Class<T> tClass) {
        // check if trace info is present
        final Optional<UUID> uuid = PcmModelTrace.extractTrace(name);
        if (!uuid.isPresent()) {
            log.info(String.format("No trace information found in results file for name '%s'", name));
            return Optional.empty();
        }

        // get traced element or fail
        final NamedElement el = this.trace
                .find(uuid.get())
                .orElseThrow(() -> new NoSuchElementException(String.format("Could not find '%s' in trace.", name)));

        // check if traced element is of expected type
        if (tClass.isInstance(el)) {
            return Optional.of((T) el);
        } else {
            // found element is not of correct type
            return Optional.empty();
        }
    }
}
