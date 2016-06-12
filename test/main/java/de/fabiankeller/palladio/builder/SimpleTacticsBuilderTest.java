package de.fabiankeller.palladio.builder;


import de.fabiankeller.palladio.builder.repository.ComponentBuilder;
import de.fabiankeller.palladio.builder.repository.InterfaceBuilder;
import de.fabiankeller.palladio.builder.repository.ParameterType;
import de.fabiankeller.palladio.builder.repository.RepositoryBuilder;
import de.fabiankeller.palladio.builder.repository.impl.RepositoryBuilderImpl;
import org.apache.felix.scr.Component;

// fixme: include junit and turn it into a real test
public class SimpleTacticsBuilderTest {

    private RepositoryBuilder repo;

    public void testSimpleTactics() {
        // create interfaces in repository
        InterfaceBuilder i_businessTrip = repo.withInterface("IBusinessTrip")
                .createOperation("plan")
                .withParameter("isBook", ParameterType.BOOL)
                .withParameter("isBank", ParameterType.BOOL)
                .end();

        InterfaceBuilder i_booking = repo.withInterface("IBooking")
                .createOperation("book")
                .withParameter("isBank", ParameterType.BOOL)
                .end();

        InterfaceBuilder i_employeePayment = repo.withInterface("IEmployeePayment")
                .createOperation("reimburse")
                .end();

        InterfaceBuilder i_externalPayment = repo.withInterface("IExternalPayment")
                .createOperation("pay")
                .withParameter("isBank", ParameterType.BOOL)
                .end();

        // create components in repository
        ComponentBuilder c_businessTripMgmt = repo.withComponent("BusinessTripMgmt")
                .provides(i_businessTrip)
                .requires(i_booking)
                .requires(i_employeePayment);
        ComponentBuilder c_bookingSystem = repo.withComponent("BookingSystem")
                .provides(i_booking)
                .requires(i_externalPayment);
        ComponentBuilder c_paymentSystem = repo.withComponent("PaymentSystem")
                .provides(i_employeePayment)
                .provides(i_externalPayment);
        ComponentBuilder c_quickBooking =repo.withComponent("QuickBooking")
                .provides(i_booking)
                .requires(i_externalPayment);
    }
}
