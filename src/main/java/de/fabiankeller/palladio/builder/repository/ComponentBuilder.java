package de.fabiankeller.palladio.builder.repository;

import de.fabiankeller.palladio.builder.EntityHierarchicalBuilder;
import de.fabiankeller.palladio.builder.repository.seff.ResourceDemandBuilder;
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
}
