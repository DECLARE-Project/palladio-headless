package de.fabiankeller.palladio.builder.repository;

import de.fabiankeller.palladio.builder.BaseHierarchicalBuilder;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

/**
 * Used to build {@link ServiceEffectSpecification}s.
 */
public interface ServiceEffectSpecificationBuilder extends BaseHierarchicalBuilder<ResourceDemandingSEFF, ComponentBuilder> {

}
