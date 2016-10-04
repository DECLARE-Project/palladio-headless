package de.fabiankeller.performance.analysis.result.valueobject;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Fabian on 23.09.2016.
 */
public class PercentageTest {
    @Test
    public void getPercentage() throws Exception {
        assertEquals(0.21, Percentage.of(0.21).getPercentage(), Double.MIN_VALUE);
    }


    @Test
    public void equals() throws Exception {
        assertEquals(Percentage.of(0.21), Percentage.of(0.21));
    }

    @Test
    public void toHumanReadable() throws Exception {
        assertEquals("21.00%", Percentage.of(0.21).toHumanReadable());
    }


}