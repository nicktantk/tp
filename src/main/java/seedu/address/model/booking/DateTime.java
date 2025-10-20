package seedu.address.model.booking;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Represents a description or notes for a booking.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid date-time format. Expected format: dd/MM/yyyy HHmm";

    public final LocalDateTime dateTime;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param dateTime A valid dateTime in String.
     */
    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        this.dateTime = LocalDateTime.parse(dateTime, inputFormatter);

    }

    /**
     * Constructs a {@code Description}.
     *
     * @param dateTime A valid dateTime in LocalDateTime.
     */
    public DateTime(LocalDateTime dateTime) {
        requireNonNull(dateTime);
        this.dateTime = dateTime;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDateTime(String test) {
        if (test == null) {
            return false;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

        try {
            LocalDateTime dateTime = LocalDateTime.parse(test, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns string in "14 October 2025 1200hrs" format
     */
    public String toFormattedString() {

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HHmm'hrs'");
        return this.dateTime.format(outputFormatter);
    }

    @Override
    public String toString() {
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DateTime otherDateTime)) {
            return false;
        }
        return dateTime.equals(otherDateTime.dateTime);
    }

    /**
     * Compares this DateTime with another DateTime chronologically.
     * Returns a negative integer if this DateTime is before the other,
     * zero if they are equal, or a positive integer if this DateTime is after the other.
     *
     * @param other The other DateTime to compare to.
     * @return A negative integer, zero, or a positive integer as this DateTime
     *         is before, equal to, or after the specified DateTime.
     */
    public int compareTo(DateTime other) {
        requireNonNull(other);
        return dateTime.compareTo(other.dateTime);
    }

}
