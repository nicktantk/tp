package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.booking.Booking;
import seedu.address.model.booking.DateTime;
import seedu.address.model.booking.Description;
import seedu.address.model.booking.EditBookingDescriptor;
import seedu.address.model.booking.PackageType;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditBookingDescriptor objects.
 */
public class EditBookingDescriptorBuilder {

    private final EditBookingDescriptor descriptor;

    public EditBookingDescriptorBuilder() {
        descriptor = new EditBookingDescriptor();
    }

    public EditBookingDescriptorBuilder(EditBookingDescriptor descriptor) {
        this.descriptor = new EditBookingDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBookingDescriptor} with fields containing {@code booking}'s details
     */
    public EditBookingDescriptorBuilder(Booking booking) {
        descriptor = new EditBookingDescriptor();
        descriptor.setDescription(booking.getDescription());
        descriptor.setDateTime(booking.getDateTime());
        descriptor.setPackageType(booking.getPackageType());
        descriptor.setTags(booking.getTags());
    }

    /**
     * Sets the {@code Description} of the {@code EditBookingDescriptor} that we are building.
     */
    public EditBookingDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditBookingDescriptor} that we are building.
     */
    public EditBookingDescriptorBuilder withDateTime(String dateTime) {
        descriptor.setDateTime(new DateTime(dateTime));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditBookingDescriptor} that we are building.
     */
    public EditBookingDescriptorBuilder withPackageType(String packageType) {
        descriptor.setPackageType(PackageType.valueOf(packageType.toUpperCase()));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditBookingDescriptor}
     * that we are building.
     */
    public EditBookingDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditBookingDescriptor build() {
        return descriptor;
    }

}
