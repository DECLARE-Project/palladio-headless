package de.fabiankeller.palladio.builder.repository.seff;

import de.fabiankeller.palladio.builder.EntityHierarchicalBuilder;
import org.palladiosimulator.pcm.seff.InternalAction;

/**
 * Used to build {@link InternalAction}s. They refer to resource demands of the component itself.
 */
public interface InternalActionBuilder<P extends ResourceDemandBuilder<?>> extends EntityHierarchicalBuilder<InternalActionBuilder<P>, InternalAction, P> {

    // todo: improve string to strongly typed arguments

    InternalActionBuilder<P> withCpuDemand(String demand);

    InternalActionBuilder<P> withDelayDemand(String demand);

    InternalActionBuilder<P> withHddDemand(String demand);

    InternalActionBuilder<P> withFailure(double probability, String failure);
}
