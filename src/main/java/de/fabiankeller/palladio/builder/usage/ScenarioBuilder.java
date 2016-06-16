package de.fabiankeller.palladio.builder.usage;

import de.fabiankeller.palladio.builder.EntityBuilder;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

/**
 * Builds {@link UsageScenario}s.
 */
public interface ScenarioBuilder extends EntityBuilder<ScenarioBuilder, UsageScenario> {

    BehaviourBuilder behaviour();

    ScenarioBuilder withOpenWorkload(double interArrivalTime);
}
