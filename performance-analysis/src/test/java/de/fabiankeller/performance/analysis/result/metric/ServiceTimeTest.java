package de.fabiankeller.performance.analysis.result.metric;

import de.fabiankeller.performance.analysis.result.valueobject.Duration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Fabian on 25.09.2016.
 */
public class ServiceTimeTest {

    @Test
    public void getServiceTime() throws Exception {
        final Duration duration = Duration.ofSeconds(2.4);
        final ServiceTime st = new ServiceTime(duration);

        assertEquals(duration, st.getServiceTime());

        assertEquals(new ServiceTime(Duration.ofSeconds(2.4)), st);
        assertNotEquals(new ServiceTime(Duration.ofSeconds(2.5)), st);
    }
}