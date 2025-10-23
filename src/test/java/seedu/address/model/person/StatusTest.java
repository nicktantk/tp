package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {


    @Test
    public void isValidStatus() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // invalid status numbers
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only

        // valid phone numbers
        assertTrue(Status.isValidStatus("Prospect")); // exactly 3 numbers
        assertTrue(Status.isValidStatus("Potential"));
        assertTrue(Status.isValidStatus("Active")); // long phone numbers
    }


    @Test
    public void valueOf_returnsCorrectEnum() {
        assertTrue(Status.valueOf("ACTIVE") == Status.ACTIVE);
        assertThrows(IllegalArgumentException.class, () -> Status.valueOf("INVALID"));
    }
}
