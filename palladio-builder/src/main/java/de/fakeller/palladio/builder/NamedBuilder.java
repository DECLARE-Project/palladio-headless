package de.fakeller.palladio.builder;

import org.palladiosimulator.pcm.core.entity.NamedElement;

/**
 * Helps building {@link NamedElement}s.
 *
 * @param <SELF> the child interface extending this interface. This is used to provide a proper fluent interface.
 * @param <TYPE> The Palladio {@link NamedElement} class that is built by this builder
 */
public interface NamedBuilder<
        SELF extends NamedBuilder<SELF, TYPE>,
        TYPE extends NamedElement
        > extends BaseBuilder<TYPE> {

    /**
     * Sets the name of the entity.
     */
    default SELF withEntityName(final String name) {
        this.getReference().setEntityName(name);
        return (SELF) this;
    }

    /**
     * Returns the name of the entity under construction.
     */
    default String getEntityName() {
        return this.getReference().getEntityName();
    }

}
