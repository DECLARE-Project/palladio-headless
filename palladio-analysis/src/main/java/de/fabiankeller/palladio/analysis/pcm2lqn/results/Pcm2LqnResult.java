package de.fabiankeller.palladio.analysis.pcm2lqn.results;

import de.fabiankeller.performance.analysis.result.AbstractPerformanceResult;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.solver.models.PCMInstance;

public class Pcm2LqnResult extends AbstractPerformanceResult<NamedElement> {

    private final PCMInstance pcm;

    public Pcm2LqnResult(final PCMInstance pcm) {
        this.pcm = pcm;
    }

    /**
     * Returns the pcm instance to which the results apply.
     */
    public PCMInstance getPcmInstance() {
        return this.pcm;
    }

}
