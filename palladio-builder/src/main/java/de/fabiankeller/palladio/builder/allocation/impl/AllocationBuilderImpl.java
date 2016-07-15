package de.fabiankeller.palladio.builder.allocation.impl;

import de.fabiankeller.palladio.builder.allocation.AllocationBuilder;
import de.fabiankeller.palladio.builder.resourceenvironment.ContainerBuilder;
import de.fabiankeller.palladio.builder.resourceenvironment.ResourceEnvironmentBuilder;
import de.fabiankeller.palladio.builder.system.AssemblyBuilder;
import de.fabiankeller.palladio.builder.system.SystemBuilder;
import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.allocation.AllocationContext;
import org.palladiosimulator.pcm.allocation.AllocationFactory;

public class AllocationBuilderImpl implements AllocationBuilder {

    private final Allocation eModel = AllocationFactory.eINSTANCE.createAllocation();

    public AllocationBuilderImpl(final SystemBuilder system, final ResourceEnvironmentBuilder targetEnvironment) {
        this.eModel.setSystem_Allocation(system.getReference());
        this.eModel.setTargetResourceEnvironment_Allocation(targetEnvironment.getReference());
    }

    @Override
    public AllocationBuilder allocate(final AssemblyBuilder assembly, final ContainerBuilder container) {
        // create allocation context
        final AllocationContext ctx = AllocationFactory.eINSTANCE.createAllocationContext();
        ctx.setAssemblyContext_AllocationContext(assembly.getReference());
        ctx.setResourceContainer_AllocationContext(container.getReference());

        // link models
        ctx.setAllocation_AllocationContext(this.eModel);
        this.eModel.getAllocationContexts_Allocation().add(ctx);

        return this;
    }

    @Override
    public Allocation getReference() {
        return this.eModel;
    }
}
