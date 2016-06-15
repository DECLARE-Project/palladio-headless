package de.fabiankeller.palladio.builder;


import de.fabiankeller.palladio.builder.repository.ComponentBuilder;
import de.fabiankeller.palladio.builder.repository.InterfaceBuilder;
import de.fabiankeller.palladio.builder.repository.ParameterType;
import de.fabiankeller.palladio.builder.repository.RepositoryBuilder;
import de.fabiankeller.palladio.builder.repository.impl.RepositoryBuilderImpl;
import de.fabiankeller.palladio.builder.resourceenvironment.ContainerBuilder;
import de.fabiankeller.palladio.builder.resourceenvironment.LinkBuilder;
import de.fabiankeller.palladio.builder.resourceenvironment.ResourceEnvironmentBuilder;
import de.fabiankeller.palladio.builder.resourceenvironment.impl.ResourceEnvironmentBuilderImpl;
import de.fabiankeller.palladio.builder.system.AssemblyBuilder;
import de.fabiankeller.palladio.builder.system.SystemBuilder;
import de.fabiankeller.palladio.builder.system.impl.SystemBuilderImpl;

// fixme: include junit and turn it into a real test
public class SimpleTacticsBuilderTest {

    private final RepositoryBuilder repo = new RepositoryBuilderImpl();
    private final SystemBuilder system = new SystemBuilderImpl();
    // fixme: DI instead of null
    private final ResourceEnvironmentBuilder env = new ResourceEnvironmentBuilderImpl(null);

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


        // // // RESOURCE ENVIRONMENT // // //
        final ContainerBuilder e_server1 = this.env.createContainer("Server1")
                .withCpu(3.5)
                .withMTTF(300000.0)
                .withMTTR(6.0)
                .withNumberOfReplicas(1)
                .withRequiredByContainer(false)
                .end();
        final ContainerBuilder e_server2 = this.env.createContainer("Server2")
                .withCpu(4.0)
                .withMTTF(250000.0)
                .withMTTR(3.0)
                .withNumberOfReplicas(1)
                .withRequiredByContainer(false)
                .end();
        final ContainerBuilder e_server3 = this.env.createContainer("Server3")
                .withCpu(3.0)
                .withMTTF(275000.0)
                .withMTTR(4.0)
                .withNumberOfReplicas(1)
                .withRequiredByContainer(false)
                .end();
        final LinkBuilder e_network = this.env.createLink("Network")
                .between(e_server1, e_server2, e_server3)
                .withLatency(0.015)
                .withThroughput(2000000000)
                .withFailureProbability(2.0e-4);
    }
}
