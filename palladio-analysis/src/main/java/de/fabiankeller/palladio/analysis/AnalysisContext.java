package de.fabiankeller.palladio.analysis;

import de.fabiankeller.palladio.analysis.result.PerformanceResult;

/**
 * Defines the context of a single analysis.
 * <p>
 * The analysis is bound to this specific object and all interactions are specific to the particular analysis.
 */
public interface AnalysisContext<SYSTEM> {

    /**
     * Runs the analysis and yields the performance results.
     */
    PerformanceResult<?> analyze();
}
