package de.fabiankeller.palladio.builder.resourceenvironment.impl;

import de.fabiankeller.palladio.builder.BuilderException;
import de.fabiankeller.palladio.builder.repository.impl.AbstractHierarchicalBuilder;
import de.fabiankeller.palladio.builder.resourceenvironment.ContainerBuilder;
import de.fabiankeller.palladio.builder.resourceenvironment.ProcessingResourceBuilder;
import de.fabiankeller.palladio.builder.resourceenvironment.ResourceEnvironmentBuilder;
import de.fabiankeller.palladio.builder.util.PalladioResourceRepository;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;

public class ContainerBuilderImpl extends AbstractHierarchicalBuilder<ContainerBuilder, ResourceContainer, ResourceEnvironmentBuilder> implements ContainerBuilder {

    private final PalladioResourceRepository resources;

    public ContainerBuilderImpl(final ResourceEnvironmentBuilder belongsTo, final PalladioResourceRepository resources) {
        super(belongsTo);
        this.resources = resources;
    }

    @Override
    protected ResourceContainer newEModel() {
        return ResourceenvironmentFactory.eINSTANCE.createResourceContainer();
    }

    @Override
    protected void registerParent() {
        this.belongsTo.getReference().getResourceContainer_ResourceEnvironment().add(this.eModel);
        this.eModel.setResourceEnvironment_ResourceContainer(this.belongsTo.getReference());
    }


    @Override
    public ContainerBuilder setParentContainer(final ContainerBuilder parent) {
        if (null != this.eModel.getParentResourceContainer__ResourceContainer()) {
            throw new BuilderException(this, String.format("Cannot set the parent for the resource createContainer, as it already has a parent createContainer (%s)!",
                    this.eModel.getParentResourceContainer__ResourceContainer().getEntityName()));
        }

        // bidirectional reference
        this.eModel.setParentResourceContainer__ResourceContainer(parent.getReference());
        parent.getReference().getNestedResourceContainers__ResourceContainer().add(this.eModel);

        return this;
    }

    @Override
    public ProcessingResourceBuilder withCpu(final double processingRate) {
        return procBuilder(this.resources.cpu()).withProcessingRate(processingRate);
    }

    @Override
    public ProcessingResourceBuilder withHdd() {
        return procBuilder(this.resources.hdd());
    }

    @Override
    public ProcessingResourceBuilder withDelay() {
        return procBuilder(this.resources.delay());
    }

    private ProcessingResourceBuilder procBuilder(final ProcessingResourceType cpu) {
        final ProcessingResourceBuilder builder = new ProcessingResourceBuilderImpl(this);
        builder.getReference().setActiveResourceType_ActiveResourceSpecification(cpu);
        return builder;
    }
}
