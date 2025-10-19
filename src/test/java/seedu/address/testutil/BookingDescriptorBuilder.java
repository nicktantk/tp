package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingDescriptor;
import seedu.address.model.booking.DateTime;
import seedu.address.model.booking.Description;
import seedu.address.model.booking.PackageType;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building BookingDescriptor objects.
 */
public class BookingDescriptorBuilder {

    private final BookingDescriptor descriptor;

    public BookingDescriptorBuilder() {
        descriptor = new BookingDescriptor();
    }

    public BookingDescriptorBuilder(BookingDescriptor descriptor) {
        this.descriptor = new BookingDescriptor(descriptor);
    }

    /**
     * Returns an {@code BookingDescriptor} with fields containing {@code booking}'s details
     */
    public BookingDescriptorBuilder(Booking booking) {
        descriptor = new BookingDescriptor();
        descriptor.setDescription(booking.getDescription());
        descriptor.setDateTime(booking.getDateTime());
        descriptor.setPackageType(booking.getPackageType());
        descriptor.setTags(booking.getTags());
    }

    /**
     * Sets the {@code Description} of the {@code BookingDescriptor} that we are building.
     */
    public BookingDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code BookingDescriptor} that we are building.
     */
    public BookingDescriptorBuilder withDateTime(String dateTime) {
        descriptor.setDateTime(new DateTime(dateTime));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code BookingDescriptor} that we are building.
     */
    public BookingDescriptorBuilder withPackageType(String packageType) {
        descriptor.setPackageType(PackageType.valueOf(packageType.toUpperCase()));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code BookingDescriptor}
     * that we are building.
     */
    public BookingDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public BookingDescriptor build() {
        return descriptor;
    }
}
