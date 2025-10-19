package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.booking.Booking;
import seedu.address.model.person.Name;
import seedu.address.model.booking.Description;
import seedu.address.model.booking.DateTime;
import seedu.address.model.booking.PackageType;
import seedu.address.model.booking.BookingDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class BookingBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_DESCRIPTION = "Wedding shoot";
    public static final String DEFAULT_DATETIME = "19/10/2025 1200";
    public static final String DEFAULT_PACKAGETYPE = "WEDDING";
    public static final Tag DEFAULT_TAG = new Tag("Outdoor");
    public static final Boolean DEFAULT_ISDONE = Boolean.FALSE;

    private Name name;
    private Description description;
    private DateTime dateTime;
    private PackageType packageType;
    private Set<Tag> tags;
    private Boolean isDone;

    /**
     * Creates a {@code BookingBuilder} with the default details.
     */
    public BookingBuilder() {
        name = new Name(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
        dateTime = new DateTime(DEFAULT_DATETIME);
        packageType = PackageType.valueOf(DEFAULT_PACKAGETYPE);
        tags = new HashSet<>();
        tags.add(DEFAULT_TAG);
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
    }

    /**
     * Sets the {@code Name} of the {@code Booking} that we are building.
     */
    public BookingBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Booking} that we are building.
     */
    public BookingBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code PackageType} of the {@code Booking} that we are building.
     */
    public BookingBuilder withPackageType(String packageType) {
        this.packageType = PackageType.valueOf(packageType);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Booking} that we are building.
     */
    public BookingBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code DateTime()} of the {@code Booking} that we are building.
     */
    public BookingBuilder withDateTime(String dateTime) {
        this.dateTime = new DateTime(dateTime);
        return this;
    }

    public BookingBuilder withIsDone(Boolean isDone) {
        this.isDone = DEFAULT_ISDONE;
        return this;
    }

    public Booking build() {
        return new Booking(description, name, dateTime, packageType, tags, isDone);
    }

}
