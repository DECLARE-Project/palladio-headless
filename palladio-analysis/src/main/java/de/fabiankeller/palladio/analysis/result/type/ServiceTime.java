package de.fabiankeller.palladio.analysis.result.type;

import de.fabiankeller.palladio.analysis.result.valueobject.Duration;

/**
 * The time it takes for a single operation to complete.
 */
public class ServiceTime<T> extends AbstractResult<T> {

    private final Duration serviceTime;

    public ServiceTime(final T attachedTo1, final Duration serviceTime) {
        super(attachedTo1);
        this.serviceTime = serviceTime;
    }

    public Duration getServiceTime() {
        return this.serviceTime;
    }
}
