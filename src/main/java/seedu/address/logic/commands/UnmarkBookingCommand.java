package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;

/**
 * Marking the status of booking event in a given index as unfinish
 */
public class UnmarkBookingCommand extends Command {

    public static final String COMMAND_WORD = "unmarkbooking";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks a booking as 'Not Paid'.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Booking %1$s has been unmarked.";
    public static final String MESSAGE_DUPLICATE = "You have already unmarked this booking.";

    private final Index bookingindex;

    public UnmarkBookingCommand(Index bookingindex) {
        this.bookingindex = bookingindex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Booking> lastShownList = model.getModifiedBookingList();

        if (bookingindex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
        }

        Booking booking = lastShownList.get(bookingindex.getZeroBased());
        if (!booking.isPaid()) {
            throw new CommandException(MESSAGE_DUPLICATE);
        }
        Booking markedbooking = booking.unMarkBooking();
        model.setBooking(booking, markedbooking);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(markedbooking)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof UnmarkBookingCommand otherCommand)) {
            return false;
        }
        return bookingindex.equals(otherCommand.bookingindex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("bookingindex", bookingindex)
                .toString();
    }
}
