package de.fakeller.palladio.builder.repository.impl;


import de.fakeller.palladio.builder.repository.InterfaceBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class ComponentBuilderTest {

    private RepositoryBuilderImpl repo;
    private ComponentBuilderImpl booking;

    private InterfaceBuilder i_payment;
    private InterfaceBuilder i_reservation;

    @Before
    public void setup() {
        this.repo = new RepositoryBuilderImpl();
        this.booking = (ComponentBuilderImpl) this.repo.withComponent("Booking");
        this.i_payment = this.repo.withInterface("IPaymentGateway");
        this.i_reservation = this.repo.withInterface("IRoomReservation");
    }

    @Test
    public void testProvideInterface() {
        assertEquals(0, this.booking.getReference().getProvidedRoles_InterfaceProvidingEntity().size());
        this.booking.provides(this.i_reservation);
        assertEquals(1, this.booking.getReference().getProvidedRoles_InterfaceProvidingEntity().size());
        assertSame(this.booking.getReference(), this.booking.getReference().getProvidedRoles_InterfaceProvidingEntity().get(0).getProvidingEntity_ProvidedRole());
    }

    @Test
    public void testRequireInterface() {
        assertEquals(0, this.booking.getReference().getRequiredRoles_InterfaceRequiringEntity().size());
        this.booking.requires(this.i_payment);
        assertEquals(1, this.booking.getReference().getRequiredRoles_InterfaceRequiringEntity().size());
        assertSame(this.booking.getReference(), this.booking.getReference().getRequiredRoles_InterfaceRequiringEntity().get(0).getRequiringEntity_RequiredRole());
    }
}
