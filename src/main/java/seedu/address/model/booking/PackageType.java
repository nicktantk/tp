package seedu.address.model.booking;

/**
 * Represents the type of photography package for a booking.
 * This enum is immutable and covers common photography services.
 */
public enum PackageType {
    PORTRAIT, FAMILY, EVENT, WEDDING, CORPORATE, PRODUCT, MATERNITY, BABY, GRADUATION, BIRTHDAY, ANNIVERSARY, OTHER;

    public static final String MESSAGE_CONSTRAINTS =
            "PackageType must be one of the following: " + "PORTRAIT," + "FAMILY, " + "EVENT, " + "WEDDING, "
                    + "CORPORATE, " + "PRODUCT, " + "MATERNITY, " + "BABY, " + "GRADUATION, " + "BIRTHDAY, "
                    + "ANNIVERSARY, " + "OTHER";

    /**
     * Returns true if a given string is a valid PackageType.
     */
    public static boolean isValidPackageType(String input) {
        try {
            PackageType.valueOf(input.toUpperCase());
            return true; // matches a valid enum constant
        } catch (IllegalArgumentException e) {
            return false; // not a valid enum name
        }
    }
}
