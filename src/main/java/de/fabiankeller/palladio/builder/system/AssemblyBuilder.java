package de.fabiankeller.palladio.builder.system;

import de.fabiankeller.palladio.builder.EntityHierarchicalBuilder;
import de.fabiankeller.palladio.builder.repository.InterfaceBuilder;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;

/**
 * Builds a Palladio {@link AssemblyContext} that can be connected to different parts of the system.
 */
public interface AssemblyBuilder extends EntityHierarchicalBuilder<AssemblyBuilder, AssemblyContext, SystemBuilder> {

    /**
     * Connect the given assembly to an interface required by the component in this assembly.
     * <p>
     * Note: use this method in case the requiredRole and providedRole are not the same (i.e. through inheritance).
     * Otherwise use {@link AssemblyBuilder#requireFrom(AssemblyBuilder, InterfaceBuilder)}.
     *
     * @param requiredRole      the role required by the component in this assembly
     * @param providingAssembly the assembly context that can satisfy the required role
     * @param providedRole      the provided role that satisfies the required role
     * @return provides fluent interface
     */
    AssemblyBuilder requireFrom(InterfaceBuilder requiredRole, AssemblyBuilder providingAssembly, InterfaceBuilder providedRole);

    /**
     * Connect the given assembly to an interface required by the component in this assembly.
     *
     * @param providingAssembly the assembly context that can satisfy the required role
     * @param iface             the shared interface
     * @return provides fluent interface
     */
    default AssemblyBuilder requireFrom(final AssemblyBuilder providingAssembly, final InterfaceBuilder iface) {
        return requireFrom(iface, providingAssembly, iface);
    }

    /**
     * Connect the given assembly to a role provided by the component in this assembly.
     * <p>
     * Note: use this method in case the requiredRole and providedRole are not the same (i.e. through inheritance).
     * Otherwise use {@link AssemblyBuilder#provideFor(AssemblyBuilder, InterfaceBuilder)}.
     *
     * @param providedRole      the role provided by the component in this assembly
     * @param requiringAssembly the assembly context that requires the provided role
     * @param requiredRole      the role of the other component that requires the provided interface
     * @return provides fluent interface
     */
    AssemblyBuilder provideFor(InterfaceBuilder providedRole, AssemblyBuilder requiringAssembly, InterfaceBuilder requiredRole);

    /**
     * Connect the given assembly to an interface provided by the component in this assembly.
     *
     * @param requiringAssembly the assembly context that requires the provided role
     * @param iface             the shared interface
     * @return provides fluent interface
     */
    default AssemblyBuilder provideFor(final AssemblyBuilder requiringAssembly, final InterfaceBuilder iface) {
        return provideFor(iface, requiringAssembly, iface);
    }

    /**
     * The system provides the specified interface. Each system must provide at least one interface to be valid.
     */
    AssemblyBuilder provideToSystem(InterfaceBuilder providedRole);

    /**
     * The system requires the specified interface.
     */
    AssemblyBuilder requireFromSystem(InterfaceBuilder requiredRole);
}
