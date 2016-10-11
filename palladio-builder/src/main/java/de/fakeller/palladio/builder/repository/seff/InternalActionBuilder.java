package de.fakeller.palladio.builder.repository.seff;

import de.fakeller.palladio.builder.EntityHierarchicalBuilder;
import de.fakeller.palladio.builder.repository.failure.SoftwareInducedFailureBuilder;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;
import org.palladiosimulator.pcm.seff.InternalAction;

/**
 * Used to build {@link InternalAction}s. They refer to resource demands of the component itself.
 */
public interface InternalActionBuilder<P extends ResourceDemandBuilder<?>> extends EntityHierarchicalBuilder<InternalActionBuilder<P>, InternalAction, P> {

    // todo: improve string to strongly typed arguments

    InternalActionBuilder<P> withCpuDemand(String demand);

    InternalActionBuilder<P> withDelayDemand(String demand);

    InternalActionBuilder<P> withHddDemand(String demand);

    /**
     * Attach a {@link SoftwareInducedFailureType} to this internal action.
     */
    InternalActionBuilder<P> withFailure(double probability, SoftwareInducedFailureBuilder failure);
}
