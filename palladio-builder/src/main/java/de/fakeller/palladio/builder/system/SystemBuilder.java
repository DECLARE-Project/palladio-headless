package de.fakeller.palladio.builder.system;

import de.fakeller.palladio.builder.EntityBuilder;
import de.fakeller.palladio.builder.repository.ComponentBuilder;
import org.palladiosimulator.pcm.system.System;

/**
 * Used to build {@link System} models.
 */
public interface SystemBuilder extends EntityBuilder<SystemBuilder, System> {

    /**
     * Start to assemble a component to the system. Any component may be assembled multiple times. Connections are
     * created between assemblies.
     */
    AssemblyBuilder assemble(ComponentBuilder component);

}
