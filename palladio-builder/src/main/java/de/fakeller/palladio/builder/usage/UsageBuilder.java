package de.fakeller.palladio.builder.usage;

import de.fakeller.palladio.builder.BaseBuilder;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

/**
 * Builds {@link UsageModel}s.
 */
public interface UsageBuilder extends BaseBuilder<UsageModel> {

    /**
     * Add a new {@link UsageScenario} to this model.
     */
    ScenarioBuilder createScenario(String name);
}
