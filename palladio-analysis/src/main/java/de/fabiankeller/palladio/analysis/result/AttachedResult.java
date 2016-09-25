package de.fabiankeller.palladio.analysis.result;

import de.fabiankeller.palladio.analysis.result.Result;
import de.fabiankeller.palladio.analysis.result.valueobject.ValueObject;

public class AttachedResult<T> implements Result<T> {

    private final T attachedTo;
    private final ValueObject value;

    public AttachedResult(final T attachedTo1, final ValueObject value) {
        this.attachedTo = attachedTo1;
        this.value = value;
    }

    public T attachedTo() {
        return this.attachedTo;
    }

    @Override
    public ValueObject value() {
        return this.value;
    }
}