package de.fabiankeller.palladio.builder.system.impl;

import de.fabiankeller.palladio.builder.BuilderException;
import de.fabiankeller.palladio.builder.repository.ComponentBuilder;
import de.fabiankeller.palladio.builder.system.AssemblyBuilder;
import de.fabiankeller.palladio.builder.system.SystemBuilder;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.system.SystemFactory;

public class SystemBuilderImpl implements SystemBuilder {

    private final System eSystem;

    public SystemBuilderImpl() {
        this.eSystem = SystemFactory.eINSTANCE.createSystem();
    }

    @Override
    public AssemblyBuilder assemble(final ComponentBuilder component) {
        // create model
        final AssemblyBuilderImpl builder = new AssemblyBuilderImpl(this, component);

        // link model
        this.eSystem.getAssemblyContexts__ComposedStructure().add(builder.getReference());

        return builder;
    }

    @Override
    public System getReference() {
        return this.eSystem;
    }

    @Override
    public System build() throws BuilderException {
        return this.getReference();
    }


}
