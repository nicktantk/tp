package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person} is the same as the target {@code Person} provided.
 */
public class MatchPersonPredicate implements Predicate<Person> {
    private final Person targetPerson;

    public MatchPersonPredicate(Person person) {
        this.targetPerson = person;
    }

    @Override
    public boolean test(Person person) {
        return person.isSamePerson(targetPerson);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MatchPersonPredicate otherMatchPersonPredicate)) {
            return false;
        }

        return targetPerson.isSamePerson(otherMatchPersonPredicate.targetPerson);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("targetPerson", targetPerson).toString();
    }
}
