package de.fabiankeller.palladio.builder.repository;

import de.fabiankeller.palladio.builder.HierarchicalEntityBuilder;
import org.palladiosimulator.pcm.repository.BasicComponent;

/**
 * Builds a {@link BasicComponent}
 */
public interface ComponentBuilder extends HierarchicalEntityBuilder<ComponentBuilder, BasicComponent, RepositoryBuilder> {

    /**
     * Add an interface the component provides.
     */
    ComponentBuilder provides(InterfaceBuilder iface);

    /**
     * Add an interface the component requires.
     */
    ComponentBuilder requires(InterfaceBuilder iface);
}
