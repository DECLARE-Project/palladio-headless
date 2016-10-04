package de.fabiankeller.performance.analysis;

import java.util.Set;

/**
 * Interface used to create performance analyses of a system.
 * <p>
 * Analyzers implementing this interface do not perform any performance analyses. They simply create and configure a
 * specific {@link AnalysisContext} that is able to execute the actual analysis.
 *
 * @param <SYSTEM>  the representation of the model to analyze
 * @param <CONTEXT> the class that is able to perform the actual performance analysis
 */
public interface PerformanceAnalyzer<SYSTEM, CONTEXT extends AnalysisContext<SYSTEM>> {

    /**
     * Returns the capabilities of this performance analysis approach.
     */
    Set<AnalysisCapability> capabilities();

    /**
     * Determines whether the analysis approach supports the given system.
     * <p>
     * Note: any implementation of this method should be seen as a smoke test and should run really fast on moderate
     * hardware for a moderate system size. This method might get called very often!
     */
    boolean supports(SYSTEM system);

    /**
     * Sets up and configures the analysis.
     */
    CONTEXT setupAnalysis(SYSTEM system);
}
