package de.fabiankeller.palladio.analysis.result.valueobject;

import java.util.Locale;

public class Percentage implements ValueObject {

    private final double percentage;

    protected Percentage(final double percentage1) {
        this.percentage = percentage1;
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
        return this.percentage;
    }

    public String toHumanReadable() {
        return String.format(Locale.ROOT, "%.2f%%", 100.0 * getPercentage());
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Percentage that = (Percentage) o;

        return Double.compare(that.percentage, this.percentage) == 0;

    }

    @Override
    public int hashCode() {
        final long temp = Double.doubleToLongBits(this.percentage);
        return (int) (temp ^ (temp >>> 32));
    }
}