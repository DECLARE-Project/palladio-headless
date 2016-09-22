package de.fabiankeller.palladio.analysis.result.valueobject;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DurationTest {
    @Test
    public void testEqualityOfSameType() throws Exception {
        assertEquals(Duration.ofMilliseconds(640), Duration.ofMilliseconds(640));
        assertNotEquals(Duration.ofMilliseconds(641), Duration.ofMilliseconds(640));

        assertEquals(Duration.ofSeconds(640), Duration.ofSeconds(640));
        assertNotEquals(Duration.ofSeconds(641), Duration.ofSeconds(640));

        assertEquals(Duration.ofMinutes(64), Duration.ofMinutes(64));
        assertNotEquals(Duration.ofMinutes(65), Duration.ofMinutes(64));

        assertEquals(Duration.ofHours(64), Duration.ofHours(64));
        assertNotEquals(Duration.ofHours(65), Duration.ofHours(64));

        assertEquals(Duration.ofDays(64), Duration.ofDays(64));
        assertNotEquals(Duration.ofDays(65), Duration.ofDays(64));

        assertEquals(Duration.ofWeeks(64), Duration.ofWeeks(64));
        assertNotEquals(Duration.ofWeeks(65), Duration.ofWeeks(64));

        assertEquals(Duration.ofYears(64), Duration.ofYears(64));
        assertNotEquals(Duration.ofYears(65), Duration.ofYears(64));
    }

    @Test
    public void testConversions() throws Exception {
        assertEquals(Duration.ofMilliseconds(1000), Duration.ofSeconds(1));
        assertNotEquals(Duration.ofMilliseconds(999), Duration.ofSeconds(1));

        assertEquals(Duration.ofSeconds(60), Duration.ofMinutes(1));
        assertNotEquals(Duration.ofSeconds(59), Duration.ofMinutes(1));

        assertEquals(Duration.ofMinutes(60), Duration.ofHours(1));
        assertNotEquals(Duration.ofMinutes(59), Duration.ofHours(1));

        assertEquals(Duration.ofHours(24), Duration.ofDays(1));
        assertNotEquals(Duration.ofHours(23), Duration.ofDays(1));

        assertEquals(Duration.ofDays(7), Duration.ofWeeks(1));
        assertNotEquals(Duration.ofDays(6), Duration.ofWeeks(1));
    }

}