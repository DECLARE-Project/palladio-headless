package de.fakeller.palladio.builder.repository;

import de.fakeller.palladio.builder.EntityHierarchicalBuilder;
import de.fakeller.palladio.builder.repository.seff.ResourceDemandBuilder;
import de.fakeller.palladio.builder.util.random.RandomVariable;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

/**
 * Builds a {@link BasicComponent}
 */
public interface ComponentBuilder extends EntityHierarchicalBuilder<ComponentBuilder, BasicComponent, RepositoryBuilder> {

    /**
     * Add an interface the component provides.
     */
    ComponentBuilder provides(InterfaceBuilder iface);

    /**
     * Add an interface the component requires.
     */
    ComponentBuilder requires(InterfaceBuilder iface);

    /**
     * Creates a {@link ServiceEffectSpecification} for the provided operation.
     */
    ResourceDemandBuilder<ComponentBuilder> withServiceEffectSpecification(SignatureBuilder affectedOperation);

    /**
     * Adds a passive resource to the component.
     */
    PassiveResourceBuilder withPassiveResource(final String name);

    default PassiveResourceBuilder withPassiveResource(final String name, final RandomVariable capacity) {
        return withPassiveResource(name).withCapacity(capacity);
    }
}
