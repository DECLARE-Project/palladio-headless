package de.fabiankeller.performance.analysis.result.metric;

import de.fabiankeller.performance.analysis.result.valueobject.NormalPercentage;

/**
 * Defines the throughput of a constrained resource.
 */
public class Throughput extends AbstractPerformanceMetric<NormalPercentage> {

    public Throughput(final NormalPercentage throughput) {
        super(throughput);
    }

    public NormalPercentage getThroughput() {
        return this.value;
    }

    @Override
    protected String getHumanReadableDescription() {
        return "Throughput";
    }
}
