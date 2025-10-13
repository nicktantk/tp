package seedu.address.model.booking;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a booking made by a client/person.
 * Each booking contains a description of the booking,
 * reference to the client, the date of the booking,
 * the package type, and any additional notes.
 */
public class Booking {
    private final Person client;
    private final LocalDate date;
    private final PackageType packageType;
    private final Set<Tag> notes = new HashSet<>();
    private final boolean isDone;
    private final Description description;

    /**
     * Constructs a Booking.
     *
     * @param description A short description of the booking
     * @param client      The client/person making the booking.
     * @param date        The date of the booking.
     * @param packageType The type of package booked.
     * @param notes       Additional notes for the booking.
     * @param isDone      Completion status of booking
     */
    public Booking(Description description, Person client, LocalDate date, PackageType packageType, Set<Tag> notes, boolean isDone) {
        requireAllNonNull(client, date, packageType, notes, isDone);
        this.description = description;
        this.client = client;
        this.date = date;
        this.packageType = packageType;
        this.notes.addAll(notes);
        this.isDone = isDone;
    }

    /**
     * Returns the client/person associated with this booking.
     * @return the client
     */
    public Person getClient() {
        return client;
    }

    /**
     * Returns the description associated with this booking.
     * @return the description
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Returns the date of the booking.
     * @return the booking date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the completion status of the booking
     * @return boolean value of if the booking is done
     */
    public boolean isDone(){
        return this.isDone;
    }

    /**
     * Sets the completion status of the booking to be done
     * @return new Booking with completed booking status
     */
    public Booking markBooking(){
        return new Booking(this.description, this.client, this.date, this.packageType, this.notes, true);
    }

    /**
     * Sets the completion status of the booking to be not done
     * @return new Booking with completed booking status
     */
    public Booking unMarkBooking(){
        return new Booking(this.description, this.client, this.date, this.packageType, this.notes, false);
    }



    /**
     * Returns the package type of the booking.
     * @return the package type
     */
    public PackageType getPackageType() {
        return packageType;
    }

    /**
     * Returns the notes for the booking.
     * @return the notes
     */
    public Set<Tag> getNotes() {
        return notes;
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
                && otherBooking.getClient().equals(getClient());
    }

   @Override
    public String toString() {
        return String.format("Booking[client=%s, date=%s, packageType=%s, notes=%s]",
                client, date, packageType, notes);
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
        if (!(other instanceof Booking)) {
            return false;
        }

        Booking otherBooking = (Booking) other;
        return description.equals(otherBooking.description)
                && client.equals(otherBooking.client)
                && date.equals(otherBooking.date)
                && packageType.equals(otherBooking.packageType)
                && notes.equals(otherBooking.notes)
                && isDone == otherBooking.isDone;
    }
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, client, date, packageType, notes);
    }
}