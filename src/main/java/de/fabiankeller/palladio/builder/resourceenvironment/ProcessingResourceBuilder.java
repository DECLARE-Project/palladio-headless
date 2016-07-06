package de.fabiankeller.palladio.builder.resourceenvironment;

import de.fabiankeller.palladio.builder.BaseHierarchicalBuilder;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;

/**
 * Builds a {@link ProcessingResourceSpecification}.
 */
public interface ProcessingResourceBuilder extends BaseHierarchicalBuilder<ProcessingResourceSpecification, ContainerBuilder> {

    enum SchedulingPolicy {
        DELAY,
        FCFS,
        PROCESSOR_SHARING
    }

    ProcessingResourceBuilder withProcessingRate(double processingRate);

    ProcessingResourceBuilder withMTTF(double mttf);

    ProcessingResourceBuilder withMTTR(double mttr);

    ProcessingResourceBuilder withNumberOfReplicas(int numberOfReplicas);

    ProcessingResourceBuilder withRequiredByContainer(boolean requiredByContainer);

    ProcessingResourceBuilder withSchedulingPolicy(SchedulingPolicy policy);
}
