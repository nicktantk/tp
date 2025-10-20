package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.DateTimeComparator;
import seedu.address.model.booking.NowOrAfterPredicate;

/**
 * Sorts and lists all persons based on alphabetical order
 */
public class SortBookingCommand extends Command {
    public static final String COMMAND_WORD = "sortbooking";
    public static final String MESSAGE_SUCCESS = "Upcoming bookings sorted by date time.";
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Comparator<Booking> dateTimeComparator = new DateTimeComparator();
        Predicate<Booking> nowOrAfterPredicate = new NowOrAfterPredicate();
        model.filterBookingList(nowOrAfterPredicate);
        model.sortBookingList(dateTimeComparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
