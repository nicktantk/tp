package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewBookingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewBookingCommand object
 */
@SuppressWarnings("checkstyle:Regexp")
public class ViewBookingCommandParser implements Parser<ViewBookingCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewBookingCommand
     * and returns a ViewBookingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewBookingCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewBookingCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewBookingCommand.MESSAGE_USAGE), pe);
        }
    }
}
