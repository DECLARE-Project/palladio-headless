package de.fabiankeller.palladio.builder.usage.impl;

import de.fabiankeller.palladio.builder.usage.ScenarioBuilder;
import de.fabiankeller.palladio.builder.usage.UsageBuilder;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class UsageBuilderImpl implements UsageBuilder {

    private final UsageModel eModel = UsagemodelFactory.eINSTANCE.createUsageModel();

    @Override
    public ScenarioBuilder createScenario(final String name) {
        return new ScenarioBuilderImpl(this);
    }

    @Override
    public UsageModel getReference() {
        return this.eModel;
    }
}
