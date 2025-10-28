package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.DateTime;
import seedu.address.model.booking.Description;
import seedu.address.model.booking.PackageType;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Phone("87438807"),
                new Email("alexyeoh@example.com"),
                Status.ACTIVE,
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com"),
                Status.ACTIVE,
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"),
                    Status.ACTIVE,
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com"),
                    Status.ACTIVE,
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Email("irfan@example.com"),
                    Status.ACTIVE,
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Email("royb@example.com"),
                    Status.ACTIVE,
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static Booking[] getSampleBookings() {
        return new Booking[]{
            new Booking(
                new Description("Wedding Shoot"),
                new Name("Alex Yeoh"),
                new DateTime("14/10/2025 1200"),
                PackageType.PORTRAIT,
                getTagSet("outdoor", "summer"),
                false
            ),
            new Booking(
                new Description("Corporate Product Photoshoot"),
                new Name("Bernice Yu"),
                new DateTime("05/11/2025 0930"),
                PackageType.PRODUCT,
                getTagSet("indoor", "studio"),
                true
            ),
            new Booking(
                new Description("Family Portrait Session"),
                new Name("Roy Balakrishnan"),
                new DateTime("10/11/2025 1430"),
                PackageType.PORTRAIT,
                getTagSet("family", "kids"),
                false
            ),
            new Booking(
                new Description("Event Coverage - Charity Gala"),
                new Name("Bernice Yu"),
                new DateTime("15/11/2025 1800"),
                PackageType.EVENT,
                getTagSet("night", "formal"),
                true
            ),
            new Booking(
                new Description("Pre-wedding Beach Shoot"),
                new Name("Roy Balakrishnan"),
                new DateTime("20/11/2025 1600"),
                PackageType.WEDDING,
                getTagSet("outdoor", "beach", "sunset"),
                false
            )
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Booking sampleBooking : getSampleBookings()) {
            sampleAb.addBooking(sampleBooking);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

}
