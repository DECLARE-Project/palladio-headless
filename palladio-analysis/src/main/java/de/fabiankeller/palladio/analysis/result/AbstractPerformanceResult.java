package de.fabiankeller.palladio.analysis.result;

import de.fabiankeller.palladio.analysis.result.type.Result;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides a base class to implement the {@link PerformanceResult} and {@link PerformanceResultWriter} interfaces.
 */
public class AbstractPerformanceResult<T> implements PerformanceResult<T>, PerformanceResultWriter<T> {

    /**
     * Stores the actual results.
     */
    private final Map<T, List<Result<? extends T>>> results = new HashMap<>();

    @Override
    public void attach(final Result<? extends T> result) {
        final T key = result.attachedTo();
        if (!this.results.containsKey(key)) {
            this.results.put(key, new ArrayList<>());
        }
        this.results.get(key).add(result);
    }


    @Override
    public boolean hasResults() {
        return this.results.size() > 0;
    }

    @Override
    public Collection<Result<? extends T>> getResults() {
        assert this.hasResults();
        // return a flat view on all results
        return this.results
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.toList());
    }

    @Override
    public Set<T> getElementsHavingResults() {
        assert this.hasResults();
        return this.results.keySet();
    }

    @Override
    public boolean hasResults(final T element) {
        return this.results.containsKey(element) && this.results.get(element).size() > 0;
    }

    @Override
    public Collection<Result<? extends T>> getResults(final T element) {
        assert this.hasResults(element);
        return this.results.get(element);
    }
}
