package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookingAtIndex;
import static seedu.address.testutil.TypicalBookings.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOKING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOKING;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.booking.Booking;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UnmarkBookingCommand.
 */
public class UnmarkBookingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Booking bookingToUnmark = model.getModifiedBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());

        // Ensure the booking is marked
        if (!bookingToUnmark.isDone()) {
            bookingToUnmark = bookingToUnmark.markBooking();
            model.setBooking(model.getModifiedBookingList().get(INDEX_FIRST_BOOKING.getZeroBased()), bookingToUnmark);
        }

        UnmarkBookingCommand unmarkBookingCommand = new UnmarkBookingCommand(INDEX_FIRST_BOOKING);
        Booking unmarkedBooking = bookingToUnmark.unMarkBooking();

        String expectedMessage = String.format(UnmarkBookingCommand.MESSAGE_SUCCESS,
                Messages.format(unmarkedBooking));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setBooking(bookingToUnmark, unmarkedBooking);

        assertCommandSuccess(unmarkBookingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getModifiedBookingList().size() + 1);
        UnmarkBookingCommand unmarkBookingCommand = new UnmarkBookingCommand(outOfBoundIndex);

        assertCommandFailure(unmarkBookingCommand, model, UnmarkBookingCommand.MESSAGE_NOTFOUND);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookingAtIndex(model, INDEX_FIRST_BOOKING);

        Booking bookingToUnmark = model.getModifiedBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());

        // Ensure the booking is marked
        if (!bookingToUnmark.isDone()) {
            bookingToUnmark = bookingToUnmark.markBooking();
            model.setBooking(model.getModifiedBookingList().get(INDEX_FIRST_BOOKING.getZeroBased()), bookingToUnmark);
        }

        UnmarkBookingCommand unmarkBookingCommand = new UnmarkBookingCommand(INDEX_FIRST_BOOKING);
        Booking unmarkedBooking = bookingToUnmark.unMarkBooking();

        String expectedMessage = String.format(UnmarkBookingCommand.MESSAGE_SUCCESS,
                Messages.format(unmarkedBooking));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showBookingAtIndex(expectedModel, INDEX_FIRST_BOOKING);

        Booking bookingInExpectedModel = expectedModel.getModifiedBookingList()
                .get(INDEX_FIRST_BOOKING.getZeroBased());
        if (!bookingInExpectedModel.isDone()) {
            bookingInExpectedModel = bookingInExpectedModel.markBooking();
            expectedModel.setBooking(
                    expectedModel.getModifiedBookingList().get(INDEX_FIRST_BOOKING.getZeroBased()),
                    bookingInExpectedModel);
        }
        expectedModel.setBooking(bookingInExpectedModel, bookingInExpectedModel.unMarkBooking());

        assertCommandSuccess(unmarkBookingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookingAtIndex(model, INDEX_FIRST_BOOKING);

        Index outOfBoundIndex = INDEX_SECOND_BOOKING;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getBookingList().size());

        UnmarkBookingCommand unmarkBookingCommand = new UnmarkBookingCommand(outOfBoundIndex);

        assertCommandFailure(unmarkBookingCommand, model, UnmarkBookingCommand.MESSAGE_NOTFOUND);
    }

    @Test
    public void execute_alreadyUnmarkedBooking_throwsCommandException() {
        Booking bookingToUnmark = model.getModifiedBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());

        // Ensure the booking is not marked
        if (bookingToUnmark.isDone()) {
            Booking unmarkedBooking = bookingToUnmark.unMarkBooking();
            model.setBooking(bookingToUnmark, unmarkedBooking);
        }

        UnmarkBookingCommand unmarkBookingCommand = new UnmarkBookingCommand(INDEX_FIRST_BOOKING);

        assertCommandFailure(unmarkBookingCommand, model, UnmarkBookingCommand.MESSAGE_DUPLICATE);
    }

    @Test
    public void equals() {
        UnmarkBookingCommand unmarkFirstCommand = new UnmarkBookingCommand(INDEX_FIRST_BOOKING);
        UnmarkBookingCommand unmarkSecondCommand = new UnmarkBookingCommand(INDEX_SECOND_BOOKING);

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        UnmarkBookingCommand unmarkFirstCommandCopy = new UnmarkBookingCommand(INDEX_FIRST_BOOKING);
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmarkFirstCommand.equals(null));

        // different booking -> returns false
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnmarkBookingCommand unmarkBookingCommand = new UnmarkBookingCommand(targetIndex);
        String expected = UnmarkBookingCommand.class.getCanonicalName() + "{bookingindex=" + targetIndex + "}";
        assertEquals(expected, unmarkBookingCommand.toString());
    }
}
