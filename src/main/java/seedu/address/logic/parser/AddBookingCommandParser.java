package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PACKAGETYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddBookingCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Description;
import seedu.address.model.booking.PackageType;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddBookingCommandParser implements Parser<AddBookingCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBookingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_NAME, PREFIX_DATETIME, PREFIX_PACKAGETYPE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_NAME, PREFIX_DATETIME, PREFIX_PACKAGETYPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBookingCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DESCRIPTION, PREFIX_NAME, PREFIX_DATETIME, PREFIX_PACKAGETYPE);
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        LocalDateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
        PackageType packageType = ParserUtil.parsePackageType(argMultimap.getValue(PREFIX_PACKAGETYPE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Booking booking = new Booking(description, name, dateTime, packageType, tagList, false);

        return new AddBookingCommand(booking);
    }

}
