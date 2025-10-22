package seedu.address.model.booking;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Stores the details to edit the booking with. Each non-empty field value will replace the
 * corresponding field value of the booking.
 */
public class EditBookingDescriptor {
    private Description description;
    private DateTime dateTime;
    private PackageType packageType;
    private Set<Tag> tags;

    public EditBookingDescriptor() {
    }

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditBookingDescriptor(EditBookingDescriptor toCopy) {
        setDescription(toCopy.description);
        setDateTime(toCopy.dateTime);
        setPackageType(toCopy.packageType);
        setTags(toCopy.tags);
    }

    /**
     * Converts a {@code BookingDescriptor} into an EditBookingDescriptor
     */
    public EditBookingDescriptor(BookingDescriptor toCopy) {
        setDescription(toCopy.getDescription());
        setDateTime(toCopy.getDateTime());
        setPackageType(toCopy.getPackageType());
        setTags(toCopy.getTags());
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(description, dateTime, packageType, tags);
    }

    public Optional<Description> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Optional<DateTime> getDateTime() {
        return Optional.ofNullable(dateTime);
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Optional<PackageType> getPackageType() {
        return Optional.ofNullable(packageType);
    }

    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
    }
    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditBookingDescriptor otherEditPersonDescriptor)) {
            return false;
        }

        return Objects.equals(description, otherEditPersonDescriptor.description)
                && Objects.equals(dateTime, otherEditPersonDescriptor.dateTime)
                && Objects.equals(packageType, otherEditPersonDescriptor.packageType)
                && Objects.equals(tags, otherEditPersonDescriptor.tags);
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
