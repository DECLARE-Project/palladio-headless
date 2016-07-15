package de.fabiankeller.palladio.builder.usage;

import de.fabiankeller.palladio.builder.EntityHierarchicalBuilder;
import de.fabiankeller.palladio.builder.repository.SignatureBuilder;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;

/**
 *
 */
public interface BehaviourBuilder extends EntityHierarchicalBuilder<BehaviourBuilder, ScenarioBehaviour, ScenarioBuilder> {
    BehaviourBuilder start();

    EntryLevelSystemCallBuilder entryLevelSystemCall(final SignatureBuilder signature);

    default EntryLevelSystemCallBuilder entryLevelSystemCall(final String name, final SignatureBuilder signature) {
        return entryLevelSystemCall(signature).withEntityName(name);
    }

    BehaviourBuilder stop();

}
