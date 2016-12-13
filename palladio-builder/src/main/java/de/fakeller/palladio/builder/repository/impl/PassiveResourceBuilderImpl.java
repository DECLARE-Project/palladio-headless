package de.fakeller.palladio.builder.repository.impl;

import de.fakeller.palladio.builder.AbstractHierarchicalBuilder;
import de.fakeller.palladio.builder.repository.ComponentBuilder;
import de.fakeller.palladio.builder.repository.PassiveResourceBuilder;
import de.fakeller.palladio.builder.util.random.RandomVariable;
import org.palladiosimulator.pcm.repository.PassiveResource;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

/**
 * @author Fabian Keller
 */
public class PassiveResourceBuilderImpl extends AbstractHierarchicalBuilder<PassiveResourceBuilder, PassiveResource, ComponentBuilder> implements PassiveResourceBuilder {

    PassiveResourceBuilderImpl(final ComponentBuilder belongsTo) {
        super(belongsTo);
    }

    @Override
    protected PassiveResource newEModel() {
        return RepositoryFactory.eINSTANCE.createPassiveResource();
    }

    @Override
    protected void registerParent() {
        this.eModel.setBasicComponent_PassiveResource(this.belongsTo.getReference());
        this.belongsTo.getReference().getPassiveResource_BasicComponent().add(this.eModel);
    }


    @Override
    public PassiveResourceBuilder withCapacity(final RandomVariable capacity) {
        this.eModel.setCapacity_PassiveResource(capacity.get());
        return this;
    }

    @Override
    public ComponentBuilder end() {
        return this.belongsTo;
    }

    @Override
    public PassiveResource getReference() {
        return this.eModel;
    }

}
