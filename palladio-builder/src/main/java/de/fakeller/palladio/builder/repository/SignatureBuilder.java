package de.fakeller.palladio.builder.repository;

import de.fakeller.palladio.builder.EntityHierarchicalBuilder;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;

/**
 * Build {@link OperationSignature} models that serve as method signature for a {@link OperationInterface}.
 */
public interface SignatureBuilder extends EntityHierarchicalBuilder<SignatureBuilder, OperationSignature, InterfaceBuilder> {

    /**
     * Add a {@link Parameter} with the given type to the signature.
     */
    SignatureBuilder withParameter(String name, ParameterType type);

    /**
     * Sets the return type of the signature.
     */
    SignatureBuilder withReturnType(ParameterType type);
}