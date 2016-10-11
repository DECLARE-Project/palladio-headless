package de.fakeller.palladio.builder.repository.seff.impl;

import de.fakeller.palladio.builder.repository.seff.BranchBuilder;
import de.fakeller.palladio.builder.repository.seff.ResourceDemandBuilder;
import de.fakeller.palladio.builder.util.RandomVariableFactory;
import org.palladiosimulator.pcm.seff.BranchAction;
import org.palladiosimulator.pcm.seff.GuardedBranchTransition;
import org.palladiosimulator.pcm.seff.SeffFactory;

public class BranchBuilderImpl<PARENT extends ResourceDemandBuilder<?>> implements BranchBuilder<PARENT> {

    private final PARENT belongsTo;
    private final BranchAction eModel;

    public BranchBuilderImpl(final PARENT belongsTo) {
        this.belongsTo = belongsTo;
        this.eModel = SeffFactory.eINSTANCE.createBranchAction();
    }

    @Override
    public ResourceDemandBuilder<BranchBuilder<PARENT>> createBranch(final String name, final String branchConditionExpression) {
        // create branch object for new branch
        final GuardedBranchTransition curBranch = SeffFactory.eINSTANCE.createGuardedBranchTransition();
        curBranch.setEntityName(name);
        curBranch.setBranchCondition_GuardedBranchTransition(RandomVariableFactory.expression(branchConditionExpression));
        // link model
        curBranch.setBranchAction_AbstractBranchTransition(this.eModel);
        this.eModel.getBranches_Branch().add(curBranch);

        // create nested resource demanding behavior
        final ResourceDemandBuilder<BranchBuilder<PARENT>> nested = ResourceDemandBuilderImpl.nestedResourceDemand(this);
        // link nested model
        curBranch.setBranchBehaviour_BranchTransition(nested.getReference());
        nested.getReference().setAbstractBranchTransition_ResourceDemandingBehaviour(curBranch);

        return nested;
    }

    @Override
    public PARENT end() {
        return this.belongsTo;
    }

    @Override
    public BranchAction getReference() {
        return this.eModel;
    }
}
