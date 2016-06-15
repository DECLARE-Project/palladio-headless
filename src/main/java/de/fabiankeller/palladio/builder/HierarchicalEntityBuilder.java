package de.fabiankeller.palladio.builder;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * Used to build Palladio {@link Entity}s (model instances) that are children of other instances and thus are built
 * from within a nested builder.
 *
 * @param <SELF>   the child interface extending this interface. This is used to provide a proper fluent interface.
 * @param <TYPE>   The Palladio {@link Entity} class that is built by this builder
 * @param <PARENT> The parent builder that created this builder.
 */
public interface HierarchicalEntityBuilder<
        SELF extends HierarchicalEntityBuilder<SELF, TYPE, PARENT>,
        TYPE extends Entity,
        PARENT extends NamedBuilder<PARENT, ?>
        > extends EntityBuilder<SELF, TYPE> {

    /**
     * Ends the fluent interface in this level and returns to the parent builder.
     */
    PARENT end();
}
