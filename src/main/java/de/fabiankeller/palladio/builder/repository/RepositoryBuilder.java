package de.fabiankeller.palladio.builder.repository;

import de.fabiankeller.palladio.builder.EntityBuilder;
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
}