package de.fakeller.palladio.builder.util.random;

import org.palladiosimulator.pcm.core.PCMRandomVariable;

/**
 * Represents a random variable.
 */
public class RandomVariable {

    private final PCMRandomVariable rnd;

    public RandomVariable(final PCMRandomVariable rnd) {
        this.rnd = rnd;
    }

    public PCMRandomVariable get() {
        return this.rnd;
    }
}
