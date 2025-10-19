package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingDescriptor;
import seedu.address.model.person.Person;

/**
 * A utility class containing lists of {@code BookingDescriptor} and {@code Booking} objects to be used in tests.
 */
public class TypicalBookings {

    // BookingDescriptors (without client names)
    public static final BookingDescriptor ALICE_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("Alice birthday")
            .withDateTime("15/12/2024 1400")
            .withPackageType("BIRTHDAY")
            .withTags("party").build();

    public static final BookingDescriptor BENSON_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("Benson corporate")
            .withDateTime("20/11/2024 1800")
            .withPackageType("CORPORATE")
            .withTags("product").build();

    public static final BookingDescriptor CARL_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("Carl wedding")
            .withDateTime("10/01/2025 1900")
            .withPackageType("WEDDING")
            .withTags("hotel", "fountain").build();

    public static final BookingDescriptor DANIEL_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("Daniel anniversary")
            .withDateTime("25/12/2024 2000")
            .withPackageType("ANNIVERSARY")
            .withTags("candlelight").build();

    public static final BookingDescriptor ELLE_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("Elle graduation")
            .withDateTime("14/02/2025 1500")
            .withPackageType("GRADUATION")
            .withTags("school").build();

    public static final BookingDescriptor FIONA_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("Fiona baby shower")
            .withDateTime("08/03/2025 1300")
            .withPackageType("BABY")
            .withTags("Andrew").build();

    public static final BookingDescriptor GEORGE_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("George retirement")
            .withDateTime("05/04/2025 1830")
            .withPackageType("FAMILY")
            .withTags("largegroup").build();

    // Manually added
    public static final BookingDescriptor HOON_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("Hoon networking")
            .withDateTime("12/05/2025 1700")
            .withPackageType("CORPORATE").build();

    public static final BookingDescriptor IDA_BOOKINGDESCRIPTOR = new BookingDescriptorBuilder()
            .withDescription("Ida housewarming")
            .withDateTime("20/06/2025 1600")
            .withPackageType("OTHER").build();

    // Actual Bookings (with client names from TypicalPersons)
    public static final Booking ALICE_BOOKING = new BookingBuilder()
            .withName("Alice Pauline")
            .withDescription("Alice birthday")
            .withDateTime("15/12/2024 1400")
            .withPackageType("BIRTHDAY")
            .withTags("party")
            .build();

    public static final Booking BENSON_BOOKING = new BookingBuilder()
            .withName("Benson Meier")
            .withDescription("Benson corporate")
            .withDateTime("20/11/2024 1800")
            .withPackageType("CORPORATE")
            .withTags("product")
            .build();

    public static final Booking CARL_BOOKING = new BookingBuilder()
            .withName("Carl Kurz")
            .withDescription("Carl wedding")
            .withDateTime("10/01/2025 1900")
            .withPackageType("WEDDING")
            .withTags("hotel", "fountain")
            .build();

    public static final Booking DANIEL_BOOKING = new BookingBuilder()
            .withName("Daniel Meier")
            .withDescription("Daniel anniversary")
            .withDateTime("25/12/2024 2000")
            .withPackageType("ANNIVERSARY")
            .withTags("candlelight")
            .build();

    public static final Booking ELLE_BOOKING = new BookingBuilder()
            .withName("Elle Meyer")
            .withDescription("Elle graduation")
            .withDateTime("14/02/2025 1500")
            .withPackageType("GRADUATION")
            .withTags("school")
            .build();

    public static final Booking FIONA_BOOKING = new BookingBuilder()
            .withName("Fiona Kunz")
            .withDescription("Fiona baby shower")
            .withDateTime("08/03/2025 1300")
            .withPackageType("BABY")
            .withTags("Andrew")
            .build();

    public static final Booking GEORGE_BOOKING = new BookingBuilder()
            .withName("George Best")
            .withDescription("George retirement")
            .withDateTime("05/04/2025 1830")
            .withPackageType("FAMILY")
            .withTags("largegroup")
            .build();

    // Manually added bookings
    public static final Booking HOON_BOOKING = new BookingBuilder()
            .withName("Hoon Meier")
            .withDescription("Hoon networking")
            .withDateTime("12/05/2025 1700")
            .withPackageType("CORPORATE")
            .build();

    public static final Booking IDA_BOOKING = new BookingBuilder()
            .withName("Ida Mueller")
            .withDescription("Ida housewarming")
            .withDateTime("20/06/2025 1600")
            .withPackageType("OTHER")
            .build();

    public static final String KEYWORD_MATCHING_WEDDING = "wedding"; // A keyword that matches WEDDING

    private TypicalBookings() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons and bookings.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();

        // Add typical persons
        for (Person person : TypicalPersons.getTypicalPersons()) {
            ab.addPerson(person);
        }

        // Add typical bookings
        for (Booking booking : getTypicalBookings()) {
            ab.addBooking(booking);
        }

        return ab;
    }

    /**
     * Returns a list of typical bookings.
     */
    public static List<Booking> getTypicalBookings() {
        return new ArrayList<>(Arrays.asList(ALICE_BOOKING, BENSON_BOOKING,
                CARL_BOOKING, DANIEL_BOOKING, ELLE_BOOKING,
                FIONA_BOOKING, GEORGE_BOOKING, HOON_BOOKING, IDA_BOOKING));
    }

    /**
     * Returns a list of typical booking descriptors.
     */
    public static List<BookingDescriptor> getTypicalBookingDescriptors() {
        return new ArrayList<>(Arrays.asList(ALICE_BOOKINGDESCRIPTOR, BENSON_BOOKINGDESCRIPTOR,
                CARL_BOOKINGDESCRIPTOR, DANIEL_BOOKINGDESCRIPTOR, ELLE_BOOKINGDESCRIPTOR,
                FIONA_BOOKINGDESCRIPTOR, GEORGE_BOOKINGDESCRIPTOR, HOON_BOOKINGDESCRIPTOR, IDA_BOOKINGDESCRIPTOR));
    }
}
