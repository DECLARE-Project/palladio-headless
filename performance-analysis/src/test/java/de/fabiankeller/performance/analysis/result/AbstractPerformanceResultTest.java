package de.fabiankeller.performance.analysis.result;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AbstractPerformanceResultTest {

    private AbstractPerformanceResult<String> sut;

    @Before
    public void setUp() throws Exception {
        this.sut = new AbstractPerformanceResult<String>() {
        };
    }

    @Test
    public void attach() throws Exception {
        assertFalse(this.sut.hasResults());
        mockResult("node");
        assertTrue(this.sut.hasResults());
    }

    @Test
    public void getResults() throws Exception {
        final Result<String> res0 = mockResult("node3");
        final Result<String> res1 = mockResult("node1");
        final Result<String> res2 = mockResult("node1");
        final Result<String> res3 = mockResult("node2");
        assertEquals(Arrays.asList(res0, res1, res2, res3), this.sut.getResults());
    }

    @Test
    public void getElementsHavingResults() throws Exception {
        final Result<String> res1 = mockResult("node1");
        final Result<String> res2 = mockResult("node1");
        final Result<String> res3 = mockResult("node2");
        assertEquals(Stream.of("node1", "node2").collect(Collectors.toSet()), this.sut.getElementsHavingResults());
    }

    @Test
    public void hasResultsForElement() throws Exception {
        assertFalse(this.sut.hasResults("node1"));
        assertFalse(this.sut.hasResults("node2"));
        mockResult("node1");
        assertTrue(this.sut.hasResults("node1"));
        assertFalse(this.sut.hasResults("node2"));
    }

    @Test
    public void getResultsForElement() throws Exception {
        mockResult("node3");
        final Result<String> res1 = mockResult("node1");
        final Result<String> res2 = mockResult("node1");
        final Result<String> res3 = mockResult("node2");
        assertEquals(Arrays.asList(res1, res2), this.sut.getResults("node1"));
        assertEquals(Arrays.asList(res3), this.sut.getResults("node2"));
    }


    private Result<String> mockResult(final String attachTo) {
        final Result mock = mock(Result.class);
        when(mock.attachedTo()).thenReturn(attachTo);
        this.sut.attach(mock);
        return mock;
    }
}