package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.AddBookingCommand.MESSAGE_DUPLICATE_BOOKING;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PREMIUM;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookingAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOKING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOKING;
import static seedu.address.testutil.TypicalBookings.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.EditBookingDescriptor;
import seedu.address.testutil.BookingBuilder;
import seedu.address.testutil.EditBookingDescriptorBuilder;

public class EditBookingCommandTest {
   
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Booking bookingToEdit = model.getFilteredBookingList().get(0);
        Booking editedBooking = new BookingBuilder().withName(bookingToEdit.getName().toString()).build();
        EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder(editedBooking).build();
        EditBookingCommand editBookingCommand = new EditBookingCommand(INDEX_FIRST_BOOKING, descriptor);

        String expectedMessage = String.format(EditBookingCommand.MESSAGE_EDIT_BOOKING_SUCCESS,
                Messages.format(editedBooking));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setBooking(bookingToEdit, editedBooking);

        assertCommandSuccess(editBookingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastBooking = Index.fromOneBased(model.getFilteredBookingList().size());
        Booking lastBooking = model.getFilteredBookingList().get(indexLastBooking.getZeroBased());

        BookingBuilder bookingInList = new BookingBuilder(lastBooking);
        Booking editedBooking = bookingInList.withDescription(VALID_DESCRIPTION_WEDDING)
                .withDateTime(VALID_DATETIME_WEDDING).withTags(VALID_TAG_PREMIUM).build();

        EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder().withDescription(VALID_DESCRIPTION_WEDDING)
                .withDateTime(VALID_DATETIME_WEDDING).withTags(VALID_TAG_PREMIUM).build();
        EditBookingCommand editBookingCommand = new EditBookingCommand(indexLastBooking, descriptor);

        String expectedMessage = String.format(EditBookingCommand.MESSAGE_EDIT_BOOKING_SUCCESS,
                Messages.format(editedBooking));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setBooking(lastBooking, editedBooking);

        assertCommandSuccess(editBookingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditBookingCommand editBookingCommand = new EditBookingCommand(INDEX_FIRST_BOOKING,
                new EditBookingDescriptor());
        Booking editedBooking = model.getFilteredBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());

        String expectedMessage = String.format(EditBookingCommand.MESSAGE_EDIT_BOOKING_SUCCESS,
                Messages.format(editedBooking));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editBookingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showBookingAtIndex(model, INDEX_FIRST_BOOKING);

        Booking bookingInFilteredList = model.getFilteredBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());
        Booking editedBooking = new BookingBuilder(bookingInFilteredList).withDescription(VALID_DESCRIPTION_BIRTHDAY)
                .build();
        EditBookingCommand editBookingCommand = new EditBookingCommand(INDEX_FIRST_BOOKING,
                new EditBookingDescriptorBuilder().withDescription(VALID_DESCRIPTION_BIRTHDAY).build());

        String expectedMessage = String.format(EditBookingCommand.MESSAGE_EDIT_BOOKING_SUCCESS,
                Messages.format(editedBooking));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setBooking(model.getFilteredBookingList().get(0), editedBooking);

        assertCommandSuccess(editBookingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateBookingUnfilteredList_failure() {
        Booking firstBooking = model.getFilteredBookingList().get(INDEX_FIRST_BOOKING.getZeroBased());
        EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder(firstBooking).build();
        EditBookingCommand editBookingCommand = new EditBookingCommand(INDEX_SECOND_BOOKING, descriptor);

        assertCommandFailure(editBookingCommand, model, MESSAGE_DUPLICATE_BOOKING);
    }

    @Test
    public void execute_duplicateBookingFilteredList_failure() {
        showBookingAtIndex(model, INDEX_FIRST_BOOKING);

        // edit booking in filtered list into a duplicate in address book
        Booking bookingInList = model.getAddressBook().getBookingList().get(INDEX_SECOND_BOOKING.getZeroBased());
        EditBookingCommand editBookingCommand = new EditBookingCommand(INDEX_FIRST_BOOKING,
                new EditBookingDescriptorBuilder(bookingInList).build());

        assertCommandFailure(editBookingCommand, model, MESSAGE_DUPLICATE_BOOKING);
    }

    @Test
    public void execute_invalidBookingIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookingList().size() + 1);
        EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder().withDescription(VALID_DESCRIPTION_WEDDING)
                .build();
        EditBookingCommand editBookingCommand = new EditBookingCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editBookingCommand, model, Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidBookingIndexFilteredList_failure() {
        showBookingAtIndex(model, INDEX_FIRST_BOOKING);
        Index outOfBoundIndex = INDEX_SECOND_BOOKING;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getBookingList().size());

        EditBookingCommand editBookingCommand = new EditBookingCommand(outOfBoundIndex,
                new EditBookingDescriptorBuilder().withDescription(VALID_DESCRIPTION_BIRTHDAY).build());

        assertCommandFailure(editBookingCommand, model, Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EditBookingDescriptor originalDescriptor = new EditBookingDescriptor(DESC_WEDDING);
        final EditBookingCommand standardCommand = new EditBookingCommand(INDEX_FIRST_BOOKING, originalDescriptor);

        // same values -> returns true
        EditBookingDescriptor copyDescriptor = new EditBookingDescriptor(DESC_WEDDING);
        EditBookingCommand commandWithSameValues = new EditBookingCommand(INDEX_FIRST_BOOKING, copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new EditBookingCommand(INDEX_SECOND_BOOKING, originalDescriptor));

        // different descriptor -> returns false
        assertNotEquals(standardCommand, new EditBookingCommand(INDEX_FIRST_BOOKING,
                new EditBookingDescriptor(DESC_BIRTHDAY)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditBookingDescriptor editBookingDescriptor = new EditBookingDescriptor();
        EditBookingCommand editBookingCommand = new EditBookingCommand(index, editBookingDescriptor);
        String expected = EditBookingCommand.class.getCanonicalName() + "{index=" + index + ", editBookingDescriptor="
                + editBookingDescriptor + "}";
        assertEquals(expected, editBookingCommand.toString());
    }

}
