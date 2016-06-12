package de.fabiankeller.palladio.builder;

import de.fabiankeller.palladio.builder.EntityBuilder;

/**
 * To be thrown if invalid models are trying to be built.
 */
public class EntityViolationException extends Exception {

    public EntityViolationException(EntityBuilder<?, ?> builder, String message) {
        super(builderToExceptionMessage(builder, message));
    }

    public EntityViolationException(EntityBuilder<?, ?> builder, String message, Throwable cause) {
        super(builderToExceptionMessage(builder, message), cause);
    }

    public EntityViolationException(EntityBuilder<?, ?> builder, Throwable cause) {
        super(builderToExceptionMessage(builder), cause);
    }

    public EntityViolationException(EntityBuilder<?, ?> builder, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(builderToExceptionMessage(builder, message), cause, enableSuppression, writableStackTrace);
    }


    private static String builderToExceptionMessage(EntityBuilder<?, ?> builder, String original) {
        String msg = String.format("There was an error building an instance of '%s' with name '%s' in builder '%s'",
                builder.getReference().getClass().getName(),
                builder.getReference().getEntityName(),
                builder.getClass());
        if (null != original) {
            msg += ": " + original;
        }
        return msg;

    }

    private static String builderToExceptionMessage(EntityBuilder<?, ?> builder) {
        return builderToExceptionMessage(builder, null);
    }
}
