package de.fakeller.palladio.builder.util.random;

/**
 * @author Fabian Keller
 */
public interface RandomVariableFactory {

    /**
     * Create random variable with constant value.
     */
    RandomVariable fromDouble(double value);

    /**
     * Create random variable with exponential distribution.
     */
    RandomVariable exp(double value);

    /**
     * Creates a random variable from a valid {@link org.palladiosimulator.pcm.core.PCMRandomVariable} expression.
     */
    RandomVariable expression(String expression);


    static RandomVariableFactory factory() {
        return new RandomVariableFactoryImpl();
    }
}
