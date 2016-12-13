package de.fakeller.palladio.builder.util.impl;

import de.fakeller.palladio.builder.util.VariableUsageFactory;
import de.fakeller.palladio.builder.util.random.RandomVariableFactory;
import de.uka.ipd.sdq.stoex.StoexFactory;
import de.uka.ipd.sdq.stoex.VariableReference;
import org.palladiosimulator.pcm.parameter.ParameterFactory;
import org.palladiosimulator.pcm.parameter.VariableCharacterisation;
import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.parameter.VariableUsage;

public class VariableUsageFactoryImpl implements VariableUsageFactory {

    @Override
    public VariableUsage valueUsage(final String variableName, final String specification) {
        // create VariableUsage
        final VariableUsage varusg = ParameterFactory.eINSTANCE.createVariableUsage();
        final VariableCharacterisation characterisation = ParameterFactory.eINSTANCE.createVariableCharacterisation();

        // create characterisation
        characterisation.setType(VariableCharacterisationType.VALUE);
        characterisation.setVariableUsage_VariableCharacterisation(varusg);
        characterisation.setSpecification_VariableCharacterisation(RandomVariableFactory.factory().expression(specification).get());

        // create var reference
        final VariableReference varref = StoexFactory.eINSTANCE.createVariableReference();
        varref.setReferenceName(variableName);

        // add characterisation and reference to VariableUsage
        varusg.getVariableCharacterisation_VariableUsage().add(characterisation);
        varusg.setNamedReference__VariableUsage(varref);

        return varusg;
    }
}
