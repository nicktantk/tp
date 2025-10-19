package seedu.address.model.booking;

import java.util.Comparator;

/**
 * Defines a lexicographic comparator between two Person objects
 */
public class DateTimeComparator implements Comparator<Booking> {
    @Override
    public int compare(Booking o1, Booking o2) {
        return o1.getDateTime().compareTo(o2.getDateTime());
    }
}
