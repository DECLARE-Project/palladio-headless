package de.fakeller.palladio.builder.repository.seff;

import de.fakeller.palladio.builder.BaseBuilder;
import de.fakeller.palladio.builder.BaseHierarchicalBuilder;
import de.fakeller.palladio.builder.repository.SignatureBuilder;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

/**
 * Used to build {@link ResourceDemandingBehaviour}s.
 * <p>
 * Builds root level {@link ServiceEffectSpecification}s, as well as nested {@link ResourceDemandingBehaviour}s in the
 * control flow of resource demands.
 */
public interface ResourceDemandBuilder<PARENT extends BaseBuilder<?>> extends BaseHierarchicalBuilder<ResourceDemandingBehaviour, PARENT> {

    ResourceDemandBuilder<PARENT> start();


    InternalActionBuilder<ResourceDemandBuilder<PARENT>> internalAction();

    default InternalActionBuilder<ResourceDemandBuilder<PARENT>> internalAction(final String name) {
        return internalAction().withEntityName(name);
    }


    ExternalCallBuilder<ResourceDemandBuilder<PARENT>> externalCall();

    default ExternalCallBuilder<ResourceDemandBuilder<PARENT>> externalCall(final SignatureBuilder toSignature) {
        return externalCall().withCalledService(toSignature);
    }

    default ExternalCallBuilder<ResourceDemandBuilder<PARENT>> externalCall(final String name, final SignatureBuilder toSignature) {
        return externalCall(toSignature).withEntityName(name);
    }


    BranchBuilder<ResourceDemandBuilder<PARENT>> branch();

    default BranchBuilder<ResourceDemandBuilder<PARENT>> branch(final String name) {
        return branch().withEntityName(name);
    }


    ResourceDemandBuilder<PARENT> stop();
}
