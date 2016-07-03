package de.fabiankeller.palladio.builder.repository.seff.impl;

import de.fabiankeller.palladio.builder.repository.seff.InternalActionBuilder;
import de.fabiankeller.palladio.builder.repository.seff.ResourceDemandBuilder;
import org.palladiosimulator.pcm.seff.InternalAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

public class InternalActionBuilderImpl<PARENT extends ResourceDemandBuilder<?>> implements InternalActionBuilder<PARENT> {

    private final PARENT belongsTo;
    private final InternalAction eModel;

    public InternalActionBuilderImpl(final PARENT belongsTo) {
        this.belongsTo = belongsTo;
        this.eModel = SeffFactory.eINSTANCE.createInternalAction();
    }

    @Override
    public InternalActionBuilder<PARENT> withCpuDemand(final String demand) {
        // fixme: implement
        throw new RuntimeException("NIY");
    }

    @Override
    public InternalActionBuilder<PARENT> withDelayDemand(final String demand) {
        // fixme: implement
        throw new RuntimeException("NIY");
    }

    @Override
    public InternalActionBuilder<PARENT> withHddDemand(final String demand) {
        // fixme: implement
        throw new RuntimeException("NIY");
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
