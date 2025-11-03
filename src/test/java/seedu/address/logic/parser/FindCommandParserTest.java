package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_FINDBY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;

public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand("NAME", Arrays.asList("ALICE", "BOB"));
        assertParseSuccess(parser, "name alice bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "name \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_missingKeywordsAfterKey_throwsParseException() {
        // key only, no remainder
        assertParseFailure(parser, "name", FindCommand.MESSAGE_USAGE);

        // key with whitespace remainder but no actual keywords
        assertParseFailure(parser, "status    ", FindCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidFindByKey_throwsParseException() {
        // unsupported key should be rejected by ParserUtil.parseFindBy
        assertParseFailure(parser, "email alice bob", MESSAGE_INVALID_FINDBY);

        // random token as key
        assertParseFailure(parser, "foo bar", MESSAGE_INVALID_FINDBY);
    }

    @Test
    public void parse_statusArgs_returnsFindCommand() {
        // Valid status keywords should be uppercased by ParserUtil.parseStatusToString
        FindCommand expected = new FindCommand("STATUS", Arrays.asList("ACTIVE", "PROSPECT"));
        assertParseSuccess(parser, "status active prospect", expected);

        // Extra whitespace/newlines between tokens
        assertParseSuccess(parser, "  status   ACTIVE  \n   prospect \t", expected);
    }

    @Test
    public void parse_nameArgsMixedCase_returnsFindCommand() {
        // Name keywords are normalized to uppercase by parseNameToString
        FindCommand expected = new FindCommand("NAME", Arrays.asList("ALICE", "BOB", "CHARLIE"));
        assertParseSuccess(parser, "NaMe   Alice   bob   CHARLIE", expected);
    }
}
