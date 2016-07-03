package de.fabiankeller.palladio.analysis.provider;

import de.fabiankeller.palladio.analysis.PcmProvider;
import de.fabiankeller.palladio.builder.PcmBuilder;
import de.fabiankeller.palladio.builder.repository.ComponentBuilder;
import de.fabiankeller.palladio.builder.repository.InterfaceBuilder;
import de.fabiankeller.palladio.builder.repository.ParameterType;
import de.fabiankeller.palladio.builder.repository.SignatureBuilder;
import de.fabiankeller.palladio.builder.resourceenvironment.ContainerBuilder;
import de.fabiankeller.palladio.builder.resourceenvironment.LinkBuilder;
import de.fabiankeller.palladio.builder.system.AssemblyBuilder;
import org.palladiosimulator.solver.models.PCMInstance;

public class SimpleTacticsProvider implements PcmProvider {

    @Override
    public PCMInstance provide() {
        final PcmBuilder builder = new PcmBuilder();

        // // // REPOSITORY // // //

        // create interfaces in repository
        final InterfaceBuilder i_businessTrip = builder.repository().withInterface("IBusinessTrip");
        final SignatureBuilder s_plan = i_businessTrip.createOperation("plan")
                .withParameter("isBook", ParameterType.BOOL)
                .withParameter("isBank", ParameterType.BOOL);

        final InterfaceBuilder i_booking = builder.repository().withInterface("IBooking");
        final SignatureBuilder s_book = i_booking.createOperation("book")
                .withParameter("isBank", ParameterType.BOOL);

        final InterfaceBuilder i_employeePayment = builder.repository().withInterface("IEmployeePayment");
        final SignatureBuilder s_reimburse = i_employeePayment.createOperation("reimburse");


        final InterfaceBuilder i_externalPayment = builder.repository().withInterface("IExternalPayment");
        final SignatureBuilder s_isBank = i_externalPayment.createOperation("pay")
                .withParameter("isBank", ParameterType.BOOL);


        // create components in repository
        final ComponentBuilder c_businessTripMgmt = builder.repository().withComponent("BusinessTripMgmt")
                .provides(i_businessTrip)
                .requires(i_booking)
                .requires(i_employeePayment);
        //@formatter:off
        c_businessTripMgmt.withServiceEffectSpecification(s_plan)
                .start()
                .internalAction("action")
                    .withCpuDemand("4")
                .end()
                .branch("aName")
                    .createBranch("aName", "isBook.VALUE")
                        .start()
                        .externalCall()
                            .withVariableUsage("isBank", "isBank.VALUE")
                        .end()
                        .stop()
                    .end()
                .end()
                .stop();
        //@formatter:on
        final ComponentBuilder c_bookingSystem = builder.repository().withComponent("BookingSystem")
                .provides(i_booking)
                .requires(i_externalPayment);
        final ComponentBuilder c_paymentSystem = builder.repository().withComponent("PaymentSystem")
                .provides(i_employeePayment)
                .provides(i_externalPayment);
        final ComponentBuilder c_quickBooking = builder.repository().withComponent("QuickBooking")
                .provides(i_booking)
                .requires(i_externalPayment);


        // // // SYSTEM // // //
        final AssemblyBuilder a_businessTripMgmt = builder.system().assemble(c_businessTripMgmt);
        final AssemblyBuilder a_bookingSystem = builder.system().assemble(c_bookingSystem);
        final AssemblyBuilder a_paymentSystem = builder.system().assemble(c_paymentSystem);

        a_businessTripMgmt.provideToSystem(i_businessTrip);
        a_bookingSystem.provideFor(a_businessTripMgmt, i_booking);
        a_paymentSystem.provideFor(a_businessTripMgmt, i_employeePayment);
        a_bookingSystem.requireFrom(a_paymentSystem, i_externalPayment);


        // // // RESOURCE ENVIRONMENT // // //
        final ContainerBuilder e_server1 = builder.resourceEnvironment().createContainer("Server1")
                .withCpu(3.5)
                .withMTTF(300000.0)
                .withMTTR(6.0)
                .withNumberOfReplicas(1)
                .withRequiredByContainer(false)
                .end();
        final ContainerBuilder e_server2 = builder.resourceEnvironment().createContainer("Server2")
                .withCpu(4.0)
                .withMTTF(250000.0)
                .withMTTR(3.0)
                .withNumberOfReplicas(1)
                .withRequiredByContainer(false)
                .end();
        final ContainerBuilder e_server3 = builder.resourceEnvironment().createContainer("Server3")
                .withCpu(3.0)
                .withMTTF(275000.0)
                .withMTTR(4.0)
                .withNumberOfReplicas(1)
                .withRequiredByContainer(false)
                .end();
        final LinkBuilder e_network = builder.resourceEnvironment().createLink("Network")
                .between(e_server1, e_server2, e_server3)
                .withLatency(0.015)
                .withThroughput(2000000000)
                .withFailureProbability(2.0e-4);

        // // // ALLOCATION // // //
        builder.allocation().allocate(a_businessTripMgmt, e_server1);
        builder.allocation().allocate(a_bookingSystem, e_server2);
        builder.allocation().allocate(a_paymentSystem, e_server3);

        // // // USAGE // // //
        builder.usage().createScenario("defaultUsageScenario")
                .withOpenWorkload(0.5)
                .withBehaviour()
                .start()
                .entryLevelSystemCall(s_plan)
                .stop();

        return builder.build();
    }
}
