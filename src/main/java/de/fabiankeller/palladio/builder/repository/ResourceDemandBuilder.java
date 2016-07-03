package de.fabiankeller.palladio.builder.repository;

import de.fabiankeller.palladio.builder.BaseHierarchicalBuilder;
import de.fabiankeller.palladio.builder.EntityHierarchicalBuilder;
import org.palladiosimulator.pcm.seff.*;

/**
 * Used to build {@link ResourceDemandingBehaviour}s.
 * <p>
 * Builds root level {@link ServiceEffectSpecification}s, as well as nested {@link ResourceDemandingBehaviour}s in the
 * control flow of resource demands.
 */
public interface ResourceDemandBuilder extends BaseHierarchicalBuilder<ResourceDemandingSEFF, ComponentBuilder> {

    ResourceDemandBuilder start();

    InternalActionBuilder internalAction(String name);

    ExternalCallBuilder externalCall(String name);

    BranchBuilder branch(String name);

    ResourceDemandBuilder stop();

    /**
     * Used to build {@link InternalAction}s. They refer to resource demands of the component itself.
     */
    interface InternalActionBuilder extends EntityHierarchicalBuilder<InternalActionBuilder, InternalAction, ResourceDemandBuilder> {

        // todo: improve string to strongly typed arguments

        InternalActionBuilder withCpuDemand(String demand);

        InternalActionBuilder withDelayDemand(String demand);

        InternalActionBuilder withHddDemand(String demand);

        InternalActionBuilder withFailure(double probability, String failure);
    }

    /**
     * Used to build {@link ExternalCallAction}s. They refer to resource demands by calling external services.
     */
    interface ExternalCallBuilder extends EntityHierarchicalBuilder<ExternalCallBuilder, ExternalCallAction, ResourceDemandBuilder> {

        // todo: improve string to strongly typed arguments?

        ExternalCallBuilder withVariableUsage(String name, String specification);
    }

    interface BranchBuilder extends EntityHierarchicalBuilder<BranchBuilder, BranchAction, ResourceDemandBuilder> {
        ResourceDemandBuilder createBranch(String name, String branchConditionExpression);
    }
}
