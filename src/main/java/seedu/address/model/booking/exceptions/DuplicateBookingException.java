package seedu.address.model.booking.exceptions;

/**
 * Signals that the operation will result in duplicate booking (Bookings are considered duplicates if they have the same
 * client and details).
 */
public class DuplicateBookingException extends RuntimeException {
    public DuplicateBookingException() {
        super("Operation would result in duplicate booking");
    }
}

