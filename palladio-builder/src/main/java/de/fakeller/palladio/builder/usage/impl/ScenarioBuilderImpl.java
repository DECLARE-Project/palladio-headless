package de.fakeller.palladio.builder.usage.impl;


import de.fakeller.palladio.builder.AbstractHierarchicalBuilder;
import de.fakeller.palladio.builder.system.SystemBuilder;
import de.fakeller.palladio.builder.usage.BehaviourBuilder;
import de.fakeller.palladio.builder.usage.ScenarioBuilder;
import de.fakeller.palladio.builder.usage.UsageBuilder;
import de.fakeller.palladio.builder.util.random.RandomVariable;
import de.fakeller.palladio.builder.util.random.RandomVariableFactory;
import org.palladiosimulator.pcm.usagemodel.ClosedWorkload;
import org.palladiosimulator.pcm.usagemodel.OpenWorkload;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class ScenarioBuilderImpl extends AbstractHierarchicalBuilder<ScenarioBuilder, UsageScenario, UsageBuilder> implements ScenarioBuilder {

    private final SystemBuilder system;

    private BehaviourBuilder behaviour;

    public ScenarioBuilderImpl(final SystemBuilder system, final UsageBuilder belongsTo) {
        super(belongsTo);
        this.system = system;
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
            this.behaviour = new BehaviourBuilderImpl(this.system, this);
        }
        return this.behaviour;
    }

    @Override
    public ScenarioBuilder withOpenWorkload(final double interArrivalTime) {
        // create model
        final OpenWorkload wl = UsagemodelFactory.eINSTANCE.createOpenWorkload();
        wl.setInterArrivalTime_OpenWorkload(RandomVariableFactory.factory().exp(interArrivalTime).get());

        // link model
        this.eModel.setWorkload_UsageScenario(wl);
        wl.setUsageScenario_Workload(this.eModel);

        return this;
    }

    @Override
    public ScenarioBuilder withClosedWorkload(final int population, final RandomVariable thinkTime) {
        // create model
        final ClosedWorkload wl = UsagemodelFactory.eINSTANCE.createClosedWorkload();
        wl.setPopulation(population);
        wl.setThinkTime_ClosedWorkload(thinkTime.get());

        // link model
        this.eModel.setWorkload_UsageScenario(wl);
        wl.setUsageScenario_Workload(this.eModel);

        return this;
    }
}
