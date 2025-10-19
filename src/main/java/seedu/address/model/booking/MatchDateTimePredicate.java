package seedu.address.model.booking;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Booking}'s {@code DateTime} matches the given datetime.
 */
public class MatchDateTimePredicate implements Predicate<Booking> {
    private final DateTime dateTime;

    public MatchDateTimePredicate(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean test(Booking booking) {
        return booking.getDateTime().equals(dateTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MatchDateTimePredicate otherPredicate)) {
            return false;
        }

        return dateTime.equals(otherPredicate.dateTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("dateTime", dateTime).toString();
    }
}
