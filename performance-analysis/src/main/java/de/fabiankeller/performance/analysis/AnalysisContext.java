package de.fabiankeller.performance.analysis;

import de.fabiankeller.performance.analysis.result.PerformanceResult;

/**
 * Defines the context of a single analysis.
 * <p>
 * The analysis is bound to this specific object and all interactions are specific to the particular analysis.
 *
 * @param <SYSTEM> the representation of the model to analyze
 */
public interface AnalysisContext<SYSTEM> {

    /**
     * Runs the analysis and yields the performance results.
     */
    PerformanceResult<?> analyze();
}
