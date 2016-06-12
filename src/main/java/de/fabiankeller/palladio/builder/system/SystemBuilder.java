package de.fabiankeller.palladio.builder.system;

import de.fabiankeller.palladio.builder.EntityBuilder;
import de.fabiankeller.palladio.builder.HierarchicalEntityBuilder;
import de.fabiankeller.palladio.builder.repository.ComponentBuilder;
import de.fabiankeller.palladio.builder.repository.InterfaceBuilder;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
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

    /**
     * Builds a Palladio {@link AssemblyContext} that can be connected to different parts of the system.
     */
    interface AssemblyBuilder extends HierarchicalEntityBuilder<AssemblyBuilder, AssemblyContext, SystemBuilder> {

        /**
         * Connect the assembly to serve a required role by the component in this assembly.
         *
         * @param requiredRole the role required by the component in this assembly context
         * @param providedBy   the assembly context that can satisfy the required role
         * @param to           the provided role that satisfies the required role
         * @return provides fluent interface
         */
        AssemblyBuilder connectRequiredRole(InterfaceBuilder requiredRole, AssemblyBuilder providedBy, InterfaceBuilder to);

        /**
         * Connects a provided role of the assembly to a required role by another assembly context.
         *
         * @param providedRole the role provided by the component in this assembly context
         * @param requiredBy   the assembly context that requires the provided role
         * @param to           the role of the other component that requires the provided interface
         * @return provides fluent interface
         */
        AssemblyBuilder connectProvidedRole(InterfaceBuilder providedRole, AssemblyBuilder requiredBy, InterfaceBuilder to);

        /**
         * The system provides the specified interface. Each system must provide at least one interface to be valid.
         */
        AssemblyBuilder provideToSystem(InterfaceBuilder provided);

        /**
         * The system requires the specified interface.
         */
        AssemblyBuilder requiredBySystem(InterfaceBuilder required);
    }
}
