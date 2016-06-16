package de.fabiankeller.palladio.builder.usage;

import de.fabiankeller.palladio.builder.HierarchicalEntityBuilder;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;

/**
 *
 */
public interface BehaviourBuilder extends HierarchicalEntityBuilder<BehaviourBuilder, ScenarioBehaviour, ScenarioBuilder> {
    BehaviourBuilder start();

    BehaviourBuilder stop();

    BehaviourBuilder entryLevelSystemCall();
}
