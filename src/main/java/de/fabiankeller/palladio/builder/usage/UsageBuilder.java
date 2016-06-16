package de.fabiankeller.palladio.builder.usage;

import de.fabiankeller.palladio.builder.BaseBuilder;
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
