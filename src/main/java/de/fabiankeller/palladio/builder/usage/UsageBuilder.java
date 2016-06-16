package de.fabiankeller.palladio.builder.usage;

import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

/**
 * Builds {@link UsageModel}s.
 */
public interface UsageBuilder {

    /**
     * Add a new {@link UsageScenario} to this model.
     */
    ScenarioBuilder createScenario(String name);
}
