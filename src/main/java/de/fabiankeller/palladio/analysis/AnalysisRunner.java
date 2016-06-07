package de.fabiankeller.palladio.analysis;

import org.palladiosimulator.solver.models.PCMInstance;

/**
 * Runs a Palladio analysis.
 */
public interface AnalysisRunner {

    /**
     * Executes an analysis for the given PCM instance.
     */
    void run(PCMInstance pcmInstance);
}
