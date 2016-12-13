package de.fakeller.palladio.builder.util.random;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;

/**
 * Helps creating {@link PCMRandomVariable}s.
 */
public class RandomVariableFactoryImpl implements RandomVariableFactory {

    @Override
    public RandomVariable fromDouble(final double value) {
        return expression(String.valueOf(value));
    }

    @Override
    public RandomVariable exp(final double value) {
        return expression("Exp(" + String.valueOf(value) + ")");
    }

    @Override
    public RandomVariable expression(final String expression) {
        final PCMRandomVariable expr = CoreFactory.eINSTANCE.createPCMRandomVariable();
        expr.setSpecification(expression);
        return new RandomVariable(expr);
    }
}
