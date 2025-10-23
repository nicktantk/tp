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
 * Contains integration tests (interaction with the Model) and unit tests for MarkBookingCommand.
 */
public class MarkBookingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Booking bookingToMark = model.getModifiedBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());

        // Ensure the booking is not already marked
        if (bookingToMark.isPaid()) {
            bookingToMark = bookingToMark.unMarkBooking();
            model.setBooking(model.getModifiedBookingList().get(INDEX_FIRST_BOOKING.getZeroBased()), bookingToMark);
        }

        MarkBookingCommand markBookingCommand = new MarkBookingCommand(INDEX_FIRST_BOOKING);
        Booking markedBooking = bookingToMark.markBooking();

        String expectedMessage = String.format(MarkBookingCommand.MESSAGE_SUCCESS,
                Messages.format(markedBooking));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setBooking(bookingToMark, markedBooking);

        assertCommandSuccess(markBookingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getModifiedBookingList().size() + 1);
        MarkBookingCommand markBookingCommand = new MarkBookingCommand(outOfBoundIndex);

        assertCommandFailure(markBookingCommand, model, MarkBookingCommand.MESSAGE_NOTFOUND);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookingAtIndex(model, INDEX_FIRST_BOOKING);

        Booking bookingToMark = model.getModifiedBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());

        // Ensure the booking is not already marked
        if (bookingToMark.isPaid()) {
            bookingToMark = bookingToMark.unMarkBooking();
            model.setBooking(model.getModifiedBookingList().get(INDEX_FIRST_BOOKING.getZeroBased()), bookingToMark);
        }

        MarkBookingCommand markBookingCommand = new MarkBookingCommand(INDEX_FIRST_BOOKING);
        Booking markedBooking = bookingToMark.markBooking();

        String expectedMessage = String.format(MarkBookingCommand.MESSAGE_SUCCESS,
                Messages.format(markedBooking));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showBookingAtIndex(expectedModel, INDEX_FIRST_BOOKING);

        Booking bookingInExpectedModel = expectedModel.getModifiedBookingList()
                .get(INDEX_FIRST_BOOKING.getZeroBased());
        if (bookingInExpectedModel.isPaid()) {
            bookingInExpectedModel = bookingInExpectedModel.unMarkBooking();
            expectedModel.setBooking(
                    expectedModel.getModifiedBookingList().get(INDEX_FIRST_BOOKING.getZeroBased()),
                    bookingInExpectedModel);
        }
        expectedModel.setBooking(bookingInExpectedModel, bookingInExpectedModel.markBooking());

        assertCommandSuccess(markBookingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookingAtIndex(model, INDEX_FIRST_BOOKING);

        Index outOfBoundIndex = INDEX_SECOND_BOOKING;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getBookingList().size());

        MarkBookingCommand markBookingCommand = new MarkBookingCommand(outOfBoundIndex);

        assertCommandFailure(markBookingCommand, model, MarkBookingCommand.MESSAGE_NOTFOUND);
    }

    @Test
    public void execute_alreadyMarkedBooking_throwsCommandException() {
        Booking bookingToMark = model.getModifiedBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());

        // Ensure the booking is already marked
        if (!bookingToMark.isPaid()) {
            Booking markedBooking = bookingToMark.markBooking();
            model.setBooking(bookingToMark, markedBooking);
        }

        MarkBookingCommand markBookingCommand = new MarkBookingCommand(INDEX_FIRST_BOOKING);

        assertCommandFailure(markBookingCommand, model, MarkBookingCommand.MESSAGE_DUPLICATE);
    }

    @Test
    public void equals() {
        MarkBookingCommand markFirstCommand = new MarkBookingCommand(INDEX_FIRST_BOOKING);
        MarkBookingCommand markSecondCommand = new MarkBookingCommand(INDEX_SECOND_BOOKING);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkBookingCommand markFirstCommandCopy = new MarkBookingCommand(INDEX_FIRST_BOOKING);
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different booking -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MarkBookingCommand markBookingCommand = new MarkBookingCommand(targetIndex);
        String expected = MarkBookingCommand.class.getCanonicalName() + "{bookingindex=" + targetIndex + "}";
        assertEquals(expected, markBookingCommand.toString());
    }
}
