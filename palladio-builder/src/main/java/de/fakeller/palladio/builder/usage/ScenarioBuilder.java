package de.fakeller.palladio.builder.usage;

import de.fakeller.palladio.builder.EntityHierarchicalBuilder;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;

/**
 * Builds {@link UsageScenario}s.
 */
public interface ScenarioBuilder extends EntityHierarchicalBuilder<ScenarioBuilder, UsageScenario, UsageBuilder> {

    BehaviourBuilder withBehaviour();

    ScenarioBuilder withOpenWorkload(double interArrivalTime);
}
