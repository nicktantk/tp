package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getPersonsOnlyAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private final Model model = new ModelManager(getPersonsOnlyAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getPersonsOnlyAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        String findByName = "name";
        String findByStatus = "status";
        List<String> nameKeywords = Arrays.asList("Alice", "Bob", "Charlie");
        List<String> statusKeywords = Arrays.asList("Active", "Prospect");

        FindCommand findFirstCommand = new FindCommand(findByName, nameKeywords);
        FindCommand findSecondCommand = new FindCommand(findByStatus, statusKeywords);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(findByName, nameKeywords);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different parameters -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);

        // key is required; keywords list empty
        String key = "name"; // or whichever field your parser expects
        List<String> keywords = Collections.emptyList();

        FindCommand command = new FindCommand(key, keywords);

        // Let FindCommand build the appropriate predicate internally; expectedModel should mirror that
        // For integration test, update via the same rules that FindCommand uses for an empty list
        expectedModel.filterPersonList(person -> false); // no matches when no keywords

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getModifiedPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);

        String key = "NAME";
        List<String> keywords = Arrays.asList("Kurz", "Elle", "Kunz");

        FindCommand command = new FindCommand(key, keywords);

        // Expected model mirrors the predicate logic that FindCommand applies for key="name"
        expectedModel.filterPersonList(new NameContainsKeywordsPredicate(keywords));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getModifiedPersonList());
    }

    @Test
    public void toStringMethod() {
        String key = "name";
        List<String> keywords = List.of("keyword");
        FindCommand findCommand = new FindCommand(key, keywords);

        // Adjust expected string to match FindCommand.toString() implementation
        String expected = FindCommand.class.getCanonicalName()
                + "{findBy=" + key + ", keywords=" + keywords + "}";

        assertEquals(expected, findCommand.toString());
    }

}
