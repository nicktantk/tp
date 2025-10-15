package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.BookingHasNamePredicate;
import seedu.address.model.person.MatchPersonPredicate;
import seedu.address.model.person.Person;

/**
 * Lists all booking for a specific client identified using it's displayed index from the address book.
 */
public class ViewBookingCommand extends Command {

    public static final String COMMAND_WORD = "viewbooking";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all the bookings of the client identified by the index number used in"
            + " the displayed person list.\n"
            + "Parameters: INDEX (must be a postive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_BOOKINGS_LISTED_FOR_PERSON_OVERVIEW = "%1$d bookings listed for client %2$s.";

    private final Index targetIndex;

    public ViewBookingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToFilter = lastShownList.get(targetIndex.getZeroBased());
        model.updateFilteredPersonList(new MatchPersonPredicate(personToFilter));
        model.updateFilteredBookingList(new BookingHasNamePredicate(personToFilter.getName()));

        return new CommandResult(
                String.format(MESSAGE_BOOKINGS_LISTED_FOR_PERSON_OVERVIEW,
                        model.getFilteredBookingList().size(),
                        personToFilter.getName().toString()));
    }
}
