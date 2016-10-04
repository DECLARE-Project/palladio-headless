package de.fabiankeller.performance.analysis.result.trace;

import de.fabiankeller.performance.analysis.result.PerformanceResult;

/**
 * Interface used to trace performance results between different models.
 */
public interface TraceMapper<FROM, TO> {
    /**
     * Maps a performance result to a different model by mapping the elements the results are attached to from one model
     * to the other.
     */
    PerformanceResult<TO> map(PerformanceResult<FROM> result);
}
