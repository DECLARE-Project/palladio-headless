package de.fakeller.palladio.builder.repository.impl;


import de.fakeller.palladio.builder.repository.RepositoryBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class RepositoryBuilderTest {

    private RepositoryBuilder sut;

    @Before
    public void setup() {
        this.sut = new RepositoryBuilderImpl();
    }

    @Test
    public void testCreateInterface() {
        assertEquals(0, this.sut.getReference().getInterfaces__Repository().size());
        this.sut.withInterface("IBooking");
        assertEquals(1, this.sut.getReference().getInterfaces__Repository().size());
        assertEquals("IBooking", this.sut.getReference().getInterfaces__Repository().get(0).getEntityName());
    }

    @Test
    public void testCreateComponent() {
        assertEquals(0, this.sut.getReference().getComponents__Repository().size());
        this.sut.withComponent("BookingSystem");
        assertEquals(1, this.sut.getReference().getComponents__Repository().size());
        assertEquals("BookingSystem", this.sut.getReference().getComponents__Repository().get(0).getEntityName());
    }
}
