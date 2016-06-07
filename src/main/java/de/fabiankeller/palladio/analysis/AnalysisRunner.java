package de.fabiankeller.palladio.analysis;

import org.palladiosimulator.solver.models.PCMInstance;
import org.palladiosimulator.solver.transformations.SolverStrategy;

/**
 * Runs a Palladio analysis leveraging a certain {@link SolverStrategy}.
 */
public interface AnalysisRunner {

    /**
     * Executes an analysis for the given PCM instance.
     */
    void run(PCMInstance pcmInstance);
}
