package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.person.LexicographicComparator;
import seedu.address.model.person.Person;

/**
 * Sorts and lists all persons based on alphabetical order
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Clients sorted by lexicographical order.";
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Comparator<Person> lexicographicComparator = new LexicographicComparator();
        model.sortPersonList(lexicographicComparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
