package de.fakeller.palladio.builder.repository.failure.impl;

import de.fakeller.palladio.builder.AbstractHierarchicalBuilder;
import de.fakeller.palladio.builder.repository.RepositoryBuilder;
import de.fakeller.palladio.builder.repository.failure.SoftwareInducedFailureBuilder;
import org.palladiosimulator.pcm.reliability.ReliabilityFactory;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;

public class SoftwareInducedFailureBuilderImpl
        extends AbstractHierarchicalBuilder<SoftwareInducedFailureBuilder, SoftwareInducedFailureType, RepositoryBuilder>
        implements SoftwareInducedFailureBuilder {

    public SoftwareInducedFailureBuilderImpl(final RepositoryBuilder belongsTo) {
        super(belongsTo);
    }

    @Override
    protected SoftwareInducedFailureType newEModel() {
        return ReliabilityFactory.eINSTANCE.createSoftwareInducedFailureType();
    }

    @Override
    protected void registerParent() {
        this.eModel.setRepository__FailureType(this.belongsTo.getReference());
        this.belongsTo.getReference().getFailureTypes__Repository().add(this.eModel);
    }
}
