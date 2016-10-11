package de.fakeller.palladio.builder.repository.failure;

import de.fakeller.palladio.builder.EntityHierarchicalBuilder;
import de.fakeller.palladio.builder.repository.RepositoryBuilder;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;

/**
 * Used to build {@link SoftwareInducedFailureType}s.
 */
public interface SoftwareInducedFailureBuilder extends EntityHierarchicalBuilder<SoftwareInducedFailureBuilder, SoftwareInducedFailureType, RepositoryBuilder> {
}
