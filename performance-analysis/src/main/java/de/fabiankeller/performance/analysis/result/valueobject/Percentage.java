package de.fabiankeller.performance.analysis.result.valueobject;

import java.util.Locale;

public class Percentage extends AbstractValueObject<Double> {

    protected Percentage(final double percentage) {
        super(percentage);
    }

    /**
     * Creates a percentage.
     *
     * @param percentage pass a number in the interval between [0.0;1.0] to represent 0% to 100%.
     */

    public static Percentage of(final double percentage) {
        return new Percentage(percentage);
    }

    public double getPercentage() {
        return this.value;
    }

    public String toHumanReadable() {
        return String.format(Locale.ROOT, "%.2f%%", 100.0 * getPercentage());
    }
}