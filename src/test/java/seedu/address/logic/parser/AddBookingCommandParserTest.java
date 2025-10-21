package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.DESC_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PACKAGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PACKAGE_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.PACKAGE_DESC_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PREMIUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PACKAGETYPE_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PACKAGETYPE_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PREMIUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PACKAGETYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalBookings.ALICE_BOOKING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddBookingCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingDescriptor;
import seedu.address.model.booking.DateTime;
import seedu.address.model.booking.Description;
import seedu.address.model.booking.PackageType;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BookingDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddBookingCommandParserTest {
    private final AddBookingCommandParser parser = new AddBookingCommandParser();

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBookingCommand.MESSAGE_USAGE);

    @Test
    public void parse_allFieldsPresent_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_WEDDING + DATETIME_DESC_WEDDING + PACKAGE_DESC_WEDDING
                + TAG_DESC_PREMIUM;

        BookingDescriptor descriptor = new BookingDescriptorBuilder().withDescription(VALID_DESCRIPTION_WEDDING)
                .withDateTime(VALID_DATETIME_WEDDING).withPackageType(VALID_PACKAGETYPE_WEDDING)
                .withTags(VALID_TAG_PREMIUM).build();
        AddBookingCommand expectedCommand = new AddBookingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_WEDDING + DATETIME_DESC_BIRTHDAY + PACKAGE_DESC_WEDDING;

        BookingDescriptor descriptor = new BookingDescriptorBuilder().withDescription(VALID_DESCRIPTION_WEDDING)
                .withDateTime(VALID_DATETIME_BIRTHDAY).withPackageType(VALID_PACKAGETYPE_WEDDING).build();
        AddBookingCommand expectedCommand = new AddBookingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_WEDDING + DATETIME_DESC_WEDDING + PACKAGE_DESC_WEDDING
                + TAG_DESC_PREMIUM;

        // multiple descriptions
        assertParseFailure(parser, userInput + DESCRIPTION_DESC_WEDDING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // multiple datetimes
        assertParseFailure(parser, userInput + DATETIME_DESC_WEDDING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATETIME));

        // multiple package types
        assertParseFailure(parser, userInput + PACKAGE_DESC_WEDDING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PACKAGETYPE));


        // multiple fields repeated
        assertParseFailure(parser,
                userInput + DATETIME_DESC_WEDDING + DATETIME_DESC_WEDDING + DESCRIPTION_DESC_WEDDING + userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION, PREFIX_DATETIME, PREFIX_PACKAGETYPE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBookingCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, "1" + VALID_DESCRIPTION_WEDDING + DATETIME_DESC_WEDDING + PACKAGE_DESC_WEDDING
                        + TAG_DESC_PREMIUM,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, "1" + DESCRIPTION_DESC_WEDDING + VALID_DATETIME_WEDDING + PACKAGE_DESC_WEDDING
                        + TAG_DESC_PREMIUM,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, "1" + DESCRIPTION_DESC_WEDDING + DATETIME_DESC_WEDDING + VALID_PACKAGETYPE_WEDDING
                        + TAG_DESC_PREMIUM,
                expectedMessage);

        // missing index
        assertParseFailure(parser, DESCRIPTION_DESC_WEDDING + DATETIME_DESC_WEDDING + PACKAGE_DESC_WEDDING
                        + TAG_DESC_PREMIUM,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_DESCRIPTION_WEDDING + VALID_DATETIME_WEDDING + VALID_PACKAGETYPE_WEDDING,
                expectedMessage);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC + DATETIME_DESC_BIRTHDAY + PACKAGE_DESC_BIRTHDAY,
                Description.MESSAGE_CONSTRAINTS);

        // invalid datetime
        assertParseFailure(parser, "1" + DESCRIPTION_DESC_BIRTHDAY + INVALID_DATETIME_DESC + PACKAGE_DESC_BIRTHDAY,
                DateTime.MESSAGE_CONSTRAINTS);

        // invalid package type
        assertParseFailure(parser, "1" + DESCRIPTION_DESC_WEDDING + DATETIME_DESC_WEDDING + INVALID_PACKAGE_DESC,
                PackageType.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, "1" + DESCRIPTION_DESC_WEDDING + DATETIME_DESC_WEDDING + PACKAGE_DESC_BIRTHDAY
                + INVALID_TAG_DESC + VALID_TAG_PREMIUM, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC + DATETIME_DESC_WEDDING + INVALID_PACKAGE_DESC
                        + VALID_TAG_PREMIUM,
                Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        String validFields = DESCRIPTION_DESC_WEDDING + DATETIME_DESC_WEDDING + PACKAGE_DESC_WEDDING + TAG_DESC_PREMIUM;

        // negative index
        assertParseFailure(parser, "-5" + validFields, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + validFields, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }
}
