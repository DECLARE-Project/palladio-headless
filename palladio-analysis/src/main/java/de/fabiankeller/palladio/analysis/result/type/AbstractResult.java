package de.fabiankeller.palladio.analysis.result.type;

public abstract class AbstractResult<T> implements Result<T> {

    private final T attachedTo;

    public AbstractResult(final T attachedTo1) {
        this.attachedTo = attachedTo1;
    }

    public T attachedTo() {
        return this.attachedTo;
    }
}