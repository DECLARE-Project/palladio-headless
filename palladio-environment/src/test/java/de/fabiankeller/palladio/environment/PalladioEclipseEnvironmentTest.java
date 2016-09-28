package de.fabiankeller.palladio.environment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

public class PalladioEclipseEnvironmentTest {

    @Before
    public void setUp() throws Exception {
        // pretend that setup has not yet been called
        final Field isSetup = PalladioEclipseEnvironment.class.getDeclaredField("isSetup");
        isSetup.setAccessible(true);
        isSetup.set(PalladioEclipseEnvironment.INSTANCE, false);
    }

    @Test
    public void setup_doesSetup() {
        Assert.assertFalse(PalladioEclipseEnvironment.INSTANCE.isSetup());
        PalladioEclipseEnvironment.INSTANCE.setup();
        Assert.assertTrue(PalladioEclipseEnvironment.INSTANCE.isSetup());
    }

    @Test
    public void setup_calledMultipleTimes_doesNotFail() {
        PalladioEclipseEnvironment.INSTANCE.setup();
        PalladioEclipseEnvironment.INSTANCE.setup();
        PalladioEclipseEnvironment.INSTANCE.setup();
        Assert.assertTrue(PalladioEclipseEnvironment.INSTANCE.isSetup());
    }
}