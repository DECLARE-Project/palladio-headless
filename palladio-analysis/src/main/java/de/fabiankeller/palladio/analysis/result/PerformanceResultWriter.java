package de.fabiankeller.palladio.analysis.result;

/**
 * Interface is used by performance analyis approaches to store performance analysis results.
 *
 * @param <T> The top most node of the model to which performance results are attached.
 */
public interface PerformanceResultWriter<T> {

    void attach(Result<? extends T> result);
}
