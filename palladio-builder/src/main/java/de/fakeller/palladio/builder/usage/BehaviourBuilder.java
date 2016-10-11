package de.fakeller.palladio.builder.usage;

import de.fakeller.palladio.builder.EntityHierarchicalBuilder;
import de.fakeller.palladio.builder.repository.SignatureBuilder;
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
