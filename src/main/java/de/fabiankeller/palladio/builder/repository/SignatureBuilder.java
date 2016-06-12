package de.fabiankeller.palladio.builder.repository;

import de.fabiankeller.palladio.builder.HierarchicalEntityBuilder;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;

/**
 * Build {@link OperationSignature} models that serve as method signature for a {@link OperationInterface}.
 */
public interface SignatureBuilder extends HierarchicalEntityBuilder<SignatureBuilder, OperationSignature, InterfaceBuilder> {

    /**
     * Add a {@link Parameter} with the given type to the signature.
     */
    SignatureBuilder withParameter(String name, ParameterType type);

    /**
     * Sets the return type of the signature.
     */
    SignatureBuilder withReturnType(ParameterType type);
}