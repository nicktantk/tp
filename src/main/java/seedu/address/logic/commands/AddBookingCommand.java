package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PACKAGETYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.DateTime;
import seedu.address.model.booking.Description;
import seedu.address.model.booking.PackageType;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Adds a person to the address book.
 */
public class AddBookingCommand extends Command {

    public static final String COMMAND_WORD = "addbooking";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a booking to the address book. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_DATETIME + "dd/mm/yyyy HHmm "
            + PREFIX_PACKAGETYPE + "PACKAGE TYPE "
            + "[" + PREFIX_TAG + "TAG]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "Wedding Shoot "
            + PREFIX_DATETIME + "14/10/2025 1200 "
            + PREFIX_PACKAGETYPE + "PORTRAIT "
            + PREFIX_TAG + "outdoor "
            + PREFIX_TAG + "summer";

    public static final String MESSAGE_SUCCESS = "New booking added: %1$s";
    public static final String MESSAGE_DUPLICATE_BOOKING = "This booking already exists in the address book";

    private final Index index;
    private final AddBookingDescriptor addBookingDescriptor;

    /**
     * Creates an AddBookingCommand to add the specified {@code Booking}
     */
    public AddBookingCommand(Index index, AddBookingDescriptor addBookingDescriptor) {
        requireNonNull(index);
        requireNonNull(addBookingDescriptor);

        this.index = index;
        this.addBookingDescriptor = new AddBookingCommand.AddBookingDescriptor(addBookingDescriptor);
    }

    private static Booking createBookingToAdd(Person bookingPerson, AddBookingDescriptor addBookingDescriptor) {
        assert addBookingDescriptor != null;

        Description description = addBookingDescriptor.getDescription();
        Name name = bookingPerson.getName();
        DateTime dateTime = addBookingDescriptor.getDateTime();
        PackageType packageType = addBookingDescriptor.getPackageType();
        Set<Tag> tags = addBookingDescriptor.getTags();

        return new Booking(description, name, dateTime, packageType, tags, false);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person bookingPerson = lastShownList.get(index.getZeroBased());
        Booking toAdd = createBookingToAdd(bookingPerson, addBookingDescriptor);

        if (model.hasBooking(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOKING);
        }

        model.addBooking(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddBookingCommand otherAddBookingCommand)) {
            return false;
        }

        return index.equals(otherAddBookingCommand.index)
                && addBookingDescriptor.equals(otherAddBookingCommand.addBookingDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("addBookingDescriptor", addBookingDescriptor)
                .toString();
    }


    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class AddBookingDescriptor {
        private Description description;
        private DateTime dateTime;
        private PackageType packageType;
        private Set<Tag> tags;

        public AddBookingDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public AddBookingDescriptor(AddBookingCommand.AddBookingDescriptor toCopy) {
            setDescription(toCopy.description);
            setDateTime(toCopy.dateTime);
            setPackageType(toCopy.packageType);
            setTags(toCopy.tags);
        }

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public DateTime getDateTime() {
            return dateTime;
        }

        public void setDateTime(DateTime dateTime) {
            this.dateTime = dateTime;
        }

        public PackageType getPackageType() {
            return packageType;
        }

        public void setPackageType(PackageType packageType) {
            this.packageType = packageType;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Set<Tag> getTags() {
            return (tags != null) ? Collections.unmodifiableSet(tags) : Collections.emptySet();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AddBookingCommand.AddBookingDescriptor otherAddBookingDescriptor)) {
                return false;
            }

            return Objects.equals(description, otherAddBookingDescriptor.description)
                    && Objects.equals(dateTime, otherAddBookingDescriptor.dateTime)
                    && Objects.equals(packageType, otherAddBookingDescriptor.packageType)
                    && Objects.equals(tags, otherAddBookingDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("description", description)
                    .add("dateTime", dateTime)
                    .add("packageType", packageType)
                    .add("tags", tags)
                    .toString();
        }
    }
}
