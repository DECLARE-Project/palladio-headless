package de.fabiankeller.palladio.builder.repository.seff;

import de.fabiankeller.palladio.builder.EntityHierarchicalBuilder;
import org.palladiosimulator.pcm.seff.ExternalCallAction;

/**
 * Used to build {@link ExternalCallAction}s. They refer to resource demands by calling external services.
 */
public interface ExternalCallBuilder<P extends ResourceDemandBuilder<?>> extends EntityHierarchicalBuilder<ExternalCallBuilder<P>, ExternalCallAction, P> {

    // todo: improve string to strongly typed arguments?

    ExternalCallBuilder<P> withVariableUsage(String name, String specification);
}
