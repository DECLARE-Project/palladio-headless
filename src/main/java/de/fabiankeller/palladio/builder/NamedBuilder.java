package de.fabiankeller.palladio.builder;

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
        > {

    /**
     * Sets the name of the entity.
     */
    default SELF withEntityName(final String name) {
        this.getReference().setEntityName(name);
        return (SELF) this;
    }

    /**
     * Returns a reference to the palladio model.
     * <p>
     * Note: calls to this method should only be performed by builders, as the sole purpose of the method is to return
     * an object reference. The returned object may not have been fully constructed and is thus likely to have an
     * inconsistent state.
     *
     * @return reference to the object under construction
     */
    TYPE getReference();

    /**
     * Returns the built palladio model instance.
     * <p>
     * Validates and builds the model instance.
     *
     * @return the built instance.
     */
    TYPE build() throws EntityViolationException;
}
