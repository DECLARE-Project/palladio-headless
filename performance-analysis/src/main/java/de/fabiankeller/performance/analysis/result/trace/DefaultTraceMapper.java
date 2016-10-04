package de.fabiankeller.performance.analysis.result.trace;

import de.fabiankeller.performance.analysis.result.AttachedResult;
import de.fabiankeller.performance.analysis.result.PerformanceResult;
import de.fabiankeller.performance.analysis.result.PerformanceResultWriter;
import de.fabiankeller.performance.analysis.result.Result;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * @author Fabian Keller
 */
public class DefaultTraceMapper<FROM, TO> implements TraceMapper<FROM, TO> {

    private static final Logger log = Logger.getLogger(DefaultTraceMapper.class.getName());

    private final Supplier<PerformanceResultWriter<TO>> resultWriterFactory;
    private final Function<FROM, Optional<TO>> traceMapper;

    /**
     * Initialize a trace mapper.
     *
     * @param resultWriterFactory when called, creates a new {@link PerformanceResultWriter}
     * @param traceMapper         maps a {@link FROM} model object to its corresponding {@link TO} object. If there is
     *                            no such corresponding element, the method may return {@link Optional#empty()}.
     */
    public DefaultTraceMapper(final Supplier<PerformanceResultWriter<TO>> resultWriterFactory, final Function<FROM, Optional<TO>> traceMapper) {
        this.resultWriterFactory = resultWriterFactory;
        this.traceMapper = traceMapper;
    }

    @Override
    public PerformanceResult<TO> map(final PerformanceResult<FROM> result) {
        final PerformanceResultWriter<TO> writer = this.resultWriterFactory.get();
        if (!result.hasResults()) {
            return writer.get();
        }
        for (final Result<FROM> res : result.getResults()) {
            final Optional<TO> traced = this.traceMapper.apply(res.attachedTo());
            if (traced.isPresent()) {
                writer.attach(new AttachedResult<TO>(traced.get(), res.value()));
            } else {
                log.fine(String.format("Could not trace element '%s' in result trace mapper. Skipping result.", res.attachedTo()));
            }
        }
        return writer.get();
    }
}
