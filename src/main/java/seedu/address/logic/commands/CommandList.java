package seedu.address.logic.commands;

import java.util.List;

/**
 * Holds references to all available commands for HelpWindow or autocomplete.
 */
public class CommandList {

    public static final List<CommandInfo> ALL_COMMANDS = List.of(
            new CommandInfo(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE),
            new CommandInfo(AddBookingCommand.COMMAND_WORD, AddBookingCommand.MESSAGE_USAGE),
            new CommandInfo(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_USAGE),
            new CommandInfo(ListBookingCommand.COMMAND_WORD, ListBookingCommand.MESSAGE_USAGE),
            new CommandInfo(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE),
            new CommandInfo(DeleteBookingCommand.COMMAND_WORD, DeleteBookingCommand.MESSAGE_USAGE),
            new CommandInfo(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE),
            new CommandInfo(ViewBookingCommand.COMMAND_WORD, ViewBookingCommand.MESSAGE_USAGE),
            new CommandInfo(MarkBookingCommand.COMMAND_WORD, MarkBookingCommand.MESSAGE_USAGE),
            new CommandInfo(UnmarkBookingCommand.COMMAND_WORD, UnmarkBookingCommand.MESSAGE_USAGE),
            new CommandInfo(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE),
            new CommandInfo(EditBookingCommand.COMMAND_WORD, EditBookingCommand.MESSAGE_USAGE),
            new CommandInfo(SortCommand.COMMAND_WORD, SortCommand.MESSAGE_USAGE),
            new CommandInfo(SortBookingCommand.COMMAND_WORD, SortBookingCommand.MESSAGE_USAGE),
            new CommandInfo(ClearCommand.COMMAND_WORD, ClearCommand.MESSAGE_USAGE),
            new CommandInfo(HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE),
            new CommandInfo(ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_USAGE)
    );

    /** Lightweight container for command metadata. */
    public static record CommandInfo(String word, String usage) {
        @Override
        public String toString() {
            return String.format("%s : %s", word, usage);
        }
    }
}
