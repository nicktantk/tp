package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PACKAGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PACKAGE_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.PACKAGE_DESC_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_OUTDOOR;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PREMIUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PACKAGETYPE_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PACKAGETYPE_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_OUTDOOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PREMIUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PACKAGETYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOKING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOKING;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_BOOKING;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditBookingCommand;
import seedu.address.model.booking.DateTime;
import seedu.address.model.booking.Description;
import seedu.address.model.booking.EditBookingDescriptor;
import seedu.address.model.booking.PackageType;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditBookingDescriptorBuilder;

public class EditBookingCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBookingCommand.MESSAGE_USAGE);

    private final EditBookingCommandParser parser = new EditBookingCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DESCRIPTION_BIRTHDAY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditBookingCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DESCRIPTION_DESC_WEDDING, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DESCRIPTION_DESC_WEDDING, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_DATETIME_DESC, DateTime.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_PACKAGE_DESC, PackageType.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid dateTime followed by valid package
        assertParseFailure(parser, "1" + INVALID_DATETIME_DESC + PACKAGE_DESC_BIRTHDAY,
                DateTime.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_PREMIUM + TAG_DESC_OUTDOOR + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_PREMIUM + TAG_EMPTY + TAG_DESC_OUTDOOR,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_PREMIUM + TAG_DESC_OUTDOOR,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC + INVALID_DATETIME_DESC
                        + VALID_PACKAGETYPE_BIRTHDAY + VALID_TAG_FRIEND, Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_BOOKING;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_WEDDING + TAG_DESC_PREMIUM
                + DATETIME_DESC_WEDDING + PACKAGE_DESC_WEDDING + TAG_DESC_OUTDOOR;

        EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder().withDescription(VALID_DESCRIPTION_WEDDING)
                .withDateTime(VALID_DATETIME_WEDDING).withPackageType(VALID_PACKAGETYPE_WEDDING)
                .withTags(VALID_TAG_PREMIUM, VALID_TAG_OUTDOOR).build();
        EditBookingCommand expectedCommand = new EditBookingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_BOOKING;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_WEDDING + TAG_DESC_PREMIUM;

        EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder().withDescription(VALID_DESCRIPTION_WEDDING)
                .withTags(VALID_TAG_PREMIUM).build();
        EditBookingCommand expectedCommand = new EditBookingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // description
        Index targetIndex = INDEX_THIRD_BOOKING;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_WEDDING;
        EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder().withDescription(VALID_DESCRIPTION_WEDDING)
                .build();
        EditBookingCommand expectedCommand = new EditBookingCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // datetime
        userInput = targetIndex.getOneBased() + DATETIME_DESC_BIRTHDAY;
        descriptor = new EditBookingDescriptorBuilder().withDateTime(VALID_DATETIME_BIRTHDAY).build();
        expectedCommand = new EditBookingCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // package type
        userInput = targetIndex.getOneBased() + PACKAGE_DESC_BIRTHDAY;
        descriptor = new EditBookingDescriptorBuilder().withPackageType(VALID_PACKAGETYPE_BIRTHDAY).build();
        expectedCommand = new EditBookingCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_OUTDOOR;
        descriptor = new EditBookingDescriptorBuilder().withTags(VALID_TAG_OUTDOOR).build();
        expectedCommand = new EditBookingCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddBookingCommandParserTest#parse_repeatedNonTagValue_failure()

        // invalid followed by valid
        Index targetIndex = INDEX_FIRST_BOOKING;
        String userInput = targetIndex.getOneBased() + INVALID_DESCRIPTION_DESC + DESCRIPTION_DESC_WEDDING;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // valid followed by invalid
        userInput = targetIndex.getOneBased() + DATETIME_DESC_WEDDING + INVALID_DATETIME_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATETIME));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_WEDDING + DATETIME_DESC_WEDDING + PACKAGE_DESC_BIRTHDAY
                + TAG_DESC_PREMIUM + DESCRIPTION_DESC_WEDDING + DATETIME_DESC_WEDDING + PACKAGE_DESC_BIRTHDAY
                + TAG_DESC_PREMIUM + DESCRIPTION_DESC_BIRTHDAY + DATETIME_DESC_BIRTHDAY + PACKAGE_DESC_BIRTHDAY
                + TAG_DESC_OUTDOOR;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION, PREFIX_DATETIME, PREFIX_PACKAGETYPE));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_DESCRIPTION_DESC + INVALID_DATETIME_DESC
                + INVALID_PACKAGE_DESC + INVALID_DESCRIPTION_DESC + INVALID_DATETIME_DESC + INVALID_PACKAGE_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION, PREFIX_DATETIME, PREFIX_PACKAGETYPE));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_BOOKING;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder().withTags().build();
        EditBookingCommand expectedCommand = new EditBookingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
