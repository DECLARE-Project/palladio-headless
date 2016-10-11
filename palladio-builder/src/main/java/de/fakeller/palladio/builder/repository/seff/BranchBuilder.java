package de.fakeller.palladio.builder.repository.seff;

import de.fakeller.palladio.builder.EntityHierarchicalBuilder;
import org.palladiosimulator.pcm.seff.BranchAction;

/**
 * Used to build {@link BranchAction}s. A branch action provides different branches that are enabled dependening on a
 * certain condition.
 */
public interface BranchBuilder<P extends ResourceDemandBuilder<?>> extends EntityHierarchicalBuilder<BranchBuilder<P>, BranchAction, P> {

    ResourceDemandBuilder<BranchBuilder<P>> createBranch(String name, String branchConditionExpression);
}
