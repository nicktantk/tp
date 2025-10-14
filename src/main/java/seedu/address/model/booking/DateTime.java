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

    public final String value;

    /**
     * Constructs a {@code Description}.
     *
     * @param dateTime A valid dateTime.
     */
    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS);
        value = dateTime;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDateTime(String test) {
        if (test == null) return false;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

        try {
            LocalDateTime dateTime = LocalDateTime.parse(test, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        LocalDateTime dateTime = LocalDateTime.parse(value, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HHmm'hrs'");
        String formatted = dateTime.format(outputFormatter);
        return formatted;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DateTime otherDateTime)) {
            return false;
        }
        return value.equals(otherDateTime.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
