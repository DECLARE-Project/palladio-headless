package de.fakeller.palladio.builder.usage.impl;

import de.fakeller.palladio.builder.AbstractHierarchicalBuilder;
import de.fakeller.palladio.builder.BuilderException;
import de.fakeller.palladio.builder.repository.SignatureBuilder;
import de.fakeller.palladio.builder.system.SystemBuilder;
import de.fakeller.palladio.builder.usage.BehaviourBuilder;
import de.fakeller.palladio.builder.usage.EntryLevelSystemCallBuilder;
import de.fakeller.palladio.builder.usage.ScenarioBuilder;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
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
    public EntryLevelSystemCallBuilder entryLevelSystemCall(final SignatureBuilder signature) {
        assertPredecessor();
        final EntryLevelSystemCallBuilder builder = new EntryLevelSystemCallBuilderImpl(this, this.system, signature);
        enqueueAction(builder.getReference());
        return builder;
    }


    @Override
    public BehaviourBuilder stop() {
        assertPredecessor();
        return enqueueAction(UsagemodelFactory.eINSTANCE.createStop());
    }
}
