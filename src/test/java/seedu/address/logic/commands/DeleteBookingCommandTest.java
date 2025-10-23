package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookingAtIndex;
import static seedu.address.testutil.TypicalBookings.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOKING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOKING;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.booking.Booking;

public class DeleteBookingCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Booking bookingToDelete = model.getModifiedBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());
        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteBookingCommand.MESSAGE_DELETE_BOOKING_SUCCESS,
                Messages.format(bookingToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteBooking(bookingToDelete);

        assertCommandSuccess(deleteBookingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getModifiedBookingList().size() + 1);
        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(outOfBoundIndex);

        assertCommandFailure(deleteBookingCommand, model, Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookingAtIndex(model, INDEX_FIRST_BOOKING);

        Booking bookingToDelete = model.getModifiedBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());
        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteBookingCommand.MESSAGE_DELETE_BOOKING_SUCCESS,
                Messages.format(bookingToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteBooking(bookingToDelete);
        showNoBooking(expectedModel);

        assertCommandSuccess(deleteBookingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookingAtIndex(model, INDEX_FIRST_BOOKING);

        Index outOfBoundIndex = INDEX_SECOND_BOOKING;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getBookingList().size());

        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(outOfBoundIndex);

        assertCommandFailure(deleteBookingCommand, model, Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteBookingCommand deleteFirstCommand = new DeleteBookingCommand(INDEX_FIRST_BOOKING);
        DeleteBookingCommand deleteSecondCommand = new DeleteBookingCommand(INDEX_SECOND_BOOKING);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteBookingCommand deleteFirstCommandCopy = new DeleteBookingCommand(INDEX_FIRST_BOOKING);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different person -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(targetIndex);
        String expected = DeleteBookingCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteBookingCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no booking.
     */
    private void showNoBooking(Model model) {
        model.filterBookingList(p -> false);

        assertTrue(model.getModifiedBookingList().isEmpty());
    }
}
