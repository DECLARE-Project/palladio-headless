package de.fakeller.palladio.builder.repository.seff.impl;

import de.fakeller.palladio.builder.BaseBuilder;
import de.fakeller.palladio.builder.BaseHierarchicalBuilder;
import de.fakeller.palladio.builder.BuilderException;
import de.fakeller.palladio.builder.repository.ComponentBuilder;
import de.fakeller.palladio.builder.repository.SignatureBuilder;
import de.fakeller.palladio.builder.repository.seff.ExternalCallBuilder;
import de.fakeller.palladio.builder.repository.seff.ResourceDemandBuilder;
import de.fakeller.palladio.builder.util.impl.VariableUsageFactoryImpl;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RequiredRole;
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
    public ExternalCallBuilder<PARENT> withCalledService(final SignatureBuilder signature) {
        // set signature
        this.eModel.setCalledService_ExternalService(signature.getReference());

        // find component this call builder is located in
        BaseHierarchicalBuilder builder = this;
        while (builder != null && !(builder instanceof ComponentBuilder)) {
            final BaseBuilder parent = builder.end();
            if (parent instanceof BaseHierarchicalBuilder) {
                builder = (BaseHierarchicalBuilder) parent;
            } else {
                builder = null;
            }
        }
        if (null == builder) {
            throw new BuilderException(this, "Could not locate parent component this ExternalCallAction belongs to. Something must have gone utterly wrong. Please file a bug.");
        }
        final ComponentBuilder cmp = (ComponentBuilder) builder;

        // locate OperationRequiredRole in the component, that offers the given signature.
        final OperationInterface sigIface = signature.getReference().getInterface__OperationSignature();
        for (final RequiredRole required : cmp.getReference().getRequiredRoles_InterfaceRequiringEntity()) {
            if (!(required instanceof OperationRequiredRole)) {
                continue;
            }

            final OperationRequiredRole orr = (OperationRequiredRole) required;
            if (sigIface.equals(orr.getRequiredInterface__OperationRequiredRole())) {
                this.eModel.setRole_ExternalService(orr);
                break;
            }
        }
        if (null == this.eModel.getRole_ExternalService()) {
            throw new BuilderException(this, String.format("Could not find an interface providing the signature %s in component %s.", signature.getEntityName(), cmp.getEntityName()));
        }

        return this;
    }

    @Override
    public ExternalCallBuilder<PARENT> withRetryCount(final int retryCount) {
        this.eModel.setRetryCount(retryCount);
        return this;
    }

    @Override
    public ExternalCallBuilderImpl<PARENT> withInputVariableUsage(final String name, final String specification) {
        // create usage model
        final VariableUsage varusg = new VariableUsageFactoryImpl().valueUsage(name, specification);

        // link model
        varusg.setCallAction__VariableUsage(this.eModel);
        this.eModel.getInputVariableUsages__CallAction().add(varusg);

        return this;
    }

    @Override
    public ExternalCallBuilder<PARENT> withReturnVariableUsage(final String name, final String specification) {
        // fixme: implement method, but there is no example in SimpleTacticsExample to see how its supposed to be done
        throw new RuntimeException("NYI");
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
