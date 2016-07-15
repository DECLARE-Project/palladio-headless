package de.fabiankeller.palladio.builder;

import org.eclipse.emf.cdo.CDOObject;

/**
 * Base interface for building {@link CDOObject}s.
 *
 * @param <TYPE> The model class ({@link CDOObject}) that is built by this builder
 */
public interface BaseBuilder<TYPE extends CDOObject> {

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
     * Returns the built palladio model instance. Ensures the returned model is fully constructed and consistent.
     * <p>
     * Validates and builds the model instance.
     *
     * @return the built instance.
     * @deprecated - this method will most likely be removed in the near future as there is no need for it
     */
    default TYPE build() throws BuilderException {
        return this.getReference();
    }
}
