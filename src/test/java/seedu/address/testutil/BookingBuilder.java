package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.booking.Booking;
import seedu.address.model.booking.DateTime;
import seedu.address.model.booking.Description;
import seedu.address.model.booking.PackageType;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Booking objects for testing.
 */
public class BookingBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_DESCRIPTION = "Wedding Photoshoot";
    public static final String DEFAULT_DATETIME = "2025-10-20 14:00";
    public static final String DEFAULT_PACKAGETYPE = "WEDDING";
    public static final boolean DEFAULT_ISDONE = false;

    private Name name;
    private Description description;
    private DateTime dateTime;
    private PackageType packageType;
    private Set<Tag> tags;
    private boolean isDone;

    /**
     * Creates a {@code BookingBuilder} with the default details.
     */
    public BookingBuilder() {
        name = new Name(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        dateTime = new DateTime(DEFAULT_DATETIME);
        packageType = PackageType.valueOf(DEFAULT_PACKAGETYPE);
        tags = new HashSet<>();
        isDone = DEFAULT_ISDONE;
    }

    /**
     * Initializes the BookingBuilder with the data of {@code bookingToCopy}.
     */
    public BookingBuilder(Booking bookingToCopy) {
        name = bookingToCopy.getName();
        description = bookingToCopy.getDescription();
        dateTime = bookingToCopy.getDateTime();
        packageType = bookingToCopy.getPackageType();
        tags = new HashSet<>(bookingToCopy.getTags());
        isDone = bookingToCopy.isDone();
    }

    public BookingBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public BookingBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public BookingBuilder withDateTime(String dateTime) {
        this.dateTime = new DateTime(dateTime);
        return this;
    }

    public BookingBuilder withPackageType(String packageType) {
        this.packageType = PackageType.valueOf(packageType);
        return this;
    }

    public BookingBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public BookingBuilder withIsDone(boolean isDone) {
        this.isDone = isDone;
        return this;
    }

    public Booking build() {
        return new Booking(description, name, dateTime, packageType, tags, isDone);
    }
}
