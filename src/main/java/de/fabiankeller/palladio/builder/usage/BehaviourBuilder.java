package de.fabiankeller.palladio.builder.usage;

import de.fabiankeller.palladio.builder.EntityHierarchicalBuilder;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;

/**
 *
 */
public interface BehaviourBuilder extends EntityHierarchicalBuilder<BehaviourBuilder, ScenarioBehaviour, ScenarioBuilder> {
    BehaviourBuilder start();

    BehaviourBuilder stop();

    BehaviourBuilder entryLevelSystemCall();
}
