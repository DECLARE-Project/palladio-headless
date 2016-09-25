package de.fabiankeller.palladio.analysis.result;

import de.fabiankeller.palladio.analysis.result.metric.ServiceTime;
import de.fabiankeller.palladio.analysis.result.metric.Throughput;
import de.fabiankeller.palladio.analysis.result.metric.Utilization;
import de.fabiankeller.palladio.analysis.result.valueobject.Duration;
import de.fabiankeller.palladio.analysis.result.valueobject.NormalPercentage;
import de.fabiankeller.palladio.analysis.result.valueobject.Percentage;

/**
 * Interface is used by performance analyis approaches to store performance analysis results.
 *
 * @param <T> The top most node of the model to which performance results are attached.
 */
public interface PerformanceResultWriter<T> {

    void attach(Result<? extends T> result);

    default void attachUtilization(final T to, final double utilization) {
        this.attach(new AttachedResult<T>(to, new Utilization(Percentage.of(utilization))));
    }

    default void attachServiceTime(final T to, final Duration duration) {
        this.attach(new AttachedResult<T>(to, new ServiceTime(duration)));
    }

    default void attachThroughout(final T to, final double throughout) {
        this.attach(new AttachedResult<T>(to, new Throughput(NormalPercentage.of(throughout))));
    }
}
