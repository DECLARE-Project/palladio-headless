package de.fakeller.palladio.builder.repository;

import de.fakeller.palladio.builder.EntityBuilder;
import de.fakeller.palladio.builder.repository.failure.SoftwareInducedFailureBuilder;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;

/**
 * Build a {@link Repository} containing interfaces and components.
 */
public interface RepositoryBuilder extends EntityBuilder<RepositoryBuilder, Repository> {

    /**
     * Adds a component with the given name.
     */
    ComponentBuilder withComponent(String name);

    /**
     * Adds an {@link OperationInterface} with the given name.
     */
    InterfaceBuilder withInterface(String name);

    /**
     * Adds an {@link SoftwareInducedFailureType} with the given name to the repository.
     */
    SoftwareInducedFailureBuilder withSoftwareInducedFailure(String name);
}