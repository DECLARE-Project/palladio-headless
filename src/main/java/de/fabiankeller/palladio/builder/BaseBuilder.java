package de.fabiankeller.palladio.builder;

import org.eclipse.emf.cdo.CDOObject;

/**
 * Base interface for building {@link CDOObject}s.
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
     * Returns the built palladio model instance.
     * <p>
     * Validates and builds the model instance.
     *
     * @return the built instance.
     */
    TYPE build() throws BuilderException;
}
