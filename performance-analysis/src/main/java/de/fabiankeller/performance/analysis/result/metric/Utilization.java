package de.fabiankeller.performance.analysis.result.metric;

import de.fabiankeller.performance.analysis.result.valueobject.Percentage;

/**
 * Represents the utilization of a resource.
 */
public class Utilization extends AbstractPerformanceMetric<Percentage> {

    public Utilization(final Percentage utilization) {
        super(utilization);
    }

    public Percentage getUtilization() {
        return this.value;
    }

    @Override
    protected String getHumanReadableDescription() {
        return "Utilization";
    }
}
