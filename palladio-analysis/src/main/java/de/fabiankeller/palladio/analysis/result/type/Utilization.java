package de.fabiankeller.palladio.analysis.result.type;

import de.fabiankeller.palladio.analysis.result.valueobject.Percentage;

/**
 * Represents the utilization of a resource.
 */
public class Utilization<T> extends AbstractResult<T> {

    private final Percentage utilization;

    public Utilization(final T attachedTo1, final Percentage utilization) {
        super(attachedTo1);
        this.utilization = utilization;
    }

    public Percentage getUtilization() {
        return this.utilization;
    }
}
