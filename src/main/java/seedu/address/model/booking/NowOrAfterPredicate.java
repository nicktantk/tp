package seedu.address.model.booking;

import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * Tests that a {@code Booking}'s {@code DateTime} is after the current date time.
 */
public class NowOrAfterPredicate implements Predicate<Booking> {

    @Override
    public boolean test(Booking booking) {
        return booking.getDateTime().compareTo(new DateTime(LocalDateTime.now())) > 0;
    }
}
