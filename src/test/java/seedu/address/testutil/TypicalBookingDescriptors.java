package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PACKAGETYPE_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PACKAGETYPE_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_OUTDOOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PREMIUM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.booking.BookingDescriptor;

/**
 * A utility class containing a list of {@code BookingDescriptor} objects to be used in tests.
 */
public class TypicalBookingDescriptors {

    public static final BookingDescriptor ALICE_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("Alice's birthday party")
            .withDateTime("15/12/2024 1400")
            .withPackageType("birthday")
            .withTags("party").build();

    public static final BookingDescriptor BENSON_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("Benson's corporate event")
            .withDateTime("20/11/2024 1800")
            .withPackageType("corporate")
            .withTags("product").build();

    public static final BookingDescriptor CARL_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("Carl's wedding reception")
            .withDateTime("10/01/2025 1900")
            .withPackageType("wedding")
            .withTags("hotel", "fountain").build();

    public static final BookingDescriptor DANIEL_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("Daniel's anniversary dinner")
            .withDateTime("25/12/2024 2000")
            .withPackageType("anniversary")
            .withTags("candlelight").build();

    public static final BookingDescriptor ELLE_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("Elle's graduation party")
            .withDateTime("14/02/2025 1500")
            .withPackageType("graduation")
            .withTags("school").build();

    public static final BookingDescriptor FIONA_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("Fiona's baby shower")
            .withDateTime("08/03/2025 1300")
            .withPackageType("baby")
            .withTags("Andrew").build();

    public static final BookingDescriptor GEORGE_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("George's retirement party")
            .withDateTime("05/04/2025 1300")
            .withPackageType("family")
            .withTags("largegroup").build();

    // Manually added
    public static final BookingDescriptor HOON_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("Hoon's networking event")
            .withDateTime("12/05/2025 1700")
            .withPackageType("corporate").build();

    public static final BookingDescriptor IDA_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("Ida's housewarming")
            .withDateTime("20/06/2025 1600")
            .withPackageType("other").build();

    // Manually added - BookingDescriptor's details found in {@code CommandTestUtil}
    public static final BookingDescriptor AMY_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription(VALID_DESCRIPTION_WEDDING)
            .withDateTime(VALID_DATETIME_WEDDING)
            .withPackageType(VALID_PACKAGETYPE_WEDDING)
            .withTags(VALID_TAG_PREMIUM).build();

    public static final BookingDescriptor BOB_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription(VALID_DESCRIPTION_BIRTHDAY)
            .withDateTime(VALID_DATETIME_BIRTHDAY)
            .withPackageType(VALID_PACKAGETYPE_BIRTHDAY)
            .withTags(VALID_TAG_OUTDOOR, VALID_TAG_PREMIUM).build();

    public static final String KEYWORD_MATCHING_WEDDING = "wedding"; // A keyword that matches WEDDING

    private TypicalBookingDescriptors() {
    } // prevents instantiation

    public static List<BookingDescriptor> getTypicalBookingDescriptors() {
        return new ArrayList<>(Arrays.asList(ALICE_BOOKINGDESCRIPTOR, BENSON_BOOKINGDESCRIPTOR,
                CARL_BOOKINGDESCRIPTOR, DANIEL_BOOKINGDESCRIPTOR, ELLE_BOOKINGDESCRIPTOR,
                FIONA_BOOKINGDESCRIPTOR, GEORGE_BOOKINGDESCRIPTOR));
    }
}
