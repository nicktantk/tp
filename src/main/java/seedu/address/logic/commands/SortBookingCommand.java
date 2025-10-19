package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.DateTimeComparator;

/**
 * Sorts and lists all persons based on alphabetical order
 */
public class SortBookingCommand extends Command {
    public static final String COMMAND_WORD = "sortbooking";
    public static final String MESSAGE_SUCCESS = "Bookings sorted by date time.";
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Comparator<Booking> dateTimeComparator = new DateTimeComparator();
        model.updateSortedBookingList(dateTimeComparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
