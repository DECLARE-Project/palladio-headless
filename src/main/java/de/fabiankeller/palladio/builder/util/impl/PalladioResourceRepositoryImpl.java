package de.fabiankeller.palladio.builder.util.impl;

import de.fabiankeller.palladio.builder.util.PalladioResourceRepository;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.resourcetype.ResourceType;

import java.util.HashMap;
import java.util.Map;

public class PalladioResourceRepositoryImpl implements PalladioResourceRepository {

    private final ResourceRepository repository;

    private static final String CPU = "CPU";
    private static final String LAN = "LAN";
    private static final String HDD = "HDD";
    private static final String DELAY = "DELAY";

    private final Map<String, Entity> resourceTypes = new HashMap<>();

    public PalladioResourceRepositoryImpl(final ResourceRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProcessingResourceType cpu() {
        return (ProcessingResourceType) getResourceTypeByName(CPU);
    }

    @Override
    public ProcessingResourceType hdd() {
        return (ProcessingResourceType) getResourceTypeByName(HDD);
    }

    @Override
    public ProcessingResourceType delay() {
        return (ProcessingResourceType) getResourceTypeByName(DELAY);
    }

    @Override
    public CommunicationLinkResourceType lan() {
        return (CommunicationLinkResourceType) getResourceTypeByName(LAN);
    }

    private Entity getResourceTypeByName(final String entityName) {
        if (!this.resourceTypes.containsKey(entityName)) {
            for (final ResourceType type : this.repository.getAvailableResourceTypes_ResourceRepository()) {
                if (type.getEntityName().equals(entityName)) {
                    this.resourceTypes.put(entityName, type);
                    break;
                }
            }
            throw new RuntimeException(String.format("Could not find resource type '%s'", entityName));
        }
        return this.resourceTypes.get(entityName);
    }
}
