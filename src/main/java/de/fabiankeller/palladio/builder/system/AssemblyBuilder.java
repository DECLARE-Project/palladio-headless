package de.fabiankeller.palladio.builder.system;

import de.fabiankeller.palladio.builder.HierarchicalEntityBuilder;
import de.fabiankeller.palladio.builder.repository.InterfaceBuilder;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;

/**
 * Builds a Palladio {@link AssemblyContext} that can be connected to different parts of the system.
 */
public interface AssemblyBuilder extends HierarchicalEntityBuilder<AssemblyBuilder, AssemblyContext, SystemBuilder> {

    /**
     * Connect the given assembly to a role required by the component in this assembly.
     *
     * @param requiredRole      the role required by the component in this assembly
     * @param providingAssembly the assembly context that can satisfy the required role
     * @param providedRole      the provided role that satisfies the required role
     * @return provides fluent interface
     */
    AssemblyBuilder connectToRequiredRole(InterfaceBuilder requiredRole, AssemblyBuilder providingAssembly, InterfaceBuilder providedRole);

    /**
     * Connect the given assembly to a role provided by the component in this assembly.
     *
     * @param providedRole      the role provided by the component in this assembly
     * @param requiringAssembly the assembly context that requires the provided role
     * @param requiredRole      the role of the other component that requires the provided interface
     * @return provides fluent interface
     */
    AssemblyBuilder connectToProvidedRole(InterfaceBuilder providedRole, AssemblyBuilder requiringAssembly, InterfaceBuilder requiredRole);

    /**
     * The system provides the specified interface. Each system must provide at least one interface to be valid.
     */
    AssemblyBuilder provideToSystem(InterfaceBuilder providedRole);

    /**
     * The system requires the specified interface.
     */
    AssemblyBuilder requiredBySystem(InterfaceBuilder requiredRole);
}
