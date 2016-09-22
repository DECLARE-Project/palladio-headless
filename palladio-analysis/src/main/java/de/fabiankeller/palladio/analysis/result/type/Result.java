package de.fabiankeller.palladio.analysis.result.type;

/**
 * Represents a single performance analysis result, that is specific for a element in the PCM.
 *
 * @param <T> constrain the types that a result can be attached to
 */
public interface Result<T> {

    /**
     * Returns the element this result is attached to.
     */
    T attachedTo();
}
