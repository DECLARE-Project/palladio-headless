package de.fabiankeller.performance.analysis.result.exception;

/**
 * To be thrown in case the analysis result is invalid.
 */
public class InvalidResultException extends RuntimeException {

    public InvalidResultException() {
    }

    public InvalidResultException(final String message) {
        super(message);
    }

    public InvalidResultException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidResultException(final Throwable cause) {
        super(cause);
    }
}
