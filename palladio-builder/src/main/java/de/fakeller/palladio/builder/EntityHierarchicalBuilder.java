package de.fakeller.palladio.builder;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * Used to build Palladio {@link Entity}s (model instances) that are children of other instances and thus are built
 * from within a nested builder.
 *
 * @param <SELF>   the child interface extending this interface. This is used to provide a proper fluent interface.
 * @param <TYPE>   The Palladio {@link Entity} class that is built by this builder
 * @param <PARENT> The parent builder that created this builder.
 */
public interface EntityHierarchicalBuilder<
        SELF extends EntityHierarchicalBuilder<SELF, TYPE, PARENT>,
        TYPE extends Entity,
        PARENT extends BaseBuilder<?>
        > extends EntityBuilder<SELF, TYPE>, BaseHierarchicalBuilder<TYPE, PARENT> {

}
