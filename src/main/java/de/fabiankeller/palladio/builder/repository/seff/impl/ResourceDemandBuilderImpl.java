package de.fabiankeller.palladio.builder.repository.seff.impl;

import de.fabiankeller.palladio.builder.BaseBuilder;
import de.fabiankeller.palladio.builder.repository.ComponentBuilder;
import de.fabiankeller.palladio.builder.repository.SignatureBuilder;
import de.fabiankeller.palladio.builder.repository.seff.BranchBuilder;
import de.fabiankeller.palladio.builder.repository.seff.ExternalCallBuilder;
import de.fabiankeller.palladio.builder.repository.seff.InternalActionBuilder;
import de.fabiankeller.palladio.builder.repository.seff.ResourceDemandBuilder;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;

public class ResourceDemandBuilderImpl<PARENT extends BaseBuilder<?>> implements ResourceDemandBuilder<PARENT> {

    private final PARENT belongsTo;

    private final ResourceDemandingBehaviour eModel;

    private ResourceDemandBuilderImpl(final ResourceDemandingBehaviour eModel, final PARENT belongsTo) {
        this.eModel = eModel;
        this.belongsTo = belongsTo;
    }

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

    public static <P extends ResourceDemandBuilder<?>> ResourceDemandBuilder<P> nestedResourceDemand(final P belongsTo) {
        final ResourceDemandingBehaviour eModel = SeffFactory.eINSTANCE.createResourceDemandingBehaviour();
        return new ResourceDemandBuilderImpl<P>(eModel, belongsTo);
    }

    @Override
    public BranchBuilder<ResourceDemandBuilder<PARENT>> branch() {
        return null;
    }

    @Override
    public ResourceDemandBuilder<PARENT> start() {
        return null;
    }

    @Override
    public InternalActionBuilder<ResourceDemandBuilder<PARENT>> internalAction() {
        return null;
    }

    @Override
    public ExternalCallBuilder<ResourceDemandBuilder<PARENT>> externalCall() {
        return null;
    }

    @Override
    public ResourceDemandBuilder<PARENT> stop() {
        return null;
    }

    @Override
    public PARENT end() {
        return this.belongsTo;
    }

    @Override
    public ResourceDemandingBehaviour getReference() {
        return this.eModel;
    }
}
