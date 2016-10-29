package de.fakeller.palladio.builder.repository.seff.impl;

import de.fakeller.palladio.builder.repository.failure.SoftwareInducedFailureBuilder;
import de.fakeller.palladio.builder.repository.seff.InternalActionBuilder;
import de.fakeller.palladio.builder.repository.seff.ResourceDemandBuilder;
import de.fakeller.palladio.builder.util.random.RandomVariableFactory;
import de.fakeller.palladio.environment.util.PalladioResourceRepository;
import org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.ReliabilityFactory;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.seff.InternalAction;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand;
import org.palladiosimulator.pcm.seff.seff_performance.SeffPerformanceFactory;

public class InternalActionBuilderImpl<PARENT extends ResourceDemandBuilder<?>> implements InternalActionBuilder<PARENT> {

    private final PARENT belongsTo;
    private final InternalAction eModel;
    private final PalladioResourceRepository resources;

    public InternalActionBuilderImpl(final PARENT belongsTo) {
        this.belongsTo = belongsTo;
        this.resources = PalladioResourceRepository.INSTANCE.resources();
        this.eModel = SeffFactory.eINSTANCE.createInternalAction();
    }

    @Override
    public InternalActionBuilder<PARENT> withCpuDemand(final String demand) {
        return withDemand(this.resources.cpu(), demand);
    }

    @Override
    public InternalActionBuilder<PARENT> withDelayDemand(final String demand) {
        return withDemand(this.resources.delay(), demand);
    }

    @Override
    public InternalActionBuilder<PARENT> withHddDemand(final String demand) {
        return withDemand(this.resources.hdd(), demand);
    }

    private InternalActionBuilder<PARENT> withDemand(final ProcessingResourceType resourceType, final String demandSpecification) {
        // create model
        final ParametricResourceDemand prd = SeffPerformanceFactory.eINSTANCE.createParametricResourceDemand();
        prd.setRequiredResource_ParametricResourceDemand(resourceType);
        prd.setSpecification_ParametericResourceDemand(RandomVariableFactory.factory().expression(demandSpecification).get());

        // link model
        prd.setAction_ParametricResourceDemand(this.eModel);
        this.eModel.getResourceDemand_Action().add(prd);

        return this;
    }

    @Override
    public InternalActionBuilder<PARENT> withFailure(final double probability, final SoftwareInducedFailureBuilder failure) {
        // create failure description
        final InternalFailureOccurrenceDescription desc = ReliabilityFactory.eINSTANCE.createInternalFailureOccurrenceDescription();
        desc.setFailureProbability(probability);

        // link to failure type
        desc.setSoftwareInducedFailureType__InternalFailureOccurrenceDescription(failure.getReference());
        failure.getReference().getInternalFailureOccurrenceDescriptions__SoftwareInducedFailureType().add(desc);

        // link to parent
        desc.setInternalAction__InternalFailureOccurrenceDescription(this.eModel);
        this.eModel.getInternalFailureOccurrenceDescriptions__InternalAction().add(desc);

        return this;
    }


    @Override
    public PARENT end() {
        return this.belongsTo;
    }

    @Override
    public InternalAction getReference() {
        return this.eModel;
    }

}
