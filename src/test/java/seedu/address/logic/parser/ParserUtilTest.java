package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.booking.DateTime;
import seedu.address.model.booking.PackageType;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone(null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress(null));
    }

    @Test
    public void parseAddress_validValueBlank_returnsNoAddress() throws Exception {
        Address expectedAddress = new Address("No address");
        assertEquals(expectedAddress, ParserUtil.parseAddress(""));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail(null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    // ===== Additional tests for ParserUtil new methods =====

    @Test
    public void parseNameToString_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNameToString(null));
    }

    @Test
    public void parseNameToString_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNameToString(INVALID_NAME));
    }

    @Test
    public void parseNameToString_validValueUppercased_returnsString() throws Exception {
        // parseNameToString uppercases and validates via Name
        String mixedCase = "Alice Bob";
        assertEquals("ALICE BOB", ParserUtil.parseNameToString(mixedCase));
    }

    @Test
    public void parseNameToString_validValueWithWhitespace_returnsTrimmedUppercased() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        assertEquals(VALID_NAME.toUpperCase(), ParserUtil.parseNameToString(nameWithWhitespace));
    }

    @Test
    public void parseStatus_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStatus(null));
    }

    @Test
    public void parseStatus_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStatus("unknown"));
        assertThrows(ParseException.class, () -> ParserUtil.parseStatus("activee"));
        assertThrows(ParseException.class, () -> ParserUtil.parseStatus("")); // empty after trim
    }

    @Test
    public void parseStatus_validValue_isCaseInsensitiveAndTrims() throws Exception {
        // Assuming Status enum contains: PROSPECT, POTENTIAL, ACTIVE, INACTIVE, RETURNING
        assertEquals(Status.ACTIVE, ParserUtil.parseStatus("active"));
        assertEquals(Status.ACTIVE, ParserUtil.parseStatus("  ACTIVE  "));
        assertEquals(Status.RETURNING, ParserUtil.parseStatus("ReTuRnInG"));
    }

    @Test
    public void parseStatusToString_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStatusToString(null));
    }

    @Test
    public void parseStatusToString_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStatusToString("prospects")); // not valid
    }

    @Test
    public void parseStatusToString_validValue_returnsCanonicalUppercase() throws Exception {
        assertEquals("ACTIVE", ParserUtil.parseStatusToString("active"));
        assertEquals("PROSPECT", ParserUtil.parseStatusToString("  prospect "));
    }

    @Test
    public void parseFindBy_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFindBy(null));
    }

    @Test
    public void parseFindBy_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseFindBy(""));
        assertThrows(ParseException.class, () -> ParserUtil.parseFindBy(" email "));
        assertThrows(ParseException.class, () -> ParserUtil.parseFindBy("names")); // not exactly "name"
    }

    @Test
    public void parseFindBy_validValues_returnCanonicalUppercase() throws Exception {
        assertEquals("NAME", ParserUtil.parseFindBy("name"));
        assertEquals("NAME", ParserUtil.parseFindBy("NaMe"));
        assertEquals("STATUS", ParserUtil.parseFindBy("status"));
    }

    // ===== Additional tests for Description =====

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription(null));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        // Adjust invalid sample to something your Description rejects; often empty/whitespace-only
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription("")); // empty
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription("   ")); // whitespace only
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() throws Exception {
        String value = "Wedding photography, full day";
        assertEquals(new seedu.address.model.booking.Description(value),
                ParserUtil.parseDescription(value));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() throws Exception {
        String raw = WHITESPACE + "Promo shoot at studio" + WHITESPACE;
        assertEquals(new seedu.address.model.booking.Description("Promo shoot at studio"),
                ParserUtil.parseDescription(raw));
    }

    // ===== Additional tests for PackageType =====

    @Test
    public void parsePackageType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePackageType(null));
    }

    @Test
    public void parsePackageType_invalidValue_throwsParseException() {
        // Not in enum; adjust to something definitely invalid for your enum
        assertThrows(ParseException.class, () -> ParserUtil.parsePackageType("gold-plus"));
        assertThrows(ParseException.class, () -> ParserUtil.parsePackageType("")); // empty
    }

    @Test
    public void parsePackageType_validValue_isCaseInsensitiveAndTrims() throws Exception {
        // Replace BASIC/DELUXE with actual enum constants you have
        assertEquals(PackageType.PORTRAIT,
                ParserUtil.parsePackageType("portrait"));
        assertEquals(PackageType.BABY,
                ParserUtil.parsePackageType("  baby "));
        // Add another example if you have multiple values:
        // assertEquals(PackageType.DELUXE, ParserUtil.parsePackageType("Deluxe"));
    }

    // ===== Additional tests for DateTime =====

    @Test
    public void parseDateTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDateTime(null));
    }

    @Test
    public void parseDateTime_invalidValue_throwsParseException() {
        // Provide a few invalid formats your DateTime.isValidDateTime rejects
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime("2025/01/01 10:00"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime("not-a-date"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime("")); // empty
    }

    @Test
    public void parseDateTime_validValueWithoutWhitespace_returnsDateTime() throws Exception {
        String good = "01/01/2025 1000";
        assertEquals(new DateTime(good),
                ParserUtil.parseDateTime(good));
    }

    @Test
    public void parseDateTime_validValueWithWhitespace_returnsTrimmedDateTime() throws Exception {
        String raw = WHITESPACE + "01/01/2025 1000" + WHITESPACE;
        assertEquals(new DateTime("01/01/2025 1000"),
                ParserUtil.parseDateTime(raw));
    }
}
