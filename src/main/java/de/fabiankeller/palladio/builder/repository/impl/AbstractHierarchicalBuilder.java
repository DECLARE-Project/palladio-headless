package de.fabiankeller.palladio.builder.repository.impl;

import de.fabiankeller.palladio.builder.EntityBuilder;
import de.fabiankeller.palladio.builder.EntityViolationException;
import de.fabiankeller.palladio.builder.HierarchicalEntityBuilder;
import org.palladiosimulator.pcm.core.entity.Entity;

/**
 * Provides common functionality to implement {@link HierarchicalEntityBuilder}.
 */
abstract public class AbstractHierarchicalBuilder<
        SELF extends HierarchicalEntityBuilder<SELF, TYPE, PARENT>,
        TYPE extends Entity,
        PARENT extends EntityBuilder<PARENT, ?>
        > implements HierarchicalEntityBuilder<SELF, TYPE, PARENT> {

    /**
     * Access the parent builder
     */
    protected final PARENT belongsTo;

    /**
     * The model under construction
     */
    protected TYPE eModel;

    public AbstractHierarchicalBuilder(final PARENT belongsTo) {
        this.belongsTo = belongsTo;
        this.eModel = newEModel();
        registerParent();
    }

    /**
     * The method must return a new object of type {@link TYPE}.
     */
    abstract protected TYPE newEModel();

    /**
     * The method must register the parent model stored in {@link AbstractHierarchicalBuilder#belongsTo} with the actual
     * model instance stored in {@link AbstractHierarchicalBuilder#eModel}.
     */
    abstract protected void registerParent();

    @Override
    public PARENT end() {
        return this.belongsTo;
    }

    @Override
    public TYPE getReference() {
        return this.eModel;
    }

    @Override
    public TYPE build() throws EntityViolationException {
        return this.getReference();
    }
}
