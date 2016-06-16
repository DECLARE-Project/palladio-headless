package de.fabiankeller.palladio.builder.util;

import org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;

/**
 * Convenient access to Palladio's {@link ResourceRepository}
 */
public interface PalladioResourceRepository {

    ProcessingResourceType cpu();

    ProcessingResourceType hdd();

    ProcessingResourceType delay();

    CommunicationLinkResourceType lan();
}
