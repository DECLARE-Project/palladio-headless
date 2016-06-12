package de.fabiankeller.palladio.builder.system.impl;

import de.fabiankeller.palladio.builder.EntityViolationException;
import de.fabiankeller.palladio.builder.repository.ComponentBuilder;
import de.fabiankeller.palladio.builder.repository.InterfaceBuilder;
import de.fabiankeller.palladio.builder.system.SystemBuilder;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.system.SystemFactory;

public class SystemBuilderImpl implements SystemBuilder {

    private final System eSystem;

    public SystemBuilderImpl() {
        this.eSystem = SystemFactory.eINSTANCE.createSystem();
    }

    @Override
    public AssemblyBuilder assemble(final ComponentBuilder component) {
        // fixme: implement
        return null;
    }

    /**
     * Should be used by {@link AssemblyBuilder#provideToSystem(InterfaceBuilder)}.
     */
    protected SystemBuilder provides(final InterfaceBuilder providedInterface) {
        // create model
        final OperationProvidedRole providedRole = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
        providedRole.setProvidedInterface__OperationProvidedRole(providedInterface.getReference());

        // link model
        providedRole.setProvidingEntity_ProvidedRole(this.eSystem);
        this.eSystem.getProvidedRoles_InterfaceProvidingEntity().add(providedRole);

        return this;
    }

    /**
     * Should be used by {@link AssemblyBuilder#requiredBySystem(InterfaceBuilder)}.
     */
    protected SystemBuilder requires(final InterfaceBuilder requiredInterface) {
        // create model
        final OperationRequiredRole requiredRole = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
        requiredRole.setRequiredInterface__OperationRequiredRole(requiredInterface.getReference());

        // link model
        requiredRole.setRequiringEntity_RequiredRole(this.eSystem);
        this.eSystem.getRequiredRoles_InterfaceRequiringEntity().add(requiredRole);

        return this;
    }

    @Override
    public System getReference() {
        return this.eSystem;
    }

    @Override
    public System build() throws EntityViolationException {
        return this.getReference();
    }


}
