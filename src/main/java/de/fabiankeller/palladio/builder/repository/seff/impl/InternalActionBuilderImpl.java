package de.fabiankeller.palladio.builder.repository.seff.impl;

import de.fabiankeller.palladio.builder.repository.seff.InternalActionBuilder;
import de.fabiankeller.palladio.builder.repository.seff.ResourceDemandBuilder;
import de.fabiankeller.palladio.builder.util.PalladioResourceRepository;
import de.fabiankeller.palladio.builder.util.RandomVariableFactory;
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
        prd.setSpecification_ParametericResourceDemand(RandomVariableFactory.expression(demandSpecification));

        // link model
        prd.setAction_ParametricResourceDemand(this.eModel);
        this.eModel.getResourceDemand_Action().add(prd);

        return this;
    }

    @Override
    public InternalActionBuilder<PARENT> withFailure(final double probability, final String failure) {
        // fixme: implement
        throw new RuntimeException("NIY");
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
