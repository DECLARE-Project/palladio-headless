package de.fabiankeller.performance.analysis;

/**
 * Used by {@link PerformanceAnalyzer} implementations to designate algorithm capabilities.
 */
public enum AnalysisCapability {
    /**
     * The performance analysis algorithm is capable of delivering precise results, that are usually close what the
     * measurements of a real system would look like.
     */
    PRECISE_RESULTS,

    /**
     * The performance analysis algorithm has a fast execution time (less than 50ms) for most systems on moderate
     * hardware.
     */
    FAST_EXECUTION,
}
