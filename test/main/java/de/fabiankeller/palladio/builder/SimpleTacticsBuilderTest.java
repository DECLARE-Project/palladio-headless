package de.fabiankeller.palladio.builder;


import de.fabiankeller.palladio.builder.repository.ComponentBuilder;
import de.fabiankeller.palladio.builder.repository.InterfaceBuilder;
import de.fabiankeller.palladio.builder.repository.ParameterType;
import de.fabiankeller.palladio.builder.repository.RepositoryBuilder;
import de.fabiankeller.palladio.builder.repository.impl.RepositoryBuilderImpl;
import de.fabiankeller.palladio.builder.system.AssemblyBuilder;
import de.fabiankeller.palladio.builder.system.SystemBuilder;
import de.fabiankeller.palladio.builder.system.impl.SystemBuilderImpl;

// fixme: include junit and turn it into a real test
public class SimpleTacticsBuilderTest {

    private final RepositoryBuilder repo = new RepositoryBuilderImpl();
    private final SystemBuilder system = new SystemBuilderImpl();

    public void testSimpleTactics() {

        // // // REPOSITORY // // //

        // create interfaces in repository
        final InterfaceBuilder i_businessTrip = this.repo.withInterface("IBusinessTrip")
                .createOperation("plan")
                .withParameter("isBook", ParameterType.BOOL)
                .withParameter("isBank", ParameterType.BOOL)
                .end();

        final InterfaceBuilder i_booking = this.repo.withInterface("IBooking")
                .createOperation("book")
                .withParameter("isBank", ParameterType.BOOL)
                .end();

        final InterfaceBuilder i_employeePayment = this.repo.withInterface("IEmployeePayment")
                .createOperation("reimburse")
                .end();

        final InterfaceBuilder i_externalPayment = this.repo.withInterface("IExternalPayment")
                .createOperation("pay")
                .withParameter("isBank", ParameterType.BOOL)
                .end();

        // create components in repository
        final ComponentBuilder c_businessTripMgmt = this.repo.withComponent("BusinessTripMgmt")
                .provides(i_businessTrip)
                .requires(i_booking)
                .requires(i_employeePayment);
        final ComponentBuilder c_bookingSystem = this.repo.withComponent("BookingSystem")
                .provides(i_booking)
                .requires(i_externalPayment);
        final ComponentBuilder c_paymentSystem = this.repo.withComponent("PaymentSystem")
                .provides(i_employeePayment)
                .provides(i_externalPayment);
        final ComponentBuilder c_quickBooking = this.repo.withComponent("QuickBooking")
                .provides(i_booking)
                .requires(i_externalPayment);


        // // // SYSTEM // // //
        final AssemblyBuilder a_businessTripMgmt = this.system.assemble(c_businessTripMgmt);
        final AssemblyBuilder a_bookingSystem = this.system.assemble(c_bookingSystem);
        final AssemblyBuilder a_paymentSystem = this.system.assemble(c_paymentSystem);

        a_businessTripMgmt.provideToSystem(i_businessTrip);
        a_bookingSystem.provideFor(a_businessTripMgmt, i_booking);
        a_paymentSystem.provideFor(a_businessTripMgmt, i_employeePayment);
        a_bookingSystem.provideFor(a_paymentSystem, i_externalPayment);
    }
}
