package de.fakeller.palladio.environment.uriconverter;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrefixConverterTest {


    @Test
    public void canConvert_withValidPrefix_returnsTrue() throws Exception {
        assertTrue(new PrefixConverter("foo", "bar").canConvert(URI.createURI("foo://test")));
    }

    @Test
    public void canConvert_withOtherPrefix_returnsFalse() throws Exception {
        assertFalse(new PrefixConverter("foo", "bar").canConvert(URI.createURI("xyz://test")));
    }

    @Test
    public void convert_withValidPrefix_convertsUri() throws Exception {
        final PrefixConverter conv = new PrefixConverter("foo", "bar");
        assertSame(URI.createURI("bar://test"), conv.convert(URI.createURI("foo://test")));
    }
}