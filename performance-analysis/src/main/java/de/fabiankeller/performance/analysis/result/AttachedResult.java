package de.fabiankeller.performance.analysis.result;

import de.fabiankeller.performance.analysis.result.metric.PerformanceMetric;

public class AttachedResult<T> implements Result<T> {

    private final T attachedTo;
    private final PerformanceMetric value;

    public AttachedResult(final T attachedTo1, final PerformanceMetric value) {
        assert null != attachedTo1;
        assert null != value;

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


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AttachedResult<?> that = (AttachedResult<?>) o;

        if (!this.attachedTo.equals(that.attachedTo)) return false;
        return this.value.equals(that.value);

    }

    @Override
    public int hashCode() {
        int result = this.attachedTo.hashCode();
        result = 31 * result + this.value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("Result[%s]:%s", this.attachedTo, this.value.toHumanReadable());
    }
}