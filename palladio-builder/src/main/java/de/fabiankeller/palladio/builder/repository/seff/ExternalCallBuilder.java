package de.fabiankeller.palladio.builder.repository.seff;

import de.fabiankeller.palladio.builder.EntityHierarchicalBuilder;
import de.fabiankeller.palladio.builder.repository.SignatureBuilder;
import org.palladiosimulator.pcm.seff.ExternalCallAction;

/**
 * Used to build {@link ExternalCallAction}s. They refer to resource demands by calling external services.
 */
public interface ExternalCallBuilder<P extends ResourceDemandBuilder<?>> extends EntityHierarchicalBuilder<ExternalCallBuilder<P>, ExternalCallAction, P> {

    ExternalCallBuilder<P> withCalledService(SignatureBuilder signature);


    ExternalCallBuilder<P> withRetryCount(int retryCount);

    // todo: improve string to strongly typed arguments?

    ExternalCallBuilder<P> withInputVariableUsage(String name, String specification);

    ExternalCallBuilder<P> withReturnVariableUsage(String name, String specification);
}
