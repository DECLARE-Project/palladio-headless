package de.fabiankeller.palladio.builder.resourceenvironment;

import de.fabiankeller.palladio.builder.HierarchicalEntityBuilder;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;

/**
 * Used to build {@link ResourceContainer}s.
 */
public interface ContainerBuilder extends HierarchicalEntityBuilder<ContainerBuilder, ResourceContainer, ResourceEnvironmentBuilder> {
    /**
     * Nest this createContainer within the given parent createContainer.
     */
    ContainerBuilder setParentContainer(ContainerBuilder parent);

    /**
     * Specify the CPU of this {@link ResourceContainer}.
     */
    ProcessingResourceBuilder withCpu();

    /**
     * Specify the HDD of this {@link ResourceContainer}.
     */
    ProcessingResourceBuilder withHdd();

    /**
     * Specify the DELAY of this {@link ResourceContainer}.
     */
    ProcessingResourceBuilder withDelay();
}
