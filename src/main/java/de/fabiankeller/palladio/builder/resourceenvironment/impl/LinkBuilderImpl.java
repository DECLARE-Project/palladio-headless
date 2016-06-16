package de.fabiankeller.palladio.builder.resourceenvironment.impl;

import de.fabiankeller.palladio.builder.repository.impl.AbstractHierarchicalBuilder;
import de.fabiankeller.palladio.builder.resourceenvironment.ContainerBuilder;
import de.fabiankeller.palladio.builder.resourceenvironment.LinkBuilder;
import de.fabiankeller.palladio.builder.resourceenvironment.ResourceEnvironmentBuilder;
import de.fabiankeller.palladio.builder.util.PalladioResourceRepository;
import de.fabiankeller.palladio.builder.util.RandomVariableFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.resourceenvironment.CommunicationLinkResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;

public class LinkBuilderImpl extends AbstractHierarchicalBuilder<LinkBuilder, LinkingResource, ResourceEnvironmentBuilder> implements LinkBuilder {

    private final PalladioResourceRepository resources;

    private final CommunicationLinkResourceSpecification spec;

    public LinkBuilderImpl(final ResourceEnvironmentBuilder belongsTo, final PalladioResourceRepository resources) {
        super(belongsTo);
        this.resources = resources;

        // create spec
        this.spec = ResourceenvironmentFactory.eINSTANCE.createCommunicationLinkResourceSpecification();
        this.spec.setCommunicationLinkResourceType_CommunicationLinkResourceSpecification(this.resources.lan());

        // link spec to resource
        this.spec.setLinkingResource_CommunicationLinkResourceSpecification(this.eModel);
        this.eModel.setCommunicationLinkResourceSpecifications_LinkingResource(this.spec);
    }

    @Override
    protected LinkingResource newEModel() {
        return ResourceenvironmentFactory.eINSTANCE.createLinkingResource();
    }

    @Override
    protected void registerParent() {
        this.belongsTo.getReference().getLinkingResources__ResourceEnvironment().add(this.eModel);
        this.eModel.setResourceEnvironment_LinkingResource(this.belongsTo.getReference());
    }

    @Override
    public LinkBuilder between(final ContainerBuilder... containers) {
        for (final ContainerBuilder container : containers) {
            this.eModel.getConnectedResourceContainers_LinkingResource().add(container.getReference());
        }
        return this;
    }

    @Override
    public LinkBuilder withLatency(final double latency) {
        final PCMRandomVariable rnd = RandomVariableFactory.fromDouble(latency);
        this.spec.setLatency_CommunicationLinkResourceSpecification(rnd);
        return this;
    }

    @Override
    public LinkBuilder withThroughput(final double throughput) {
        final PCMRandomVariable rnd = RandomVariableFactory.fromDouble(throughput);
        this.spec.setThroughput_CommunicationLinkResourceSpecification(rnd);
        return this;
    }

    @Override
    public LinkBuilder withFailureProbability(final double probability) {
        this.spec.setFailureProbability(probability);
        return this;
    }
}
