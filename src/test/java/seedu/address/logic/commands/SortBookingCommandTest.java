package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalBookings.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for SortBookingCommand.
 */
public class SortBookingCommandTest {

    @Test
    public void execute_sortBookings_success() throws CommandException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SortBookingCommand sortBookingCommand = new SortBookingCommand();

        CommandResult result = sortBookingCommand.execute(model);

        assertEquals(SortBookingCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }
}
