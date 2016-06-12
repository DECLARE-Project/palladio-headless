package de.fabiankeller.palladio.builder.repository.impl;

import de.fabiankeller.palladio.builder.repository.InterfaceBuilder;
import de.fabiankeller.palladio.builder.repository.RepositoryBuilder;
import de.fabiankeller.palladio.builder.repository.SignatureBuilder;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import java.util.ArrayList;
import java.util.List;


public class InterfaceBuilderImpl extends AbstractHierarchicalBuilder<InterfaceBuilder, OperationInterface, RepositoryBuilder> implements InterfaceBuilder {

    private final List<SignatureBuilder> operations = new ArrayList<>();

    InterfaceBuilderImpl(final RepositoryBuilder repositoryBuilder) {
        super(repositoryBuilder);
    }

    @Override
    protected OperationInterface newEModel() {
        return RepositoryFactory.eINSTANCE.createOperationInterface();
    }

    @Override
    protected void registerParent() {
        this.eModel.setRepository__Interface(this.belongsTo.getReference());
    }


    @Override
    public InterfaceBuilder withParentInterface(final InterfaceBuilder interfaceBuilder) {
        this.eModel.getParentInterfaces__Interface().add(interfaceBuilder.getReference());
        return this;
    }

    @Override
    public SignatureBuilder createOperation(final String name) {
        // create builder
        final SignatureBuilder operation = new SignatureBuilderImpl(this).withEntityName(name);
        this.operations.add(operation);

        // link model
        this.eModel.getSignatures__OperationInterface().add(operation.getReference());

        return operation;
    }
}
