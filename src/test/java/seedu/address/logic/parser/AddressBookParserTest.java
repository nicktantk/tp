package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOKING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddBookingCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteBookingCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditBookingCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListBookingCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkBookingCommand;
import seedu.address.logic.commands.SortBookingCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UnmarkBookingCommand;
import seedu.address.logic.commands.ViewBookingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingDescriptor;
import seedu.address.model.booking.EditBookingDescriptor;
import seedu.address.model.person.Person;
import seedu.address.testutil.BookingBuilder;
import seedu.address.testutil.BookingDescriptorBuilder;
import seedu.address.testutil.BookingUtil;
import seedu.address.testutil.EditBookingDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addBooking() throws Exception {
        Booking booking = new BookingBuilder().build();
        BookingDescriptor descriptor = new BookingDescriptorBuilder(booking).build();
        AddBookingCommand command = (AddBookingCommand) parser.parseCommand(AddBookingCommand.COMMAND_WORD
                + " " + INDEX_FIRST_PERSON.getOneBased() + " " + BookingUtil.getBookingDetails(booking));
        assertEquals(new AddBookingCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteBooking() throws Exception {
        DeleteBookingCommand command = (DeleteBookingCommand) parser.parseCommand(
                DeleteBookingCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOKING.getOneBased());
        assertEquals(new DeleteBookingCommand(INDEX_FIRST_BOOKING), command);
    }

    @Test
    public void parseCommand_viewBooking() throws Exception {
        ViewBookingCommand command = (ViewBookingCommand) parser.parseCommand(
                ViewBookingCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new ViewBookingCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_markBooking() throws Exception {
        MarkBookingCommand command = (MarkBookingCommand) parser.parseCommand(
                MarkBookingCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOKING.getOneBased());
        assertEquals(new MarkBookingCommand(INDEX_FIRST_BOOKING), command);
    }

    @Test
    public void parseCommand_unmarkBooking() throws Exception {
        UnmarkBookingCommand command = (UnmarkBookingCommand) parser.parseCommand(
                UnmarkBookingCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOKING.getOneBased());
        assertEquals(new UnmarkBookingCommand(INDEX_FIRST_BOOKING), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_editBooking() throws Exception {
        Booking booking = new BookingBuilder().build();
        EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder(booking).build();
        EditBookingCommand command = (EditBookingCommand) parser.parseCommand(EditBookingCommand.COMMAND_WORD
                + " " + INDEX_FIRST_BOOKING.getOneBased() + " "
                + BookingUtil.getEditBookingDescriptorDetails(descriptor));
        assertEquals(new EditBookingCommand(INDEX_FIRST_BOOKING, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> nameKeywords = Arrays.asList("FOO", "BAR", "BAZ");
        List<String> statusKeywords = Arrays.asList("PROSPECT", "POTENTIAL", "ACTIVE");
        FindCommand findByNameCommand = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " name " + nameKeywords.stream().collect(Collectors.joining(" "))
        );
        FindCommand findByStatusCommand = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " status " + statusKeywords.stream().collect(Collectors.joining(" "))
        );
        assertEquals(new FindCommand("NAME", nameKeywords), findByNameCommand);
        assertEquals(new FindCommand("STATUS", statusKeywords), findByStatusCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listBooking() throws Exception {
        assertTrue(parser.parseCommand(ListBookingCommand.COMMAND_WORD) instanceof ListBookingCommand);
        assertTrue(parser.parseCommand(ListBookingCommand.COMMAND_WORD + " 3") instanceof ListBookingCommand);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " 3") instanceof SortCommand);
    }

    @Test
    public void parseCommand_sortBooking() throws Exception {
        assertTrue(parser.parseCommand(SortBookingCommand.COMMAND_WORD) instanceof SortBookingCommand);
        assertTrue(parser.parseCommand(SortBookingCommand.COMMAND_WORD + " 3") instanceof SortBookingCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
