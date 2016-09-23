package de.fabiankeller.palladio.analysis.result.type;

import de.fabiankeller.palladio.analysis.result.valueobject.NormalPercentage;

/**
 * Defines the throughput of a constrained resource.
 */
public class Throughput<T> extends AbstractResult<T> {
    
    private final NormalPercentage throughput;

    public Throughput(final T attachedTo1, final NormalPercentage throughput) {
        super(attachedTo1);
        this.throughput = throughput;
    }

    public NormalPercentage getThroughput() {
        return this.throughput;
    }
}
