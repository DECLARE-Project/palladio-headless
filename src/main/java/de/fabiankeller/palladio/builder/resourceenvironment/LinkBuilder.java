package de.fabiankeller.palladio.builder.resourceenvironment;

import de.fabiankeller.palladio.builder.HierarchicalEntityBuilder;
import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;

/**
 * Used to build {@link LinkingResource}s.
 */
public interface LinkBuilder extends HierarchicalEntityBuilder<LinkBuilder, LinkingResource, ResourceEnvironmentBuilder> {

    LinkBuilder between(ContainerBuilder... containers);

    LinkBuilder withLatency(double latency);

    LinkBuilder withThroughput(double throughput);

    LinkBuilder withFailureProbability(double probability);
}
