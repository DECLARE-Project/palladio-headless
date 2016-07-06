package de.fabiankeller.palladio.builder.repository.seff.impl;

import de.fabiankeller.palladio.builder.BuilderException;
import de.fabiankeller.palladio.builder.repository.ComponentBuilder;
import de.fabiankeller.palladio.builder.repository.InterfaceBuilder;
import de.fabiankeller.palladio.builder.repository.ParameterType;
import de.fabiankeller.palladio.builder.repository.SignatureBuilder;
import de.fabiankeller.palladio.builder.repository.impl.RepositoryBuilderImpl;
import de.fabiankeller.palladio.builder.repository.seff.ResourceDemandBuilder;
import org.eclipse.emf.common.util.EList;
import org.junit.Before;
import org.junit.Test;
import org.palladiosimulator.pcm.seff.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResourceDemandBuilderImplTest {
    private ResourceDemandBuilder<ComponentBuilder> sut;

    private RepositoryBuilderImpl repo;

    private ComponentBuilder c_booking;

    private InterfaceBuilder i_payment;
    private SignatureBuilder s_pay;

    private InterfaceBuilder i_reservation;

    @Before
    public void setup() {
        this.repo = new RepositoryBuilderImpl();
        this.i_payment = this.repo.withInterface("IPaymentGateway");
        this.s_pay = this.i_payment.createOperation("pay").withParameter("IBAN", ParameterType.STRING);
        this.i_reservation = this.repo.withInterface("IRoomReservation");


        this.c_booking = this.repo.withComponent("Booking")
                .provides(this.i_payment)
                .provides(this.i_reservation);
        this.sut = this.c_booking.withServiceEffectSpecification(this.s_pay);
    }

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
    public void stop_withoutStartAction_throwsException() {
        this.sut.stop();
    }

    @SafeVarargs
    private final void assertActionIsOfType(final ResourceDemandingBehaviour rdb, final Class<? extends AbstractAction>... type) {
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
