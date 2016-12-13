package de.fakeller.palladio.builder.repository.seff.impl;

import de.fakeller.palladio.builder.repository.seff.LoopBuilder;
import de.fakeller.palladio.builder.repository.seff.ResourceDemandBuilder;
import de.fakeller.palladio.builder.util.random.RandomVariable;
import org.palladiosimulator.pcm.seff.LoopAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

/**
 * @author Fabian Keller
 */
public class LoopBuilderImpl<PARENT extends ResourceDemandBuilder<?>> implements LoopBuilder<PARENT> {

    private final PARENT belongsTo;
    private final LoopAction eModel;

    public LoopBuilderImpl(final PARENT belongsTo) {
        this.belongsTo = belongsTo;
        this.eModel = SeffFactory.eINSTANCE.createLoopAction();
    }

    @Override
    public LoopBuilderImpl<PARENT> withIterationCount(final RandomVariable iterationCount) {
        this.eModel.setIterationCount_LoopAction(iterationCount.get());
        return this;
    }

    @Override
    public ResourceDemandBuilder<LoopBuilder<PARENT>> withLoopBody() {
        // create nested resource demanding behavior
        final ResourceDemandBuilder<LoopBuilder<PARENT>> nested = ResourceDemandBuilderImpl.nestedResourceDemand(this);
        // link nested model
        this.eModel.setBodyBehaviour_Loop(nested.getReference());
        nested.getReference().setAbstractLoopAction_ResourceDemandingBehaviour(this.eModel);

        return nested;
    }

    @Override
    public PARENT end() {
        return this.belongsTo;
    }

    @Override
    public LoopAction getReference() {
        return this.eModel;
    }
}
