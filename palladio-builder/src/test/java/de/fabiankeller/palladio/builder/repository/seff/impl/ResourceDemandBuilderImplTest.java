package de.fabiankeller.palladio.builder.repository.seff.impl;

import de.fabiankeller.palladio.builder.BuilderException;
import de.fabiankeller.palladio.builder.repository.*;
import de.fabiankeller.palladio.builder.repository.impl.RepositoryBuilderImpl;
import de.fabiankeller.palladio.builder.repository.seff.ResourceDemandBuilder;
import de.fabiankeller.palladio.config.EnvironmentConfig;
import de.fabiankeller.palladio.environment.PalladioEclipseEnvironment;
import de.fabiankeller.palladio.environment.util.PalladioResourceRepository;
import org.eclipse.emf.common.util.EList;
import org.junit.Before;
import org.junit.Test;
import org.palladiosimulator.pcm.seff.*;
import org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

public class ResourceDemandBuilderImplTest {
    private ResourceDemandBuilder<ComponentBuilder> sut;

    private RepositoryBuilder repo;

    private ComponentBuilder c_booking;

    private InterfaceBuilder i_payment;
    private SignatureBuilder s_pay;

    private InterfaceBuilder i_reservation;

    @Before
    public void setup() {
        // todo: eclipse env setup for tests needs to be put somewhere else
        final Properties runnerConfig = new Properties();
        try {
            final BufferedInputStream stream = new BufferedInputStream(new FileInputStream(new File("src/main/resources/config.properties")));
            runnerConfig.load(stream);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        final EnvironmentConfig config = new EnvironmentConfig(runnerConfig);
        PalladioEclipseEnvironment.INSTANCE.setup(config);


        this.repo = new RepositoryBuilderImpl();
        this.i_payment = this.repo.withInterface("IPaymentGateway");
        this.s_pay = this.i_payment.createOperation("pay").withParameter("IBAN", ParameterType.STRING);
        this.i_reservation = this.repo.withInterface("IRoomReservation");


        this.c_booking = this.repo.withComponent("Booking")
                .requires(this.i_payment)
                .requires(this.i_reservation);
        this.sut = this.c_booking.withServiceEffectSpecification(this.s_pay);
    }

    // // START & STOP // //

    @Test
    public void start_asFirstAction_canBeAdded() {
        assertEquals(0, this.sut.getReference().getSteps_Behaviour().size());
        this.sut.start();
        assertEquals(1, this.sut.getReference().getSteps_Behaviour().size());
        assertActionIsOfType(this.sut.getReference(), 0, StartAction.class);
    }

    @Test(expected = BuilderException.class)
    public void start_withExistingActions_cannotBeAddedAgain() {
        this.sut.start().start();
    }

    @Test(expected = BuilderException.class)
    public void stop_withoutStartAction_throwsException() {
        this.sut.stop();
    }


    // // INTERNAL ACTION // //

    @Test
    public void internalAction_withoutNameAndDemands_isCreated() {
        this.sut.start();
        this.sut.internalAction();
        this.sut.stop();
        this.assertActionIsOfType(this.sut.getReference(), StartAction.class, InternalAction.class, StopAction.class);
    }

    @Test(expected = BuilderException.class)
    public void internalAction_withoutPrecedingStart_shouldThrowException() {
        this.sut.internalAction();
    }

    @Test
    public void internalAction_withDemand_shouldCreatedDemands() {
        this.sut.start();
        this.sut.internalAction()
                .withCpuDemand("1000")
                .withDelayDemand("2000")
                .withHddDemand("3000");
        final InternalAction action = (InternalAction) this.sut.getReference().getSteps_Behaviour().get(1);
        final EList<ParametricResourceDemand> nested = action.getResourceDemand_Action();

        assertSame(PalladioResourceRepository.INSTANCE.resources().cpu(), nested.get(0).getRequiredResource_ParametricResourceDemand());
        assertEquals("1000", nested.get(0).getSpecification_ParametericResourceDemand().getSpecification());

        assertSame(PalladioResourceRepository.INSTANCE.resources().delay(), nested.get(1).getRequiredResource_ParametricResourceDemand());
        assertEquals("2000", nested.get(1).getSpecification_ParametericResourceDemand().getSpecification());

        assertSame(PalladioResourceRepository.INSTANCE.resources().hdd(), nested.get(2).getRequiredResource_ParametricResourceDemand());
        assertEquals("3000", nested.get(2).getSpecification_ParametericResourceDemand().getSpecification());
    }


    // // BRANCHING // //

    @Test
    public void branch_withTwoBranches_willCreateNestedResourceDemands() {
        // @formatter:off
        this.sut
            .start()
            .branch()
                .createBranch("iban", "IBAN.VALUE ~= DE")
                    .start()
                    .stop()
                .end()
                .createBranch("iban2", "IBAN.VALUE !~= DE")
                    .start()
                    .stop()
                .end()
            .end()
            .stop();
        // @formatter:on

        assertActionIsOfType(this.sut.getReference(), StartAction.class, BranchAction.class, StopAction.class);
        final BranchAction branchAction = (BranchAction) this.sut.getReference().getSteps_Behaviour().get(1);
        assertActionIsOfType(branchAction.getBranches_Branch().get(0).getBranchBehaviour_BranchTransition(), StartAction.class, StopAction.class);
        assertActionIsOfType(branchAction.getBranches_Branch().get(1).getBranchBehaviour_BranchTransition(), StartAction.class, StopAction.class);
    }

    @Test(expected = BuilderException.class)
    public void branch_withoutPrecedingStart_shouldThrowException() {
        this.sut.branch();
    }

    // // EXTERNAL CALL // //

    @Test
    public void externalCall_withValidSignature_isAdded() {
        this.sut
                .start()
                .externalCall(this.s_pay)
                .withRetryCount(2)
                .end()
                .stop();
        assertActionIsOfType(this.sut.getReference(), StartAction.class, ExternalCallAction.class, StopAction.class);

        final ExternalCallAction extCall = (ExternalCallAction) this.sut.getReference().getSteps_Behaviour().get(1);
        assertEquals(2, extCall.getRetryCount());
        assertSame(this.s_pay.getReference(), extCall.getCalledService_ExternalService());
        assertSame(this.i_payment.getReference(), extCall.getRole_ExternalService().getRequiredInterface__OperationRequiredRole());
    }

    @Test(expected = BuilderException.class)
    public void externalCall_withoutPrecedingStart_shouldThrowException() {
        this.sut.externalCall();
    }


    // // ASSERTION HELPERS // //

    @SafeVarargs
    private final void assertActionIsOfType(final ResourceDemandingBehaviour rdb, final Class<? extends AbstractAction>... type) {
        final int noSteps = rdb.getSteps_Behaviour().size();
        assertEquals(String.format("Expected %d actions, but found %d.", type.length, noSteps), type.length, noSteps);

        for (int pos = 0; pos < type.length; pos++) {
            this.assertActionIsOfType(rdb, pos, type[pos]);
        }
    }

    private void assertActionIsOfType(final ResourceDemandingBehaviour rdb, final int index, final Class<? extends AbstractAction> type) {
        final EList<AbstractAction> steps = rdb.getSteps_Behaviour();
        assertTrue(steps.size() > index);

        final AbstractAction action = steps.get(index);
        assertTrue(String.format("Expected action at index %d to be of type %s, but found %s.", index, type.getName(), action.getClass().getName()), type.isInstance(action));
    }
}
