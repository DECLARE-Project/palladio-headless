package de.fakeller.palladio.builder.repository.seff.impl;

import de.fakeller.palladio.builder.BaseBuilder;
import de.fakeller.palladio.builder.BuilderException;
import de.fakeller.palladio.builder.repository.ComponentBuilder;
import de.fakeller.palladio.builder.repository.SignatureBuilder;
import de.fakeller.palladio.builder.repository.seff.BranchBuilder;
import de.fakeller.palladio.builder.repository.seff.ExternalCallBuilder;
import de.fakeller.palladio.builder.repository.seff.InternalActionBuilder;
import de.fakeller.palladio.builder.repository.seff.ResourceDemandBuilder;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;

public class ResourceDemandBuilderImpl<PARENT extends BaseBuilder<?>> implements ResourceDemandBuilder<PARENT> {

    private final PARENT belongsTo;

    private final ResourceDemandingBehaviour eModel;

    private AbstractAction predecessor;

    private ResourceDemandBuilderImpl(final ResourceDemandingBehaviour eModel, final PARENT belongsTo) {
        this.eModel = eModel;
        this.belongsTo = belongsTo;
    }


    // // CONSTRUCTOR METHODS // //

    /**
     * Creates a {@link ResourceDemandBuilder} to be used by {@link ComponentBuilder}s, as this will create a root-level
     * resource demand.
     */
    public static ResourceDemandBuilder<ComponentBuilder> rootResourceDemand(final ComponentBuilder belongsTo, final SignatureBuilder affectedOperation) {
        // create demand
        final ResourceDemandingSEFF eModel = SeffFactory.eINSTANCE.createResourceDemandingSEFF();
        // link SEFF demand to the belonging component
        eModel.setBasicComponent_ServiceEffectSpecification(belongsTo.getReference());
        belongsTo.getReference().getServiceEffectSpecifications__BasicComponent().add(eModel);
        // link SEFF demand to the actual operation - one-directional only
        eModel.setDescribedService__SEFF(affectedOperation.getReference());

        return new ResourceDemandBuilderImpl<ComponentBuilder>(eModel, belongsTo);
    }

    /**
     * Creates a {@link ResourceDemandBuilder} to be used in nested control flow resource demands, as can be reached by
     * using {@link BranchBuilder}s.
     */
    public static <P extends BaseBuilder<?>> ResourceDemandBuilder<P> nestedResourceDemand(final P belongsTo) {
        final ResourceDemandingBehaviour eModel = SeffFactory.eINSTANCE.createResourceDemandingBehaviour();
        return new ResourceDemandBuilderImpl<P>(eModel, belongsTo);
    }


    // // QUEUING LOGIC // //

    private ResourceDemandBuilder<PARENT> enqueueAction(final AbstractAction action) {
        // double-link
        if (null != this.predecessor) {
            action.setPredecessor_AbstractAction(this.predecessor);
            this.predecessor.setSuccessor_AbstractAction(action);
        }

        // link to parent model
        this.eModel.getSteps_Behaviour().add(action);
        action.setResourceDemandingBehaviour_AbstractAction(this.eModel);

        this.predecessor = action;
        return this;
    }

    private void assertPredecessor() {
        if (null == this.predecessor) {
            throw new BuilderException(this, "The action you are trying to create requires a previous action to be registered. If you have not set an action yet, begin with the start() action.");
        }
    }


    // // NON-BUILDER ACTIONS // //

    @Override
    public ResourceDemandBuilder<PARENT> start() {
        final int noExistingActions = this.getReference().getSteps_Behaviour().size();
        if (noExistingActions > 0) {
            throw new BuilderException(this, String.format("There are already %d actions defined for this resource demand specification. Cannot add the START action again!", noExistingActions));
        }
        return enqueueAction(SeffFactory.eINSTANCE.createStartAction());
    }

    @Override
    public ResourceDemandBuilder<PARENT> stop() {
        assertPredecessor();
        return enqueueAction(SeffFactory.eINSTANCE.createStopAction());
    }


    // // BUILDER ACTIONS // //

    @Override
    public InternalActionBuilder<ResourceDemandBuilder<PARENT>> internalAction() {
        assertPredecessor();
        final InternalActionBuilder<ResourceDemandBuilder<PARENT>> builder = new InternalActionBuilderImpl<>(this);
        enqueueAction(builder.getReference());
        return builder;
    }

    @Override
    public ExternalCallBuilder<ResourceDemandBuilder<PARENT>> externalCall() {
        assertPredecessor();
        final ExternalCallBuilder<ResourceDemandBuilder<PARENT>> builder = new ExternalCallBuilderImpl<>(this);
        enqueueAction(builder.getReference());
        return builder;
    }

    @Override
    public BranchBuilder<ResourceDemandBuilder<PARENT>> branch() {
        assertPredecessor();
        final BranchBuilderImpl<ResourceDemandBuilder<PARENT>> builder = new BranchBuilderImpl<>(this);
        enqueueAction(builder.getReference());
        return builder;
    }


    // // BUILDER API METHODS // //

    @Override
    public PARENT end() {
        return this.belongsTo;
    }

    @Override
    public ResourceDemandingBehaviour getReference() {
        return this.eModel;
    }
}
