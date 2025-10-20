package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedBooking.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBookings.ALICE_BOOKING;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.booking.DateTime;
import seedu.address.model.booking.Description;
import seedu.address.model.booking.PackageType;
import seedu.address.model.person.Name;

/**
 * Unit tests for {@code JsonAdaptedBooking}.
 */
public class JsonAdaptedBookingTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_DATETIME = "2025-14-99 25:00"; // impossible date
    private static final String INVALID_PACKAGETYPE = "ULTRAVIP"; // not in enum
    private static final String INVALID_TAG = "#invalidTag";

    private static final String VALID_NAME = ALICE_BOOKING.getName().toString();
    private static final String VALID_DESCRIPTION = ALICE_BOOKING.getDescription().toString();
    private static final String VALID_DATETIME = ALICE_BOOKING.getDateTime().toString();
    private static final String VALID_PACKAGETYPE = ALICE_BOOKING.getPackageType().name();
    private static final String VALID_ISDONE = String.valueOf(ALICE_BOOKING.isDone());
    private static final List<JsonAdaptedTag> VALID_TAGS = ALICE_BOOKING.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validBookingDetails_returnsBooking() throws Exception {
        JsonAdaptedBooking booking = new JsonAdaptedBooking(ALICE_BOOKING);
        assertEquals(ALICE_BOOKING, booking.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedBooking booking = new JsonAdaptedBooking(
                VALID_DESCRIPTION, INVALID_NAME, VALID_DATETIME, VALID_PACKAGETYPE, VALID_TAGS, VALID_ISDONE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedBooking booking = new JsonAdaptedBooking(
                VALID_DESCRIPTION, null, VALID_DATETIME, VALID_PACKAGETYPE, VALID_TAGS, VALID_ISDONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedBooking booking = new JsonAdaptedBooking(
                INVALID_DESCRIPTION, VALID_NAME, VALID_DATETIME, VALID_PACKAGETYPE, VALID_TAGS, VALID_ISDONE);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedBooking booking = new JsonAdaptedBooking(
                null, VALID_DESCRIPTION, VALID_DATETIME, VALID_PACKAGETYPE, VALID_TAGS, VALID_ISDONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        JsonAdaptedBooking booking = new JsonAdaptedBooking(
                VALID_DESCRIPTION, VALID_NAME, INVALID_DATETIME, VALID_PACKAGETYPE, VALID_TAGS, VALID_ISDONE);
        String expectedMessage = DateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_nullDateTime_throwsIllegalValueException() {
        JsonAdaptedBooking booking = new JsonAdaptedBooking(
                VALID_DESCRIPTION, VALID_NAME, null, VALID_PACKAGETYPE, VALID_TAGS, VALID_ISDONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_invalidPackageType_throwsIllegalValueException() {
        JsonAdaptedBooking booking = new JsonAdaptedBooking(
                VALID_DESCRIPTION, VALID_NAME, VALID_DATETIME, INVALID_PACKAGETYPE, VALID_TAGS, VALID_ISDONE);
        String expectedMessage = PackageType.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_nullPackageType_throwsIllegalValueException() {
        JsonAdaptedBooking booking = new JsonAdaptedBooking(
                VALID_DESCRIPTION, VALID_NAME, VALID_DATETIME, null, VALID_TAGS, VALID_ISDONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PackageType.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedBooking booking = new JsonAdaptedBooking(
                VALID_DESCRIPTION, VALID_NAME, VALID_DATETIME, VALID_PACKAGETYPE, invalidTags, VALID_ISDONE);
        assertThrows(IllegalValueException.class, booking::toModelType);
    }
}
