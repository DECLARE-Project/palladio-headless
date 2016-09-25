package de.fabiankeller.palladio.analysis.result.type;

import de.fabiankeller.palladio.analysis.result.valueobject.Duration;
import de.fabiankeller.palladio.analysis.result.valueobject.ValueObject;

/**
 * The time it takes for a single operation to complete.
 */
public class ServiceTime<T> implements ValueObject {

    private final Duration serviceTime;

    public ServiceTime(final Duration serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Duration getServiceTime() {
        return this.serviceTime;
    }

    @Override
    public String toHumanReadable() {
        return "ServiceTime: " + this.serviceTime.toHumanReadable();
    }
}
