package de.fabiankeller.palladio.builder.usage.impl;

import de.fabiankeller.palladio.builder.system.SystemBuilder;
import de.fabiankeller.palladio.builder.usage.ScenarioBuilder;
import de.fabiankeller.palladio.builder.usage.UsageBuilder;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class UsageBuilderImpl implements UsageBuilder {

    private final UsageModel eModel = UsagemodelFactory.eINSTANCE.createUsageModel();

    private final SystemBuilder system;

    public UsageBuilderImpl(final SystemBuilder system) {
        this.system = system;
    }

    @Override
    public ScenarioBuilder createScenario(final String name) {
        return new ScenarioBuilderImpl(this.system, this);
    }

    @Override
    public UsageModel getReference() {
        return this.eModel;
    }
}
