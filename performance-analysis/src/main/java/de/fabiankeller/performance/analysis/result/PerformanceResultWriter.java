package de.fabiankeller.performance.analysis.result;

import de.fabiankeller.performance.analysis.result.metric.ServiceTime;
import de.fabiankeller.performance.analysis.result.metric.Throughput;
import de.fabiankeller.performance.analysis.result.metric.Utilization;
import de.fabiankeller.performance.analysis.result.valueobject.Duration;
import de.fabiankeller.performance.analysis.result.valueobject.NormalPercentage;
import de.fabiankeller.performance.analysis.result.valueobject.Percentage;

/**
 * Interface is used by performance analyis approaches to store performance analysis results.
 *
 * @param <T> The top most node of the model to which performance results are attached.
 */
public interface PerformanceResultWriter<T> {

    /**
     * Stores a single performance result.
     */
    void attach(Result<T> result);

    /**
     * Returns the actual performance result.
     */
    PerformanceResult<T> get();

    default void attachUtilization(final T to, final double utilization) {
        this.attach(new AttachedResult<T>(to, new Utilization(Percentage.of(utilization))));
    }

    default void attachServiceTime(final T to, final Duration duration) {
        this.attach(new AttachedResult<T>(to, new ServiceTime(duration)));
    }

    default void attachThroughput(final T to, final double throughout) {
        this.attach(new AttachedResult<T>(to, new Throughput(NormalPercentage.of(throughout))));
    }
}
