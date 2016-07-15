package de.fabiankeller.palladio.builder;

import org.eclipse.emf.cdo.CDOObject;

/**
 * Used to build models that are children of other instances and thus are built from within a nested builder.
 *
 * @param <TYPE>   The model class ({@link CDOObject}) that is built by this builder
 * @param <PARENT> The parent builder that created this builder.
 */
public interface BaseHierarchicalBuilder<
        TYPE extends CDOObject,
        PARENT extends BaseBuilder<?>
        > extends BaseBuilder<TYPE> {

    /**
     * Ends the fluent interface in this level and returns to the parent builder.
     */
    PARENT end();
}
