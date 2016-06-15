package de.fabiankeller.palladio.builder.resourceenvironment;

import de.fabiankeller.palladio.builder.NamedBuilder;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;

/**
 * Used to build {@link ResourceEnvironment}s.
 */
public interface ResourceEnvironmentBuilder extends NamedBuilder<ResourceEnvironmentBuilder, ResourceEnvironment> {

    /**
     * Add a processing container to this resource environment.
     */
    ContainerBuilder createContainer(String name);

    /**
     * Add a communication channel to this resource environment so that linked containers can exchange data.
     */
    LinkBuilder createLink(String name);
}
