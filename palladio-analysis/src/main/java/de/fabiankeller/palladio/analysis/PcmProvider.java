package de.fabiankeller.palladio.analysis;

import org.palladiosimulator.solver.models.PCMInstance;

/**
 * Provides a {@link PCMInstance}.
 */
public interface PcmProvider {

    PCMInstance provide();
}
