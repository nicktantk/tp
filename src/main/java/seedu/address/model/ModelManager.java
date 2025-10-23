package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.booking.Booking;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Booking> filteredBookings;
    private final SortedList<Person> sortedFilteredPersons;
    private final SortedList<Booking> sortedFilteredBookings;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        sortedFilteredPersons = new SortedList<>(filteredPersons);
        filteredBookings = new FilteredList<>(this.addressBook.getBookingList());

        filteredPersons.setPredicate(PREDICATE_SHOW_ALL_PERSONS);
        filteredBookings.setPredicate(PREDICATE_SHOW_ALL_BOOKINGS);

        sortedFilteredBookings = new SortedList<>(filteredBookings);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        filterPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Booking List Accessors =============================================================

    @Override
    public boolean hasBooking(Booking booking) {
        requireNonNull(booking);
        return addressBook.hasBooking(booking);
    }

    @Override
    public void deleteBooking(Booking target) {
        addressBook.removeBooking(target);
    }

    @Override
    public void addBooking(Booking booking) {
        addressBook.addBooking(booking);
        filterBookingList(b -> true); // Show all bookings after add
    }

    @Override
    public void setBooking(Booking target, Booking editedBooking) {
        requireAllNonNull(target, editedBooking);
        addressBook.setBooking(target, editedBooking);
    }

    //=========== Sort/Filter Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getModifiedPersonList() {
        return sortedFilteredPersons;
    }

    @Override
    public void filterPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public Predicate<Person> getFilteredPersonsPredicate() {
        @SuppressWarnings("All predicates of filteredPersons are of type Predicate<Person>")
        Predicate<Person> predicate = (Predicate<Person>) filteredPersons.getPredicate();
        return predicate;
    }

    @Override
    public void sortPersonList(Comparator<Person> comparator) {
        sortedFilteredPersons.setComparator(comparator);
    }

    //=========== Sort/Filter Booking List Accessors =============================================================
    /**
     * Returns an unmodifiable view of the list of {@code Booking} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Booking> getModifiedBookingList() {
        return sortedFilteredBookings;
    }

    @Override
    public void filterBookingList(Predicate<Booking> predicate) {
        requireNonNull(predicate);
        filteredBookings.setPredicate(predicate);
    }
    @Override
    public void sortBookingList(Comparator<Booking> comparator) {
        sortedFilteredBookings.setComparator(comparator);
    }

    @Override
    public Predicate<Booking> getFilteredBookingsPredicate() {
        @SuppressWarnings("All predicates of filteredBookings are of type Predicate<Booking>")
        Predicate<Booking> predicate = (Predicate<Booking>) filteredBookings.getPredicate();
        return predicate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager otherModelManager)) {
            return false;
        }

        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
