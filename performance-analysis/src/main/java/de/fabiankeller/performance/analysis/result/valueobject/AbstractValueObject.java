package de.fabiankeller.performance.analysis.result.valueobject;

/**
 * Defines hashCode and equals for value objects.
 */
abstract public class AbstractValueObject<T> implements ValueObject {
    protected final T value;

    protected AbstractValueObject(final T value) {
        assert null != value;
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AbstractValueObject<?> that = (AbstractValueObject<?>) o;

        return this.value.equals(that.value);

    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}
