package de.fabiankeller.palladio.builder;

/**
 * To be thrown if invalid models are trying to be built, builder methods are used incorrectly or assumptions do not hold.
 */
public class BuilderException extends RuntimeException {

    public BuilderException(final BaseBuilder<?> builder, final String message) {
        super(builderToExceptionMessage(builder, message));
    }

    public BuilderException(final BaseBuilder<?> builder, final String message, final Throwable cause) {
        super(builderToExceptionMessage(builder, message), cause);
    }

    public BuilderException(final BaseBuilder<?> builder, final Throwable cause) {
        super(builderToExceptionMessage(builder), cause);
    }

    public BuilderException(final BaseBuilder<?> builder, final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(builderToExceptionMessage(builder, message), cause, enableSuppression, writableStackTrace);
    }


    private static String builderToExceptionMessage(final BaseBuilder<?> builder, final String original) {
        String msg = String.format("There was an error building an instance of '%s' with name '%s' in builder '%s'",
                builder.getReference().getClass().getName(),
                builder.getReference().getEntityName(),
                builder.getClass());
        if (null != original) {
            msg += ": " + original;
        }
        return msg;

    }

    private static String builderToExceptionMessage(final BaseBuilder<?> builder) {
        return builderToExceptionMessage(builder, null);
    }
}
