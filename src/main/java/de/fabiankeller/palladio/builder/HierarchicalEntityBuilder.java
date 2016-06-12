package de.fabiankeller.palladio.builder;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * Used to build Palladio {@link Entity}s (model instances) that are children of other instances and thus are built
 * from within a nested builder.
 */
public interface HierarchicalEntityBuilder<
        SELF extends HierarchicalEntityBuilder<SELF, TYPE, PARENT>,
        TYPE extends Entity,
        PARENT extends EntityBuilder<PARENT, ?>
        > extends EntityBuilder<SELF, TYPE> {

    /**
     * Ends the fluent interface in this level and returns to the parent builder.
     */
    PARENT end();
}
