package de.fabiankeller.performance.analysis.result;

import java.util.Collection;
import java.util.Set;

/**
 * Stores a bunch of performance results associated to the hierarchy below {@link T}.
 *
 * @param <T> The root node of the model class hierarchy to which performance results are attached.
 */
public interface PerformanceResult<T> {

    /**
     * Returns all available results.
     */
    Collection<Result<T>> getResults();

    /**
     * Returns a set of all {@link T} elements, that have a performance result attached.
     */
    Set<T> getElementsHavingResults();

    /**
     * Determines whether there are any results at all.
     */
    boolean hasResults();

    /**
     * Determines whether the given {@link T} has any results associated to it.
     */
    boolean hasResults(T element);

    /**
     * Returns all results for the {@link T}.
     */
    Collection<Result<T>> getResults(T element);
}
