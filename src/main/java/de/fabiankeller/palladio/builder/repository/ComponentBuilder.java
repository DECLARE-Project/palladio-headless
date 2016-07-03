package de.fabiankeller.palladio.builder.repository;

import de.fabiankeller.palladio.builder.EntityHierarchicalBuilder;
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
    ResourceDemandBuilder withServiceEffectSpecification(SignatureBuilder affectedOperation);
}
