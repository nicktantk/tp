package seedu.address.model.booking;

import seedu.address.model.person.Person;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a booking made by a client/person.
 * Each booking contains a reference to the client, the date of the booking,
 * the package type, and any additional notes.
 */
public class Booking {
    private final Person client;
    private final LocalDate date;
    private final PackageType packageType;
    private final String notes;

    /**
     * Constructs a Booking.
     *
     * @param client      The client/person making the booking.
     * @param date        The date of the booking.
     * @param packageType The type of package booked.
     * @param notes       Additional notes for the booking.
     */
    public Booking(Person client, LocalDate date, PackageType packageType, String notes) {
        this.client = client;
        this.date = date;
        this.packageType = packageType;
        this.notes = notes;
    }

    /**
     * Returns the client/person associated with this booking.
     * @return the client
     */
    public Person getClient() {
        return client;
    }

    /**
     * Returns the date of the booking.
     * @return the booking date
     */
    public LocalDate getDate() {
        return date;
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
    public String getNotes() {
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
        return client.equals(otherBooking.client)
                && date.equals(otherBooking.date)
                && packageType.equals(otherBooking.packageType)
                && notes.equals(otherBooking.notes);
    }
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(client, date, packageType, notes);
    }
}