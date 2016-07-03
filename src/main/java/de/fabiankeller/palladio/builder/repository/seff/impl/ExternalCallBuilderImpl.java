package de.fabiankeller.palladio.builder.repository.seff.impl;

import de.fabiankeller.palladio.builder.repository.seff.ExternalCallBuilder;
import de.fabiankeller.palladio.builder.repository.seff.ResourceDemandBuilder;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

public class ExternalCallBuilderImpl<PARENT extends ResourceDemandBuilder<?>> implements ExternalCallBuilder<PARENT> {

    private final PARENT belongsTo;
    private final ExternalCallAction eModel;

    public ExternalCallBuilderImpl(final PARENT belongsTo) {
        this.belongsTo = belongsTo;
        this.eModel = SeffFactory.eINSTANCE.createExternalCallAction();
    }

    @Override
    public ExternalCallBuilderImpl<PARENT> withVariableUsage(final String name, final String specification) {
        // fixme: implement
        throw new RuntimeException("NIY");
    }

    @Override
    public PARENT end() {
        return this.belongsTo;
    }

    @Override
    public ExternalCallAction getReference() {
        return this.eModel;
    }

}
