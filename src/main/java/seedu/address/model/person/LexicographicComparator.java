package seedu.address.model.person;

import java.util.Comparator;

/**
 * Defines a lexicographic comparator between two Person objects
 */
public class LexicographicComparator implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        return o1.getName().fullName.compareToIgnoreCase(o2.getName().fullName);
    }
}
