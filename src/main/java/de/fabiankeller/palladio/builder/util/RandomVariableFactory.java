package de.fabiankeller.palladio.builder.util;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;

/**
 * Helps creating {@link PCMRandomVariable}s.
 */
public class RandomVariableFactory {

    public static PCMRandomVariable fromDouble(final double value) {
        final PCMRandomVariable rnd = CoreFactory.eINSTANCE.createPCMRandomVariable();
        rnd.setSpecification(String.valueOf(value));
        return rnd;
    }

    public static PCMRandomVariable exp(final double value) {
        final PCMRandomVariable rnd = CoreFactory.eINSTANCE.createPCMRandomVariable();
        rnd.setSpecification("Exp(" + String.valueOf(value) + ")");
        return rnd;
    }
}
