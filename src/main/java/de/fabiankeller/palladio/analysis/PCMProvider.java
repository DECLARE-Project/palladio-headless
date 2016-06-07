package de.fabiankeller.palladio.analysis;

import org.palladiosimulator.solver.models.PCMInstance;

/**
 * Provides a PCMInstance.
 */
public interface PCMProvider {

    PCMInstance provide();
}
