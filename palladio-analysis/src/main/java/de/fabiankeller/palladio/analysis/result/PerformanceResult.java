package de.fabiankeller.palladio.analysis.result;

import de.fabiankeller.palladio.analysis.result.type.Result;

import java.util.Collection;
import java.util.Set;

/**
 * Stores a bunch of performance results associated to the hierarchy below {@link T}.
 *
 * @param <T> The top most node of the model to which performance results are attached.
 */
public interface PerformanceResult<T> {

    /**
     * Returns all available results.
     */
    Collection<Result<? extends T>> getResults();

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
    Collection<Result<? extends T>> getResults(T element);
}
