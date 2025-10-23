package seedu.address.model.booking;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Stores the details to edit the person with. Each non-empty field value will replace the
 * corresponding field value of the person.
 */
public class BookingDescriptor {
    private Description description;
    private DateTime dateTime;
    private PackageType packageType;
    private Set<Tag> tags = Collections.emptySet();

    public BookingDescriptor() {
    }

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public BookingDescriptor(BookingDescriptor toCopy) {
        setDescription(toCopy.description);
        setDateTime(toCopy.dateTime);
        setPackageType(toCopy.packageType);
        setTags(toCopy.tags);
    }
    /**
     * Constructor that copies the relevant fields from a {@code Booking} object.
     */
    public BookingDescriptor(Booking booking) {
        setDescription(booking.getDescription());
        setDateTime(booking.getDateTime());
        setPackageType(booking.getPackageType());
        setTags(booking.getTags());
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        requireNonNull(description);
        this.description = description;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        requireNonNull(dateTime);
        this.dateTime = dateTime;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(PackageType packageType) {
        requireNonNull(packageType);
        this.packageType = packageType;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Set<Tag> getTags() {
        return (tags != null) ? Collections.unmodifiableSet(tags) : Collections.emptySet();
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : Collections.emptySet();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookingDescriptor otherBookingDescriptor)) {
            return false;
        }

        return Objects.equals(description, otherBookingDescriptor.description)
                && Objects.equals(dateTime, otherBookingDescriptor.dateTime)
                && Objects.equals(packageType, otherBookingDescriptor.packageType)
                && Objects.equals(tags, otherBookingDescriptor.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("description", description)
                .add("dateTime", dateTime)
                .add("packageType", packageType)
                .add("tags", tags)
                .toString();
    }
}
