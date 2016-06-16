package de.fabiankeller.palladio.builder.resourceenvironment.impl;

import de.fabiankeller.palladio.builder.resourceenvironment.ContainerBuilder;
import de.fabiankeller.palladio.builder.resourceenvironment.ProcessingResourceBuilder;
import de.fabiankeller.palladio.builder.util.RandomVariableFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;

public class ProcessingResourceBuilderImpl implements ProcessingResourceBuilder {

    ContainerBuilder belongsTo;

    ProcessingResourceSpecification eModel;

    public ProcessingResourceBuilderImpl(final ContainerBuilder belongsTo) {
        this.belongsTo = belongsTo;

        // build model
        this.eModel = ResourceenvironmentFactory.eINSTANCE.createProcessingResourceSpecification();

        // link model
        this.eModel.setResourceContainer_ProcessingResourceSpecification(this.belongsTo.getReference());
        this.belongsTo.getReference().getActiveResourceSpecifications_ResourceContainer().add(this.eModel);
    }

    @Override
    public ProcessingResourceBuilder withProcessingRate(final double processingRate) {
        final PCMRandomVariable rnd = RandomVariableFactory.fromDouble(processingRate);
        this.eModel.setProcessingRate_ProcessingResourceSpecification(rnd);
        return this;
    }

    @Override
    public ProcessingResourceBuilder withMTTF(final double mttf) {
        this.eModel.setMTTF(mttf);
        return this;
    }

    @Override
    public ProcessingResourceBuilder withMTTR(final double mttr) {
        this.eModel.setMTTR(mttr);
        return this;
    }

    @Override
    public ProcessingResourceBuilder withNumberOfReplicas(final int numberOfReplicas) {
        this.eModel.setNumberOfReplicas(numberOfReplicas);
        return this;
    }

    @Override
    public ProcessingResourceBuilder withRequiredByContainer(final boolean requiredByContainer) {
        this.eModel.setRequiredByContainer(requiredByContainer);
        return this;
    }

    @Override
    public ProcessingResourceBuilder withSchedulingPolicy() {
        // fixme: implement
        return null;
    }

    @Override
    public ProcessingResourceSpecification getReference() {
        return this.eModel;
    }

    @Override
    public ContainerBuilder end() {
        return this.belongsTo;
    }
}
