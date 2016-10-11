package de.fakeller.palladio.builder.system.impl;


import de.fakeller.palladio.builder.BuilderException;
import de.fakeller.palladio.builder.repository.ComponentBuilder;
import de.fakeller.palladio.builder.repository.InterfaceBuilder;
import de.fakeller.palladio.builder.repository.impl.RepositoryBuilderImpl;
import de.fakeller.palladio.builder.system.AssemblyBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AssemblyBuilderTest {
    private SystemBuilderImpl system;

    private RepositoryBuilderImpl repo;

    private ComponentBuilder c_payment;
    private ComponentBuilder c_booking;
    private ComponentBuilder c_other;

    private InterfaceBuilder i_payment;
    private InterfaceBuilder i_reservation;

    private AssemblyBuilder a_payment;
    private AssemblyBuilder a_booking;
    private AssemblyBuilder a_other;

    @Before
    public void setup() {
        this.system = new SystemBuilderImpl();
        this.repo = new RepositoryBuilderImpl();

        this.i_payment = this.repo.withInterface("IPaymentGateway");
        this.i_reservation = this.repo.withInterface("IRoomReservation");

        this.c_payment = this.repo.withComponent("Payment");
        this.c_payment.provides(this.i_payment);
        this.c_booking = this.repo.withComponent("Booking");
        this.c_booking.requires(this.i_payment);
        this.c_booking.provides(this.i_reservation);
        this.c_other = this.repo.withComponent("Other Component With No Functionality");

        this.a_payment = this.system.assemble(this.c_payment);
        this.a_booking = this.system.assemble(this.c_booking);
        this.a_other = this.system.assemble(this.c_other);
    }

    // // CONNECTING ASSEMBLIES // //

    @Test
    public void testConnectBookingToPayment() {
        Assert.assertEquals(0, this.system.getReference().getConnectors__ComposedStructure().size());
        this.a_booking.requireFrom(this.a_payment, this.i_payment);
        Assert.assertEquals(1, this.system.getReference().getConnectors__ComposedStructure().size());
    }

    @Test
    public void testConnectBookingToPaymentReverse() {
        Assert.assertEquals(0, this.system.getReference().getConnectors__ComposedStructure().size());
        this.a_payment.provideFor(this.a_booking, this.i_payment);
        Assert.assertEquals(1, this.system.getReference().getConnectors__ComposedStructure().size());
    }


    @Test(expected = BuilderException.class)
    public void provideFor_withoutInterface_throwsException() {
        this.a_payment.provideFor(this.a_other, this.i_reservation);
    }

    @Test(expected = BuilderException.class)
    public void provideFor_withoutTargetHavingInterface_throwsException() {
        this.a_payment.provideFor(this.a_other, this.i_payment);
    }

    @Test(expected = BuilderException.class)
    public void requireFrom_withoutInterface_throwsException() {
        this.a_booking.requireFrom(this.a_other, this.i_reservation);
    }

    @Test(expected = BuilderException.class)
    public void requireFrom_withoutTargetHavingInterface_throwsException() {
        this.a_booking.requireFrom(this.a_other, this.i_payment);
    }


    // // CONNECTING TO SYSTEM // //

    @Test
    public void testProvideToSystem() {
        Assert.assertEquals(0, this.system.getReference().getConnectors__ComposedStructure().size());
        Assert.assertEquals(0, this.system.getReference().getProvidedRoles_InterfaceProvidingEntity().size());

        this.a_payment.provideToSystem(this.i_payment);

        Assert.assertEquals(1, this.system.getReference().getConnectors__ComposedStructure().size());
        Assert.assertEquals(1, this.system.getReference().getProvidedRoles_InterfaceProvidingEntity().size());
    }

    @Test
    public void testProvideMultipleToSystem() {
        Assert.assertEquals(0, this.system.getReference().getConnectors__ComposedStructure().size());
        Assert.assertEquals(0, this.system.getReference().getProvidedRoles_InterfaceProvidingEntity().size());

        this.a_payment.provideToSystem(this.i_payment);
        this.a_booking.provideToSystem(this.i_reservation);

        Assert.assertEquals(2, this.system.getReference().getConnectors__ComposedStructure().size());
        Assert.assertEquals(2, this.system.getReference().getProvidedRoles_InterfaceProvidingEntity().size());
    }

    @Test(expected = BuilderException.class)
    public void provideToSystem_withIncorrectInterface_throwsException() {
        this.a_payment.provideToSystem(this.i_reservation);
    }

    @Test
    public void testRequireFromSystem() {
        Assert.assertEquals(0, this.system.getReference().getConnectors__ComposedStructure().size());
        Assert.assertEquals(0, this.system.getReference().getRequiredRoles_InterfaceRequiringEntity().size());

        this.a_booking.requireFromSystem(this.i_payment);

        Assert.assertEquals(1, this.system.getReference().getConnectors__ComposedStructure().size());
        Assert.assertEquals(1, this.system.getReference().getRequiredRoles_InterfaceRequiringEntity().size());
    }

    @Test(expected = BuilderException.class)
    public void requireFromSystem_withIncorrectInterface_throwsException() {
        this.a_booking.requireFromSystem(this.i_reservation);
    }
}
