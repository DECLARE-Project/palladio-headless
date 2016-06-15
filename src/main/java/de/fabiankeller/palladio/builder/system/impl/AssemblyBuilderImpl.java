package de.fabiankeller.palladio.builder.system.impl;

import de.fabiankeller.palladio.builder.repository.ComponentBuilder;
import de.fabiankeller.palladio.builder.repository.InterfaceBuilder;
import de.fabiankeller.palladio.builder.repository.impl.AbstractHierarchicalBuilder;
import de.fabiankeller.palladio.builder.system.AssemblyBuilder;
import de.fabiankeller.palladio.builder.system.SystemBuilder;
import org.palladiosimulator.pcm.core.composition.*;
import org.palladiosimulator.pcm.repository.*;

import java.util.stream.Collectors;

public class AssemblyBuilderImpl extends AbstractHierarchicalBuilder<AssemblyBuilder, AssemblyContext, SystemBuilder> implements AssemblyBuilder {

    public AssemblyBuilderImpl(final SystemBuilder belongsTo, final ComponentBuilder component) {
        super(belongsTo);
        this.eModel.setEncapsulatedComponent__AssemblyContext(component.getReference());
    }

    @Override
    protected AssemblyContext newEModel() {
        return CompositionFactory.eINSTANCE.createAssemblyContext();
    }

    @Override
    protected void registerParent() {
        // AssemblyContext does not know the System it is contained in
    }

    @Override
    public AssemblyBuilder requireFrom(final InterfaceBuilder requiredRole, final AssemblyBuilder providingAssembly, final InterfaceBuilder providedRole) {
        // this context requires, other context provides
        return this.connect(providingAssembly, providedRole, this, requiredRole);
    }

    @Override
    public AssemblyBuilder provideFor(final InterfaceBuilder providedRole, final AssemblyBuilder requiringAssembly, final InterfaceBuilder requiredRole) {
        // this context provides, other context requires
        return connect(this, providedRole, requiringAssembly, requiredRole);
    }

    /**
     * Helper to actually connect provided->required operations of two assemblies.
     */
    private AssemblyBuilder connect(final AssemblyBuilder providingAssembly, final InterfaceBuilder providedRole,
                                    final AssemblyBuilder requiringAssembly, final InterfaceBuilder requiredRole) {

        final AssemblyConnector connector = CompositionFactory.eINSTANCE.createAssemblyConnector();
        connector.setEntityName(String.format("Connector %s -> %s",
                requiringAssembly.getReference().getEncapsulatedComponent__AssemblyContext().getEntityName(),
                providingAssembly.getReference().getEncapsulatedComponent__AssemblyContext().getEntityName()));

        // requiring connector side
        connector.setRequiringAssemblyContext_AssemblyConnector(requiringAssembly.getReference());
        final OperationRequiredRole operationRequiredRole = getRequiredRole(requiringAssembly.getReference().getEncapsulatedComponent__AssemblyContext(), requiredRole);
        connector.setRequiredRole_AssemblyConnector(operationRequiredRole);

        // providing connector side
        connector.setProvidingAssemblyContext_AssemblyConnector(providingAssembly.getReference());
        final OperationProvidedRole operationProvidedRole = getProvidedRole(providingAssembly.getReference().getEncapsulatedComponent__AssemblyContext(), providedRole);
        connector.setProvidedRole_AssemblyConnector(operationProvidedRole);

        // link connector to system
        this.end().getReference().getConnectors__ComposedStructure().add(connector);

        return this;
    }

    /**
     * Locates the {@link OperationProvidedRole} object in the given component exposing the given interface.
     */
    private OperationProvidedRole getProvidedRole(final RepositoryComponent component, final InterfaceBuilder iface) {
        for (final ProvidedRole role : component.getProvidedRoles_InterfaceProvidingEntity()) {
            if (!(role instanceof OperationProvidedRole)) {
                continue;
            }
            final OperationProvidedRole opr = (OperationProvidedRole) role;
            if (opr.getProvidedInterface__OperationProvidedRole().equals(iface.getReference())) {
                return opr;
            }
        }
        throw new RuntimeException(String.format("Could not find providing interface %s in component %s. Only found interfaces [%s].",
                iface.getReference().getEntityName(),
                component.getEntityName(),
                component.getProvidedRoles_InterfaceProvidingEntity()
                        .stream()
                        .map(Role::getEntityName)
                        .collect(Collectors.joining(", "))));
    }

    /**
     * Locates the {@link OperationRequiredRole} object in the given component exposing the given interface.
     */
    private OperationRequiredRole getRequiredRole(final RepositoryComponent component, final InterfaceBuilder iface) {
        for (final RequiredRole role : component.getRequiredRoles_InterfaceRequiringEntity()) {
            if (!(role instanceof OperationRequiredRole)) {
                continue;
            }
            final OperationRequiredRole orr = (OperationRequiredRole) role;
            if (orr.getRequiredInterface__OperationRequiredRole() == iface.getReference()) {
                return orr;
            }
        }
        throw new RuntimeException(String.format("Could not find requiring interface %s in component %s. Only found interfaces [%s].",
                iface.getReference().getEntityName(),
                component.getEntityName(),
                component.getRequiredRoles_InterfaceRequiringEntity()
                        .stream()
                        .map(Role::getEntityName)
                        .collect(Collectors.joining(", "))));
    }


    @Override
    public AssemblyBuilder provideToSystem(final InterfaceBuilder providedRole) {
        // create operation provided role
        final OperationProvidedRole opr = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
        opr.setProvidedInterface__OperationProvidedRole(providedRole.getReference());

        // create delegation connector
        final ProvidedDelegationConnector connector = CompositionFactory.eINSTANCE.createProvidedDelegationConnector();
        connector.setAssemblyContext_ProvidedDelegationConnector(this.getReference());
        connector.setInnerProvidedRole_ProvidedDelegationConnector(getProvidedRole(this.getReference().getEncapsulatedComponent__AssemblyContext(), providedRole));
        connector.setOuterProvidedRole_ProvidedDelegationConnector(opr);

        // link provided role to system
        this.end().getReference().getProvidedRoles_InterfaceProvidingEntity().add(opr);
        this.end().getReference().getConnectors__ComposedStructure().add(connector);

        return this;
    }

    @Override
    public AssemblyBuilder requireFromSystem(final InterfaceBuilder requiredRole) {
        // create operation required role
        final OperationRequiredRole orr = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
        orr.setRequiredInterface__OperationRequiredRole(requiredRole.getReference());

        // create delegation connector
        final RequiredDelegationConnector connector = CompositionFactory.eINSTANCE.createRequiredDelegationConnector();
        connector.setAssemblyContext_RequiredDelegationConnector(this.getReference());
        connector.setInnerRequiredRole_RequiredDelegationConnector(getRequiredRole(this.getReference().getEncapsulatedComponent__AssemblyContext(), requiredRole));
        connector.setOuterRequiredRole_RequiredDelegationConnector(orr);

        // link required role to system
        this.end().getReference().getRequiredRoles_InterfaceRequiringEntity().add(orr);
        this.end().getReference().getConnectors__ComposedStructure().add(connector);

        return this;
    }
}
