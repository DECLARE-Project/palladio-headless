package de.fabiankeller.palladio.builder.repository.seff.impl;

import de.fabiankeller.palladio.builder.repository.seff.BranchBuilder;
import de.fabiankeller.palladio.builder.repository.seff.ResourceDemandBuilder;
import org.palladiosimulator.pcm.seff.BranchAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

public class BranchBuilderImpl<PARENT extends ResourceDemandBuilder<?>> implements BranchBuilder<PARENT> {

    private final PARENT belongsTo;
    private final BranchAction eModel;

    public BranchBuilderImpl(final PARENT belongsTo) {
        this.belongsTo = belongsTo;
        this.eModel = SeffFactory.eINSTANCE.createBranchAction();
    }

    @Override
    public ResourceDemandBuilder<ResourceDemandBuilder<PARENT>> createBranch(final String name, final String branchConditionExpression) {
        // fixme: implement
        throw new RuntimeException("NIY");
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
