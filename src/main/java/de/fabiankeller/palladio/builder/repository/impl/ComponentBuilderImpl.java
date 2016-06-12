package de.fabiankeller.palladio.builder.repository.impl;

import de.fabiankeller.palladio.builder.repository.ComponentBuilder;
import de.fabiankeller.palladio.builder.repository.InterfaceBuilder;
import de.fabiankeller.palladio.builder.repository.RepositoryBuilder;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryFactory;


public class ComponentBuilderImpl extends AbstractHierarchicalBuilder<ComponentBuilder, BasicComponent, RepositoryBuilder> implements ComponentBuilder {


    ComponentBuilderImpl(final RepositoryBuilder belongsTo) {
        super(belongsTo);
    }

    @Override
    protected BasicComponent newEModel() {
        return RepositoryFactory.eINSTANCE.createBasicComponent();
    }

    @Override
    protected void registerParent() {
        this.eModel.setRepository__RepositoryComponent(this.belongsTo.getReference());
    }


    @Override
    public ComponentBuilder provides(final InterfaceBuilder iface) {
        // create model
        final OperationProvidedRole provided = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
        provided.setProvidedInterface__OperationProvidedRole(iface.getReference());

        // link model
        this.eModel.getProvidedRoles_InterfaceProvidingEntity().add(provided);

        return this;
    }

    @Override
    public ComponentBuilder requires(final InterfaceBuilder iface) {
        // create model
        final OperationRequiredRole provided = RepositoryFactory.eINSTANCE.createOperationRequiredRole();
        provided.setRequiredInterface__OperationRequiredRole(iface.getReference());

        // link model
        this.eModel.getRequiredRoles_InterfaceRequiringEntity().add(provided);

        return this;
    }
}
