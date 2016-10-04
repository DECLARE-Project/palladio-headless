package de.fabiankeller.performance.analysis.result.metric;

import de.fabiankeller.performance.analysis.result.valueobject.Percentage;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Fabian on 25.09.2016.
 */
public class UtilizationTest {
    @Test
    public void getUtilization() throws Exception {
        final Percentage utilization = Percentage.of(0.4);
        final Utilization util = new Utilization(utilization);

        Assert.assertEquals(utilization, util.getUtilization());

        assertEquals(new Utilization(Percentage.of(0.4)), util);
        assertNotEquals(new Utilization(Percentage.of(0.41)), util);
    }

}