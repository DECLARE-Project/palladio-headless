package de.fabiankeller.palladio.analysis.result;

import de.fabiankeller.palladio.analysis.result.metric.PerformanceMetric;

public class AttachedResult<T> implements Result<T> {

    private final T attachedTo;
    private final PerformanceMetric value;

    public AttachedResult(final T attachedTo1, final PerformanceMetric value) {
        this.attachedTo = attachedTo1;
        this.value = value;
    }

    public T attachedTo() {
        return this.attachedTo;
    }

    @Override
    public PerformanceMetric value() {
        return this.value;
    }
}