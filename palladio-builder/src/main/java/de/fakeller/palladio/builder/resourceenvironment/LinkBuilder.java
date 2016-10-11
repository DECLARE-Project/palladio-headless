package de.fakeller.palladio.builder.resourceenvironment;

import de.fakeller.palladio.builder.EntityHierarchicalBuilder;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;

/**
 * Used to build {@link LinkingResource}s.
 */
public interface LinkBuilder extends EntityHierarchicalBuilder<LinkBuilder, LinkingResource, ResourceEnvironmentBuilder> {

    LinkBuilder between(ContainerBuilder... containers);

    LinkBuilder withLatency(double latency);

    LinkBuilder withThroughput(double throughput);

    LinkBuilder withFailureProbability(double probability);
}
