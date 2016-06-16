package de.fabiankeller.palladio.builder.usage.impl;

import de.fabiankeller.palladio.builder.AbstractHierarchicalBuilder;
import de.fabiankeller.palladio.builder.BuilderException;
import de.fabiankeller.palladio.builder.repository.SignatureBuilder;
import de.fabiankeller.palladio.builder.system.SystemBuilder;
import de.fabiankeller.palladio.builder.usage.BehaviourBuilder;
import de.fabiankeller.palladio.builder.usage.ScenarioBuilder;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class BehaviourBuilderImpl extends AbstractHierarchicalBuilder<BehaviourBuilder, ScenarioBehaviour, ScenarioBuilder> implements BehaviourBuilder {

    private final SystemBuilder system;

    private AbstractUserAction predecessor;

    public BehaviourBuilderImpl(final SystemBuilder system, final ScenarioBuilder belongsTo) {
        super(belongsTo);
        this.system = system;
    }


    @Override
    protected ScenarioBehaviour newEModel() {
        return UsagemodelFactory.eINSTANCE.createScenarioBehaviour();
    }

    @Override
    protected void registerParent() {
        this.eModel.setUsageScenario_SenarioBehaviour(this.belongsTo.getReference());
        this.belongsTo.getReference().setScenarioBehaviour_UsageScenario(this.eModel);
    }

    private BehaviourBuilder enqueueAction(final AbstractUserAction action) {
        // double-link
        if (null != this.predecessor) {
            action.setPredecessor(this.predecessor);
            this.predecessor.setSuccessor(action);
        }

        // link to parent model
        this.eModel.getActions_ScenarioBehaviour().add(action);
        action.setScenarioBehaviour_AbstractUserAction(this.eModel);

        this.predecessor = action;
        return this;
    }

    private void assertPredecessor() {
        if (null == this.predecessor) {
            throw new BuilderException(this, "The action you are trying to create requires a previous action to be registered. If you have not set an action yet, begin with the start() action.");
        }
    }

    @Override
    public BehaviourBuilder start() {
        return enqueueAction(UsagemodelFactory.eINSTANCE.createStart());
    }

    @Override
    public BehaviourBuilder entryLevelSystemCall(final SignatureBuilder signature, final int priority) {
        assertPredecessor();
        final EntryLevelSystemCall call = UsagemodelFactory.eINSTANCE.createEntryLevelSystemCall();
        call.setPriority(priority);
        call.setOperationSignature__EntryLevelSystemCall(signature.getReference());
        call.setProvidedRole_EntryLevelSystemCall(findRoleProvidingSignature(signature));
        return enqueueAction(call);
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
    public BehaviourBuilder stop() {
        assertPredecessor();
        return enqueueAction(UsagemodelFactory.eINSTANCE.createStop());
    }
}
