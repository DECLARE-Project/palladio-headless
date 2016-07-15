package de.fabiankeller.palladio.builder.repository;

import de.fabiankeller.palladio.builder.EntityHierarchicalBuilder;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;

/**
 * Builds an {@link OperationInterface}.
 */
public interface InterfaceBuilder extends EntityHierarchicalBuilder<InterfaceBuilder, OperationInterface, RepositoryBuilder> {

    /**
     * Adds an interface as parent.
     */
    InterfaceBuilder withParentInterface(InterfaceBuilder interfaceBuilder);

    /**
     * Adds an {@link OperationSignature} to the interface.
     */
    SignatureBuilder createOperation(String name);
}