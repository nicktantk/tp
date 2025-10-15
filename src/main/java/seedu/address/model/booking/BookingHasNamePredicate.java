package seedu.address.model.booking;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;

/**
 * Tests that a {@code Booking}'s {@code Name} matches the name given.
 */
public class BookingHasNamePredicate implements Predicate<Booking> {
    private final Name targetName;

    public BookingHasNamePredicate(Name name) {
        this.targetName = name;
    }

    @Override
    public boolean test(Booking booking) {
        return booking.getName().equals(targetName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookingHasNamePredicate otherBookingHasNamePredicate)) {
            return false;
        }

        return targetName.equals(otherBookingHasNamePredicate.targetName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("targetName", targetName).toString();
    }
}
