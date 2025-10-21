package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PACKAGETYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingDescriptor;
import seedu.address.model.person.Person;

/**
 * Adds a booking to the address book.
 */
public class AddBookingCommand extends Command {

    public static final String COMMAND_WORD = "addbooking";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a booking to InSight. \n"
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
    public static final String MESSAGE_DUPLICATE_BOOKING = "There is already a booking at this time slot.";

    private final Index index;
    private final BookingDescriptor bookingDescriptor;

    /**
     * Creates an AddBookingCommand to add the specified {@code Booking}
     */
    public AddBookingCommand(Index index, BookingDescriptor bookingDescriptor) {
        requireNonNull(index);
        requireNonNull(bookingDescriptor);

        this.index = index;
        this.bookingDescriptor = new BookingDescriptor(bookingDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person bookingPerson = lastShownList.get(index.getZeroBased());
        Booking toAdd = new Booking(bookingPerson.getName(), bookingDescriptor, false);

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
                && bookingDescriptor.equals(otherAddBookingCommand.bookingDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("BookingDescriptor", bookingDescriptor)
                .toString();
    }
}
