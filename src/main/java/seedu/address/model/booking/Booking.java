package seedu.address.model.booking;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * Represents a booking made by a client/person.
 * Each booking contains a description of the booking,
 * reference to the client, the date of the booking,
 * the package type, and any additional notes.
 */
public class Booking {
    private final Description description;
    private final Name name;
    private final DateTime dateTime;
    private final PackageType packageType;
    private final Set<Tag> tags = new HashSet<>();
    private final boolean isDone;

    /**
     * Constructs a Booking.
     *
     * @param description A short description of the booking
     * @param name        The client/person making the booking.
     * @param dateTime    The date and time of the booking.
     * @param packageType The type of package booked.
     * @param tags        Additional tags for the booking.
     * @param isDone      Completion status of booking
     */
    public Booking(Description description, Name name, DateTime dateTime,
                   PackageType packageType, Set<Tag> tags, boolean isDone) {
        requireAllNonNull(description, name, dateTime, packageType, tags, isDone);
        this.description = description;
        this.name = name;
        this.dateTime = dateTime;
        this.packageType = packageType;
        this.tags.addAll(tags);
        this.isDone = isDone;
    }

    /**
     * Returns the client/person associated with this booking.
     *
     * @return the client
     */
    public Name getName() {
        return name;
    }

    /**
     * Returns the description associated with this booking.
     *
     * @return the description
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Returns the date of the booking.
     *
     * @return the booking date
     */
    public DateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns the completion status of the booking
     *
     * @return boolean value of if the booking is done
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Sets the completion status of the booking to be done
     *
     * @return new Booking with completed booking status
     */
    public Booking markBooking() {
        return new Booking(this.description, this.name, this.dateTime, this.packageType, this.tags, true);
    }

    /**
     * Sets the completion status of the booking to be not done
     *
     * @return new Booking with completed booking status
     */
    public Booking unMarkBooking() {
        return new Booking(this.description, this.name, this.dateTime, this.packageType, this.tags, false);
    }


    /**
     * Returns the package type of the booking.
     *
     * @return the package type
     */
    public PackageType getPackageType() {
        return packageType;
    }

    /**
     * Returns the notes for the booking.
     *
     * @return the notes
     */
    public Set<Tag> getTags() {
        return tags;
    }

    /**
     * Returns true if both clients are equal.
     * This defines a weaker notion of equality between two bookings.
     */
    public boolean isSameBooking(Booking otherBooking) {
        if (otherBooking == this) {
            return true;
        }

        return otherBooking != null
                && otherBooking.getDateTime().equals(this.getDateTime());
    }

    @Override
    public String toString() {
        return String.format("Booking[client=%s, date=%s, packageType=%s, notes=%s]",
                name, dateTime, packageType, tags);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Booking otherBooking)) {
            return false;
        }

        return description.equals(otherBooking.description)
                && name.equals(otherBooking.name)
                && dateTime.equals(otherBooking.dateTime)
                && packageType.equals(otherBooking.packageType)
                && tags.equals(otherBooking.tags)
                && isDone == otherBooking.isDone;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, name, dateTime, packageType, tags);
    }
}
