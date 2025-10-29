package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input objects and returns a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    @Override
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);

        final String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Split into key and remainder
        final String[] firstAndRest = trimmedArgs.split("\\s+", 2);
        if (firstAndRest.length < 2 || firstAndRest[1].trim().isEmpty()) {
            throw new ParseException(FindCommand.MESSAGE_NO_FILTERS);
        }

        final String findBy = ParserUtil.parseFindBy(firstAndRest[0]).toUpperCase(); // may throw ParseException
        final String[] rawKeywords = firstAndRest[1].trim().split("\\s+");

        final List<String> keywords = new ArrayList<>(rawKeywords.length);
        if ("NAME".equals(findBy)) {
            for (String kw : rawKeywords) {
                keywords.add(ParserUtil.parseNameToString(kw)); // may throw ParseException
            }
        } else {
            for (String kw : rawKeywords) {
                keywords.add(ParserUtil.parseStatusToString(kw)); // use status parser for non-NAME
            }
        }

        return new FindCommand(findBy, keywords);
    }
}
