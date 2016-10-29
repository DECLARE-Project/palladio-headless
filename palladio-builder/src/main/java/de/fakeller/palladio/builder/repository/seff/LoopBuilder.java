package de.fakeller.palladio.builder.repository.seff;

import de.fakeller.palladio.builder.EntityHierarchicalBuilder;
import de.fakeller.palladio.builder.repository.seff.impl.LoopBuilderImpl;
import de.fakeller.palladio.builder.util.random.RandomVariable;
import org.palladiosimulator.pcm.seff.LoopAction;

/**
 * Used to build {@link LoopAction}s. A loop action allows to loop the nested resource demand builder for the specified
 * number of times.
 */
public interface LoopBuilder<P extends ResourceDemandBuilder<?>> extends EntityHierarchicalBuilder<LoopBuilder<P>, LoopAction, P> {

    LoopBuilderImpl<P> withIterationCount(final RandomVariable iterationCount);

    ResourceDemandBuilder<LoopBuilder<P>> withLoopBody();
}
