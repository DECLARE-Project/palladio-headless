package de.fabiankeller.palladio.builder.repository.seff.impl;

import de.fabiankeller.palladio.builder.BaseBuilder;
import de.fabiankeller.palladio.builder.repository.SignatureBuilder;
import de.fabiankeller.palladio.builder.repository.seff.ResourceDemandBuilder;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;

public class ResourceDemandBuilderImpl<PARENT extends BaseBuilder<?>> implements ResourceDemandBuilder<PARENT> {

    private final PARENT belongsTo;

    private final ResourceDemandingSEFF eModel;

    public ResourceDemandBuilderImpl(final PARENT belongsTo, final SignatureBuilder affectedOperation) {
        this.belongsTo = belongsTo;

        // link SEFF to the belonging component
        this.eModel = SeffFactory.eINSTANCE.createResourceDemandingSEFF();
        belongsTo.getReference().getServiceEffectSpecifications__BasicComponent().add(this.eModel);

        // link SEFF to the actual operation - one-directional only
        this.eModel.setDescribedService__SEFF(affectedOperation.getReference());
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
    public ResourceDemandingSEFF getReference() {
        return this.eModel;
    }
}
