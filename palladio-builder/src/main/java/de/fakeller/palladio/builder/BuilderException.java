package de.fakeller.palladio.builder;

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
        String nameInfo = "";
        if (builder instanceof NamedBuilder) {
            final NamedBuilder namedBuilder = (NamedBuilder) builder;
            nameInfo = String.format(" with name '%s'", namedBuilder.getEntityName());
        }
        String msg = String.format("There was an error building an instance of '%s'%s in builder '%s'",
                builder.getReference().getClass().getName(),
                nameInfo,
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
