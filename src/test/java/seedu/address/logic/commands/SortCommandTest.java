package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for SortBookingCommand.
 */
public class SortCommandTest {

    @Test
    public void execute_sortPersons_success() throws CommandException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SortCommand sortCommand = new SortCommand();

        CommandResult result = sortCommand.execute(model);

        assertEquals(SortCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }
}
