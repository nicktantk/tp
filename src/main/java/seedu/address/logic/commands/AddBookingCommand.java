package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PACKAGETYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;

/**
 * Adds a person to the address book.
 */
public class AddBookingCommand extends Command {

    public static final String COMMAND_WORD = "addBooking";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a booking to the address book. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATETIME + "dd/mm/yyyy HHmm "
            + PREFIX_PACKAGETYPE + "PACKAGE TYPE "
            + "[" + PREFIX_TAG + "TAG]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Wedding Shoot "
            + PREFIX_NAME + "John Doe "
            + PREFIX_DATETIME + "14/10/2025 1200 "
            + PREFIX_PACKAGETYPE + "PORTRAIT "
            + PREFIX_TAG + "outdoor "
            + PREFIX_TAG + "summer";

    public static final String MESSAGE_SUCCESS = "New booking added: %1$s";
    public static final String MESSAGE_DUPLICATE_BOOKING = "This booking already exists in the address book";

    private final Booking toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddBookingCommand(Booking booking) {
        requireNonNull(booking);
        toAdd = booking;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

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

        return toAdd.equals(otherAddBookingCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
