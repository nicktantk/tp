package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBookings.ALICE_BOOKINGDESCRIPTOR;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingDescriptor;
import seedu.address.model.person.Person;
import seedu.address.testutil.BookingBuilder;
import seedu.address.testutil.BookingDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddBookingCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void constructor_nullBooking_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddBookingCommand(null, null));
    }

    @Test
    public void execute_bookingAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBookingAdded modelStub = new ModelStubAcceptingBookingAdded();
        Booking validBooking = new BookingBuilder().build();
        BookingDescriptor descriptor = new BookingDescriptorBuilder(validBooking).build();
        Index index = Index.fromZeroBased(0);

        CommandResult commandResult = new AddBookingCommand(index, descriptor).execute(modelStub);

        assertEquals(String.format(AddBookingCommand.MESSAGE_SUCCESS, Messages.format(validBooking)),
                commandResult.getFeedbackToUser());
        assertEquals(List.of(validBooking), modelStub.bookingsAdded);
    }

    @Test
    public void execute_duplicateBooking_throwsCommandException() {
        Booking validBooking = new BookingBuilder().build();
        BookingDescriptor descriptor = new BookingDescriptorBuilder(validBooking).build();
        Index index = Index.fromZeroBased(0);
        AddBookingCommand addBookingCommand = new AddBookingCommand(index, descriptor);
        ModelStub modelStub = new ModelStubWithBooking(validBooking);

        assertThrows(CommandException.class, AddBookingCommand.MESSAGE_DUPLICATE_BOOKING, (
        ) -> addBookingCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Booking validBooking = new BookingBuilder().build();
        BookingDescriptor descriptor = new BookingDescriptorBuilder(validBooking).build();
        AddBookingCommand addBookingCommand = new AddBookingCommand(outOfBoundIndex, descriptor);
        assertCommandFailure(addBookingCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Booking noon = new BookingBuilder().withDateTime("19/10/2025 1200").build();
        BookingDescriptor noonDescriptor = new BookingDescriptorBuilder(noon).build();
        Booking midnight = new BookingBuilder().withDateTime("19/10/2025 0000").build();
        BookingDescriptor midnightDescriptor = new BookingDescriptorBuilder(midnight).build();
        Index index = Index.fromZeroBased(0);

        AddBookingCommand addNoonCommand = new AddBookingCommand(index, noonDescriptor);
        AddBookingCommand addMidnightCommand = new AddBookingCommand(index, midnightDescriptor);

        // same object -> returns true
        assertEquals(addNoonCommand, addNoonCommand);

        // same values -> returns true
        AddBookingCommand addNoonCommandCopy = new AddBookingCommand(index, noonDescriptor);
        assertEquals(addNoonCommand, addNoonCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addNoonCommand);

        // null -> returns false
        assertNotEquals(null, addNoonCommand);

        // different booking -> returns false
        assertNotEquals(addNoonCommand, addMidnightCommand);
    }

    @Test
    public void toStringMethod() {
        AddBookingCommand addBookingCommand = new AddBookingCommand(INDEX_FIRST_PERSON, ALICE_BOOKINGDESCRIPTOR);
        String expected = AddBookingCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_PERSON
                + ", BookingDescriptor=" + ALICE_BOOKINGDESCRIPTOR + "}";
        assertEquals(expected, addBookingCommand.toString());
    }

    /**
     * A default model stub that have all the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person person, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBooking(Booking booking) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBooking(Booking target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBooking(Booking booking) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBooking(Booking target, Booking editedBooking) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Booking> getFilteredBookingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBookingList(Predicate<Booking> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedBookingList(Comparator<Booking> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedPersonList(Comparator<Person> comparator) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single booking.
     */
    private class ModelStubWithBooking extends ModelStub {
        private final Booking booking;
        private final ArrayList<Person> persons = new ArrayList<>();

        ModelStubWithBooking(Booking booking) {
            requireNonNull(booking);
            this.booking = booking;
            // Add a dummy person so index 0 is valid
            persons.add(new PersonBuilder().build());
        }

        @Override
        public boolean hasBooking(Booking booking) {
            requireNonNull(booking);
            return this.booking.isSameBooking(booking);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableArrayList(persons);
        }
    }


    /**
     * A Model stub that always accept the booking being added.
     */
    private class ModelStubAcceptingBookingAdded extends ModelStub {
        final ArrayList<Booking> bookingsAdded = new ArrayList<>();
        final ArrayList<Person> persons = new ArrayList<>();

        public ModelStubAcceptingBookingAdded() {
            // Add a dummy person so index 0 is valid
            persons.add(new PersonBuilder().build());
        }

        @Override
        public boolean hasBooking(Booking booking) {
            requireNonNull(booking);
            return bookingsAdded.stream().anyMatch(booking::isSameBooking);
        }

        @Override
        public void addBooking(Booking booking) {
            requireNonNull(booking);
            bookingsAdded.add(booking);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableArrayList(persons);
        }
    }


}
