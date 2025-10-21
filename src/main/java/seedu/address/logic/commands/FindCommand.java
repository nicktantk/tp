package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PersonHasStatusPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: name + [name1 name2...] or "
            + "status + [status1 status2...]\n"
            + "Example: " + COMMAND_WORD + " name alice bob charlie\n"
            + "Another example: " + COMMAND_WORD + " status active returning";

    private final String findBy;
    private final List<String> keywords;

    /**
     * Finds persons either by name of status
     * @param findBy
     * @param keywords
     */
    public FindCommand(String findBy, List<String> keywords) {
        this.findBy = findBy;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute(Model model) {
        if (findBy.equals("NAME")) {
            requireNonNull(model);
            NameContainsKeywordsPredicate pred = new NameContainsKeywordsPredicate(keywords);
            model.updateFilteredPersonList(pred);
            return new CommandResult(
                    String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));

        } else {
            requireNonNull(model);
            PersonHasStatusPredicate pred = new PersonHasStatusPredicate(keywords);
            model.updateFilteredPersonList(pred);
            return new CommandResult(
                    String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand otherFindCommand)) {
            return false;
        }

        return findBy.equals(otherFindCommand.findBy) && keywords.equals(otherFindCommand.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("findBy", findBy)
                .add("keywords", keywords)
                .toString();
    }
}
