package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKINGS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListBookingCommand extends Command {

    public static final String COMMAND_WORD = "listbooking";

    public static final String MESSAGE_SUCCESS = "Listed all bookings";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBookingList(PREDICATE_SHOW_ALL_BOOKINGS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
