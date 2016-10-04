package de.fabiankeller.performance.analysis.result.metric;

import de.fabiankeller.performance.analysis.result.valueobject.AbstractValueObject;
import de.fabiankeller.performance.analysis.result.valueobject.ValueObject;

/**
 * Used as base class for implementing {@link PerformanceMetric}s.
 */
abstract public class AbstractPerformanceMetric<T extends ValueObject> extends AbstractValueObject<T> implements PerformanceMetric {

    protected AbstractPerformanceMetric(final T value) {
        super(value);
    }

    @Override
    public String toHumanReadable() {
        return String.format("%s: %s", this.getHumanReadableDescription(), this.value.toHumanReadable());
    }

    /**
     * Returns the humand-readable name of this metric. This is used to create the
     * {@link PerformanceMetric::toHumanReadable} notation.
     */
    abstract protected String getHumanReadableDescription();
}
