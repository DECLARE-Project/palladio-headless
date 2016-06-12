package de.fabiankeller.palladio.builder.system.impl;

import de.fabiankeller.palladio.builder.repository.ComponentBuilder;
import de.fabiankeller.palladio.builder.repository.InterfaceBuilder;
import de.fabiankeller.palladio.builder.repository.impl.AbstractHierarchicalBuilder;
import de.fabiankeller.palladio.builder.system.AssemblyBuilder;
import de.fabiankeller.palladio.builder.system.SystemBuilder;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.CompositionFactory;

public class AssemblyBuilderImpl extends AbstractHierarchicalBuilder<AssemblyBuilder, AssemblyContext, SystemBuilder> implements AssemblyBuilder {

    /**
     * component encapsulated in assembly context
     */
    private final ComponentBuilder component;

    public AssemblyBuilderImpl(final SystemBuilder belongsTo, final ComponentBuilder component) {
        super(belongsTo);
        this.component = component;
        this.eModel.setEncapsulatedComponent__AssemblyContext(component.getReference());
    }

    @Override
    protected AssemblyContext newEModel() {
        return CompositionFactory.eINSTANCE.createAssemblyContext();
    }

    @Override
    protected void registerParent() {
        // AssemblyContext does not know the System it is contained in
    }

    @Override
    public AssemblyBuilder connectRequiredRole(final InterfaceBuilder requiredRole, final AssemblyBuilder providedBy, final InterfaceBuilder to) {
        return null;
    }

    @Override
    public AssemblyBuilder connectProvidedRole(final InterfaceBuilder providedRole, final AssemblyBuilder requiredBy, final InterfaceBuilder to) {
        return null;
    }

    @Override
    public AssemblyBuilder provideToSystem(final InterfaceBuilder provided) {
        return null;
    }

    @Override
    public AssemblyBuilder requiredBySystem(final InterfaceBuilder required) {
        return null;
    }
}
