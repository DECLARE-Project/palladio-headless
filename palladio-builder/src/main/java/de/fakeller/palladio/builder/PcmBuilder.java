package de.fakeller.palladio.builder;

import de.fakeller.palladio.builder.allocation.AllocationBuilder;
import de.fakeller.palladio.builder.allocation.impl.AllocationBuilderImpl;
import de.fakeller.palladio.builder.repository.RepositoryBuilder;
import de.fakeller.palladio.builder.repository.impl.RepositoryBuilderImpl;
import de.fakeller.palladio.builder.resourceenvironment.ResourceEnvironmentBuilder;
import de.fakeller.palladio.builder.resourceenvironment.impl.ResourceEnvironmentBuilderImpl;
import de.fakeller.palladio.builder.system.SystemBuilder;
import de.fakeller.palladio.builder.system.impl.SystemBuilderImpl;
import de.fakeller.palladio.builder.usage.UsageBuilder;
import de.fakeller.palladio.builder.usage.impl.UsageBuilderImpl;
import de.fakeller.palladio.environment.PCMResourceSetPartitionFactory;
import de.fakeller.palladio.environment.util.PalladioResourceRepository;
import de.fakeller.palladio.environment.util.PalladioResourceRepositoryImpl;
import org.eclipse.emf.common.util.URI;
import org.palladiosimulator.analyzer.workflow.blackboard.PCMResourceSetPartition;
import org.palladiosimulator.solver.models.PCMInstance;

/**
 * Entry point to building {@link PCMInstance}s.
 */
public class PcmBuilder {

    private final PCMResourceSetPartition rsp;

    private final RepositoryBuilder repository;
    private final SystemBuilder system;
    private final ResourceEnvironmentBuilder env;
    private final AllocationBuilder allocation;
    private final UsageBuilder usage;

    public PcmBuilder() {
        // setup palladio requirements
        this.rsp = new PCMResourceSetPartitionFactory.DefaultFactory().create();
        final PalladioResourceRepository resources = new PalladioResourceRepositoryImpl(this.rsp.getResourceTypeRepository());

        // setup builders
        this.repository = new RepositoryBuilderImpl();
        this.system = new SystemBuilderImpl();
        this.env = new ResourceEnvironmentBuilderImpl(resources);
        this.allocation = new AllocationBuilderImpl(this.system, this.env);
        this.usage = new UsageBuilderImpl(this.system);
    }

    public RepositoryBuilder repository() {
        return this.repository;
    }

    public SystemBuilder system() {
        return this.system;
    }

    public ResourceEnvironmentBuilder resourceEnvironment() {
        return this.env;
    }

    public AllocationBuilder allocation() {
        return this.allocation;
    }

    public UsageBuilder usage() {
        return this.usage;
    }

    public PCMInstance build() {
        this.rsp.setContents(URI.createFileURI("default.repository"), this.repository.build());
        this.rsp.setContents(URI.createFileURI("default.system"), this.system.build());
        this.rsp.setContents(URI.createFileURI("default.resourceenvironment"), this.env.build());
        this.rsp.setContents(URI.createFileURI("default.allocation"), this.allocation.build());
        this.rsp.setContents(URI.createFileURI("default.usagemodel"), this.usage.build());
        this.rsp.resolveAllProxies();
        return new PCMInstance(this.rsp);
    }


}
