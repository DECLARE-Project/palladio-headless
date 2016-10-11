package de.fakeller.palladio.builder.util;

import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.parameter.VariableUsage;

/**
 * Helps to create {@link VariableUsage}s objects.
 */
public interface VariableUsageFactory {
    /**
     * Variable usage with type VALUE ({@link VariableCharacterisationType} for more info).
     */
    VariableUsage valueUsage(String variableName, String specification);
}
