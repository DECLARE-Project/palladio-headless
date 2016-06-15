package de.fabiankeller.palladio.builder.resourceenvironment.impl;

import de.fabiankeller.palladio.builder.BuilderException;
import de.fabiankeller.palladio.builder.resourceenvironment.ContainerBuilder;
import de.fabiankeller.palladio.builder.resourceenvironment.LinkBuilder;
import de.fabiankeller.palladio.builder.resourceenvironment.ResourceEnvironmentBuilder;
import de.fabiankeller.palladio.builder.util.PalladioResourceRepository;
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
    public ResourceEnvironment build() throws BuilderException {
        return this.getReference();
    }

    @Override
    public ContainerBuilder createContainer(final String name) {
        return new ContainerBuilderImpl(this, this.resources);
    }

    @Override
    public LinkBuilder createLink(final String name) {
        return new LinkBuilderImpl(this, this.resources);
    }
}
