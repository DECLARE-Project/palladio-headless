package de.fabiankeller.palladio.builder.usage;

import de.fabiankeller.palladio.builder.EntityHierarchicalBuilder;
import de.fabiankeller.palladio.builder.repository.SignatureBuilder;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;

/**
 *
 */
public interface BehaviourBuilder extends EntityHierarchicalBuilder<BehaviourBuilder, ScenarioBehaviour, ScenarioBuilder> {
    BehaviourBuilder start();

    default BehaviourBuilder entryLevelSystemCall(final SignatureBuilder signature) {
        return entryLevelSystemCall(signature, 0);
    }

    BehaviourBuilder entryLevelSystemCall(SignatureBuilder signature, int priority);

    BehaviourBuilder stop();

}
