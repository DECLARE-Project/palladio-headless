package de.fabiankeller.palladio.builder.repository.impl;

import de.fabiankeller.palladio.builder.repository.InterfaceBuilder;
import de.fabiankeller.palladio.builder.repository.ParameterType;
import de.fabiankeller.palladio.builder.repository.SignatureBuilder;
import org.palladiosimulator.pcm.repository.*;


public class SignatureBuilderImpl extends AbstractHierarchicalBuilder<SignatureBuilder, OperationSignature, InterfaceBuilder> implements SignatureBuilder {

    SignatureBuilderImpl(final InterfaceBuilder belongsTo) {
        super(belongsTo);
    }

    @Override
    protected OperationSignature newEModel() {
        return RepositoryFactory.eINSTANCE.createOperationSignature();
    }

    @Override
    protected void registerParent() {
        this.eModel.setInterface__OperationSignature(this.belongsTo.getReference());
    }


    @Override
    public SignatureBuilder withParameter(final String name, final ParameterType type) {
        // create model
        final Parameter param = RepositoryFactory.eINSTANCE.createParameter();
        param.setParameterName(name);
        param.setDataType__Parameter(this.transformDataType(type));

        // link model
        param.setOperationSignature__Parameter(this.eModel);
        this.eModel.getParameters__OperationSignature().add(param);

        return this;
    }

    @Override
    public SignatureBuilder withReturnType(final ParameterType type) {
        this.eModel.setReturnType__OperationSignature(transformDataType(type));
        return this;
    }


    private DataType transformDataType(final ParameterType type) {
        final PrimitiveDataType transformed = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
        switch (type) {
            case BOOL:
                transformed.setType(PrimitiveTypeEnum.BOOL);
                break;
            case BYTE:
                transformed.setType(PrimitiveTypeEnum.BYTE);
                break;
            case CHAR:
                transformed.setType(PrimitiveTypeEnum.CHAR);
                break;
            case DOUBLE:
                transformed.setType(PrimitiveTypeEnum.DOUBLE);
                break;
            case INT:
                transformed.setType(PrimitiveTypeEnum.INT);
                break;
            case STRING:
                transformed.setType(PrimitiveTypeEnum.STRING);
                break;
            default:
                throw new RuntimeException("Unknown parameter type: " + type);
        }
        return transformed;
    }
}
