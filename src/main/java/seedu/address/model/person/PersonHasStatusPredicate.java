package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person} is the same as the target {@code Person} provided.
 */
public class PersonHasStatusPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonHasStatusPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getStatus().equals(Status.valueOf(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonHasStatusPredicate otherPersonHasStatusPredicate)) {
            return false;
        }

        return keywords.equals(otherPersonHasStatusPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
