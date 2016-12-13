package de.fakeller.palladio.builder.repository;

import de.fakeller.palladio.builder.EntityHierarchicalBuilder;
import de.fakeller.palladio.builder.util.random.RandomVariable;
import org.palladiosimulator.pcm.repository.PassiveResource;

/**
 * Allows to add {@link PassiveResource}s to a {@link ComponentBuilder}.
 */
public interface PassiveResourceBuilder extends EntityHierarchicalBuilder<PassiveResourceBuilder, PassiveResource, ComponentBuilder> {

    PassiveResourceBuilder withCapacity(RandomVariable capacity);
}
