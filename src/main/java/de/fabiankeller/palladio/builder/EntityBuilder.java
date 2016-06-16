package de.fabiankeller.palladio.builder;

import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * Used to build Palladio {@link Entity}s (model instances).
 *
 * @param <SELF> the child interface extending this interface. This is used to provide a proper fluent interface.
 * @param <TYPE> The Palladio {@link Entity} class that is built by this builder
 */
public interface EntityBuilder<
        SELF extends EntityBuilder<SELF, TYPE>,
        TYPE extends Entity
        > extends NamedBuilder<SELF, TYPE> {

    /**
     * Sets the id of the entity.
     */
    default SELF withId(final String id) {
        this.getReference().setId(id);
        return (SELF) this;
    }
}
