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

    ProcessingResourceBuilder withMTTF(double mttf);

    ProcessingResourceBuilder withMTTR(double mttr);

    ProcessingResourceBuilder withNumberOfReplicas(long numberOfReplicas);

    ProcessingResourceBuilder withRequiredByContainer(boolean requiredByContainer);

    ProcessingResourceBuilder withSchedulingPolicy(/* todo: add policies */);

    /**
     * Finish specifying resource and return to parent.
     */
    ContainerBuilder end();
}
