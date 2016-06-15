package de.fabiankeller.palladio.builder.resourceenvironment;

import de.fabiankeller.palladio.builder.HierarchicalEntityBuilder;
import de.fabiankeller.palladio.builder.NamedBuilder;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;

/**
 * Builds a {@link ProcessingResourceSpecification}.
 * <p>
 * Note: Unfortunately it is not possible to extend either {@link HierarchicalEntityBuilder} or {@link NamedBuilder}, as
 * the {@link ProcessingResourceSpecification} interface does not extend {@link NamedElement}.
 */
public interface ProcessingResourceBuilder {

    ProcessingResourceBuilder withProcessingRate(double processingRate);

    ProcessingResourceBuilder withMTTF(double mttf);

    ProcessingResourceBuilder withMTTR(double mttr);

    ProcessingResourceBuilder withNumberOfReplicas(int numberOfReplicas);

    ProcessingResourceBuilder withRequiredByContainer(boolean requiredByContainer);

    ProcessingResourceBuilder withSchedulingPolicy(/* todo: add policies */);

    ProcessingResourceSpecification getReference();

    /**
     * Finish specifying resource and return to container.
     */
    ContainerBuilder end();
}
