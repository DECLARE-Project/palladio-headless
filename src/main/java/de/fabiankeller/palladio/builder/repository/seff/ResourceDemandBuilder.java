package de.fabiankeller.palladio.builder.repository.seff;

import de.fabiankeller.palladio.builder.BaseBuilder;
import de.fabiankeller.palladio.builder.BaseHierarchicalBuilder;
import de.fabiankeller.palladio.builder.EntityHierarchicalBuilder;
import org.palladiosimulator.pcm.seff.*;

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

    default ExternalCallBuilder<ResourceDemandBuilder<PARENT>> externalCall(final String name) {
        return externalCall().withEntityName(name);
    }


    BranchBuilder<ResourceDemandBuilder<PARENT>> branch();

    default BranchBuilder<ResourceDemandBuilder<PARENT>> branch(final String name) {
        return branch().withEntityName(name);
    }


    ResourceDemandBuilder<PARENT> stop();


    /**
     * Used to build {@link InternalAction}s. They refer to resource demands of the component itself.
     */
    interface InternalActionBuilder<P extends ResourceDemandBuilder<?>> extends EntityHierarchicalBuilder<InternalActionBuilder<P>, InternalAction, P> {

        // todo: improve string to strongly typed arguments

        InternalActionBuilder<P> withCpuDemand(String demand);

        InternalActionBuilder<P> withDelayDemand(String demand);

        InternalActionBuilder<P> withHddDemand(String demand);

        InternalActionBuilder<P> withFailure(double probability, String failure);
    }

    /**
     * Used to build {@link ExternalCallAction}s. They refer to resource demands by calling external services.
     */
    interface ExternalCallBuilder<P extends ResourceDemandBuilder<?>> extends EntityHierarchicalBuilder<ExternalCallBuilder<P>, ExternalCallAction, P> {

        // todo: improve string to strongly typed arguments?

        ExternalCallBuilder<P> withVariableUsage(String name, String specification);
    }

    interface BranchBuilder<P extends ResourceDemandBuilder<?>> extends EntityHierarchicalBuilder<BranchBuilder<P>, BranchAction, P> {
        ResourceDemandBuilder<ResourceDemandBuilder<P>> createBranch(String name, String branchConditionExpression);
    }
}
