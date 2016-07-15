package de.fabiankeller.palladio.builder.util;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;

/**
 * Helps creating {@link PCMRandomVariable}s.
 */
public class RandomVariableFactory {

    public static PCMRandomVariable fromDouble(final double value) {
        return expression(String.valueOf(value));
    }

    public static PCMRandomVariable exp(final double value) {
        return expression("Exp(" + String.valueOf(value) + ")");
    }

    public static PCMRandomVariable expression(final String branchConditionExpression) {
        final PCMRandomVariable expr = CoreFactory.eINSTANCE.createPCMRandomVariable();
        expr.setSpecification(branchConditionExpression);
        return expr;
    }
}
