package de.fabiankeller.palladio.analysis.result.type;

import de.fabiankeller.palladio.analysis.result.valueobject.NormalPercentage;
import de.fabiankeller.palladio.analysis.result.valueobject.ValueObject;

/**
 * Defines the throughput of a constrained resource.
 */
public class Throughput<T> implements ValueObject {

    private final NormalPercentage throughput;

    public Throughput(final NormalPercentage throughput) {
        this.throughput = throughput;
    }

    public NormalPercentage getThroughput() {
        return this.throughput;
    }

    @Override
    public String toHumanReadable() {
        return "Throughput: " + this.throughput.toHumanReadable();
    }
}
