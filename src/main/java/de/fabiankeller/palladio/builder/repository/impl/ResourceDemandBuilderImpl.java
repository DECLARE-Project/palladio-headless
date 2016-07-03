package de.fabiankeller.palladio.builder.repository.impl;

import de.fabiankeller.palladio.builder.repository.ComponentBuilder;
import de.fabiankeller.palladio.builder.repository.ResourceDemandBuilder;
import de.fabiankeller.palladio.builder.repository.SignatureBuilder;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;

public class ResourceDemandBuilderImpl implements ResourceDemandBuilder {

    private final ComponentBuilder belongsTo;

    private final ResourceDemandingSEFF eModel;

    public ResourceDemandBuilderImpl(final ComponentBuilder belongsTo, final SignatureBuilder affectedOperation) {
        this.belongsTo = belongsTo;

        // link SEFF to the belonging component
        this.eModel = SeffFactory.eINSTANCE.createResourceDemandingSEFF();
        belongsTo.getReference().getServiceEffectSpecifications__BasicComponent().add(this.eModel);

        // link SEFF to the actual operation - one-directional only
        this.eModel.setDescribedService__SEFF(affectedOperation.getReference());
    }

    @Override
    public ComponentBuilder end() {
        return this.belongsTo;
    }

    @Override
    public ResourceDemandingSEFF getReference() {
        return this.eModel;
    }
}
