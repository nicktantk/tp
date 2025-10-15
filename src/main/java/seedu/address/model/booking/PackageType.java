package seedu.address.model.booking;

/**
 * Represents the type of photography package for a booking.
 * This enum is immutable and covers common photography services.
 */
public enum PackageType {
    PORTRAIT,
    FAMILY,
    EVENT,
    WEDDING,
    CORPORATE,
    PRODUCT,
    MATERNITY,
    NEWBORN,
    PET,
    GRADUATION,
    OTHER;

    public static final String MESSAGE_CONSTRAINTS = "PackageType must be one of the following: "
            + "PORTRAIT, FAMILY, EVENT, WEDDING, CORPORATE, PRODUCT, MATERNITY, NEWBORN, PET, GRADUATION, OTHER";
}
