package de.fabiankeller.palladio.builder.system.impl;

import de.fabiankeller.palladio.builder.repository.ComponentBuilder;
import de.fabiankeller.palladio.builder.repository.InterfaceBuilder;
import de.fabiankeller.palladio.builder.repository.impl.RepositoryBuilderImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SystemBuilderTest {
    private SystemBuilderImpl sut;

    private RepositoryBuilderImpl repo;

    private ComponentBuilder c_payment;
    private ComponentBuilder c_booking;

    private InterfaceBuilder i_payment;
    private InterfaceBuilder i_reservation;

    @Before
    public void setup() {
        this.sut = new SystemBuilderImpl();
        this.repo = new RepositoryBuilderImpl();
        this.c_payment = this.repo.withComponent("Payment");
        this.c_booking = this.repo.withComponent("Booking");
        this.i_payment = this.repo.withInterface("IPaymentGateway");
        this.i_reservation = this.repo.withInterface("IRoomReservation");
    }

    @Test
    public void testCreateAssembly() {
        Assert.assertEquals(0, this.sut.getReference().getAssemblyContexts__ComposedStructure().size());
        this.sut.assemble(this.c_payment);
        Assert.assertEquals(1, this.sut.getReference().getAssemblyContexts__ComposedStructure().size());
    }

    @Test
    public void testCreateMultipleAssemblies() {
        Assert.assertEquals(0, this.sut.getReference().getAssemblyContexts__ComposedStructure().size());
        this.sut.assemble(this.c_payment);
        this.sut.assemble(this.c_payment);
        this.sut.assemble(this.c_booking);
        Assert.assertEquals(3, this.sut.getReference().getAssemblyContexts__ComposedStructure().size());
    }

}
