package de.fabiankeller.palladio.builder.usage.impl;

import de.fabiankeller.palladio.builder.AbstractHierarchicalBuilder;
import de.fabiankeller.palladio.builder.BuilderException;
import de.fabiankeller.palladio.builder.repository.SignatureBuilder;
import de.fabiankeller.palladio.builder.system.SystemBuilder;
import de.fabiankeller.palladio.builder.usage.BehaviourBuilder;
import de.fabiankeller.palladio.builder.usage.EntryLevelSystemCallBuilder;
import de.fabiankeller.palladio.builder.util.impl.VariableUsageFactoryImpl;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class EntryLevelSystemCallBuilderImpl
        extends AbstractHierarchicalBuilder<EntryLevelSystemCallBuilder, EntryLevelSystemCall, BehaviourBuilder>
        implements EntryLevelSystemCallBuilder {

    private final SystemBuilder system;
    private final SignatureBuilder signature;

    public EntryLevelSystemCallBuilderImpl(final BehaviourBuilder belongsTo, final SystemBuilder system, final SignatureBuilder signature) {
        super(belongsTo);
        this.system = system;
        this.signature = signature;
    }

    @Override
    protected EntryLevelSystemCall newEModel() {
        return UsagemodelFactory.eINSTANCE.createEntryLevelSystemCall();

    }

    @Override
    protected void registerParent() {
        this.eModel.setOperationSignature__EntryLevelSystemCall(this.signature.getReference());
        this.eModel.setProvidedRole_EntryLevelSystemCall(findRoleProvidingSignature(this.signature));
    }

    private OperationProvidedRole findRoleProvidingSignature(final SignatureBuilder signature) {
        for (final ProvidedRole providedRole : this.system.getReference().getProvidedRoles_InterfaceProvidingEntity()) {
            if (!(providedRole instanceof OperationProvidedRole)) {
                continue;
            }
            final OperationProvidedRole opRole = (OperationProvidedRole) providedRole;
            for (final OperationSignature other : opRole.getProvidedInterface__OperationProvidedRole().getSignatures__OperationInterface()) {
                if (other == signature.getReference()) {
                    return opRole;
                }
            }
        }
        throw new BuilderException(this, "Could not find signature in provided roles of the system. Did you forget to call AssemblyBuilder.provideToSystem()?");
    }

    @Override
    public EntryLevelSystemCallBuilder withPriority(final int priority) {
        this.eModel.setPriority(priority);
        return this;
    }

    @Override
    public EntryLevelSystemCallBuilder withInputVariableUsage(final String name, final String specification) {
        // create usage model
        final VariableUsage varusg = new VariableUsageFactoryImpl().valueUsage(name, specification);

        // link model
        varusg.setEntryLevelSystemCall_InputParameterUsage(this.eModel);
        this.eModel.getInputParameterUsages_EntryLevelSystemCall().add(varusg);

        return this;
    }

    @Override
    public EntryLevelSystemCallBuilder withOutputVariableUsage(final String name, final String specification) {
        // create usage model
        final VariableUsage varusg = new VariableUsageFactoryImpl().valueUsage(name, specification);

        // link model
        varusg.setEntryLevelSystemCall_OutputParameterUsage(this.eModel);
        this.eModel.getOutputParameterUsages_EntryLevelSystemCall().add(varusg);

        return this;
    }
}
