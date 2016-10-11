package de.fakeller.palladio.builder.resourceenvironment;

import de.fakeller.palladio.builder.EntityHierarchicalBuilder;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;

/**
 * Used to build {@link ResourceContainer}s.
 */
public interface ContainerBuilder extends EntityHierarchicalBuilder<ContainerBuilder, ResourceContainer, ResourceEnvironmentBuilder> {
    /**
     * Nest this createContainer within the given parent createContainer.
     */
    ContainerBuilder setParentContainer(ContainerBuilder parent);

    /**
     * Specify the CPU of this {@link ResourceContainer}.
     */
    ProcessingResourceBuilder withCpu(double processingRate);

    /**
     * Specify the HDD of this {@link ResourceContainer}.
     */
    ProcessingResourceBuilder withHdd();

    /**
     * Specify the DELAY of this {@link ResourceContainer}.
     */
    ProcessingResourceBuilder withDelay();
}
