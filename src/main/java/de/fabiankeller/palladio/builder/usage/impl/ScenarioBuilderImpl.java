package de.fabiankeller.palladio.builder.usage.impl;


import de.fabiankeller.palladio.builder.AbstractHierarchicalBuilder;
import de.fabiankeller.palladio.builder.usage.BehaviourBuilder;
import de.fabiankeller.palladio.builder.usage.ScenarioBuilder;
import de.fabiankeller.palladio.builder.usage.UsageBuilder;
import de.fabiankeller.palladio.builder.util.RandomVariableFactory;
import org.palladiosimulator.pcm.usagemodel.OpenWorkload;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class ScenarioBuilderImpl extends AbstractHierarchicalBuilder<ScenarioBuilder, UsageScenario, UsageBuilder> implements ScenarioBuilder {

    private BehaviourBuilder behaviour;

    public ScenarioBuilderImpl(final UsageBuilder belongsTo) {
        super(belongsTo);
    }

    @Override
    protected UsageScenario newEModel() {
        return UsagemodelFactory.eINSTANCE.createUsageScenario();
    }

    @Override
    protected void registerParent() {
        this.eModel.setUsageModel_UsageScenario(this.belongsTo.getReference());
        this.belongsTo.getReference().getUsageScenario_UsageModel().add(this.eModel);
    }

    @Override
    public BehaviourBuilder withBehaviour() {
        if (null == this.behaviour) {
            this.behaviour = new BehaviourBuilderImpl(this);
        }
        return this.behaviour;
    }

    @Override
    public ScenarioBuilder withOpenWorkload(final double interArrivalTime) {
        // create model
        final OpenWorkload wl = UsagemodelFactory.eINSTANCE.createOpenWorkload();
        wl.setInterArrivalTime_OpenWorkload(RandomVariableFactory.exp(interArrivalTime));

        // link model
        this.eModel.setWorkload_UsageScenario(wl);
        wl.setUsageScenario_Workload(this.eModel);

        return this;
    }
}
