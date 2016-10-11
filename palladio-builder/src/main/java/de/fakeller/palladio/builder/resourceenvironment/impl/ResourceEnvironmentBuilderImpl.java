package de.fakeller.palladio.builder.resourceenvironment.impl;

import de.fakeller.palladio.builder.resourceenvironment.ContainerBuilder;
import de.fakeller.palladio.builder.resourceenvironment.LinkBuilder;
import de.fakeller.palladio.builder.resourceenvironment.ResourceEnvironmentBuilder;
import de.fakeller.palladio.environment.util.PalladioResourceRepository;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;

public class ResourceEnvironmentBuilderImpl implements ResourceEnvironmentBuilder {
    private final PalladioResourceRepository resources;
    private final ResourceEnvironment env;

    public ResourceEnvironmentBuilderImpl(final PalladioResourceRepository resources) {
        this.resources = resources;
        this.env = ResourceenvironmentFactory.eINSTANCE.createResourceEnvironment();
    }

    @Override
    public ResourceEnvironment getReference() {
        return this.env;
    }

    @Override
    public ContainerBuilder createContainer(final String name) {
        return new ContainerBuilderImpl(this, this.resources).withEntityName(name);
    }

    @Override
    public LinkBuilder createLink(final String name) {
        return new LinkBuilderImpl(this, this.resources).withEntityName(name);
    }
}
