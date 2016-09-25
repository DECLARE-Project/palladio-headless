package de.fabiankeller.palladio.analysis.result.type;

import de.fabiankeller.palladio.analysis.result.valueobject.Percentage;
import de.fabiankeller.palladio.analysis.result.valueobject.ValueObject;

/**
 * Represents the utilization of a resource.
 */
public class Utilization<T> implements ValueObject {

    private final Percentage utilization;

    public Utilization(final Percentage utilization) {
        this.utilization = utilization;
    }

    public Percentage getUtilization() {
        return this.utilization;
    }

    @Override
    public String toHumanReadable() {
        return "Utilization: " + this.utilization.toHumanReadable();
    }
}
