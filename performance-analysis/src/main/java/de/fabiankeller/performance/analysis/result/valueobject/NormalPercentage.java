package de.fabiankeller.performance.analysis.result.valueobject;

/**
 * A normal percentage is constrained to the interval [0.0;1.0].
 */
public class NormalPercentage extends Percentage {


    protected NormalPercentage(final double percentage1) {
        super(percentage1);
    }


    public static NormalPercentage of(final double percentage) {
        if (percentage < 0.0 || percentage > 1.0) {
            throw new IllegalArgumentException(String.format("Given percentage '%s' is not in regular interval [0.0;1.0].", percentage));
        }
        return new NormalPercentage(percentage);
    }
}