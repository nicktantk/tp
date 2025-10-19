package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalBookings.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.booking.BookingHasNamePredicate;
import seedu.address.model.person.MatchPersonPredicate;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewBookingCommand.
 */
public class ViewBookingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToView = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ViewBookingCommand viewBookingCommand = new ViewBookingCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ViewBookingCommand.MESSAGE_BOOKINGS_LISTED_FOR_PERSON_OVERVIEW,
                getExpectedBookingCount(model, personToView),
                personToView.getName().toString());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(new MatchPersonPredicate(personToView));
        expectedModel.updateFilteredBookingList(new BookingHasNamePredicate(personToView.getName()));

        assertCommandSuccess(viewBookingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ViewBookingCommand viewBookingCommand = new ViewBookingCommand(outOfBoundIndex);

        assertCommandFailure(viewBookingCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToView = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ViewBookingCommand viewBookingCommand = new ViewBookingCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ViewBookingCommand.MESSAGE_BOOKINGS_LISTED_FOR_PERSON_OVERVIEW,
                getExpectedBookingCount(model, personToView),
                personToView.getName().toString());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.updateFilteredPersonList(new MatchPersonPredicate(personToView));
        expectedModel.updateFilteredBookingList(new BookingHasNamePredicate(personToView.getName()));

        assertCommandSuccess(viewBookingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ViewBookingCommand viewBookingCommand = new ViewBookingCommand(outOfBoundIndex);

        assertCommandFailure(viewBookingCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewBookingCommand viewFirstCommand = new ViewBookingCommand(INDEX_FIRST_PERSON);
        ViewBookingCommand viewSecondCommand = new ViewBookingCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewBookingCommand viewFirstCommandCopy = new ViewBookingCommand(INDEX_FIRST_PERSON);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ViewBookingCommand viewBookingCommand = new ViewBookingCommand(targetIndex);
        String expected = ViewBookingCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, viewBookingCommand.toString());
    }

    /**
     * Helper method to count the expected number of bookings for a given person.
     */
    private int getExpectedBookingCount(Model model, Person person) {
        Model tempModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        tempModel.updateFilteredBookingList(new BookingHasNamePredicate(person.getName()));
        return tempModel.getFilteredBookingList().size();
    }
}
