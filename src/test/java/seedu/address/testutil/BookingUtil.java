package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PACKAGETYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddBookingCommand;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.EditBookingDescriptor;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Booking.
 */
public class BookingUtil {

    /**
     * Returns an addBooking command string for adding the {@code booking}.
     */
    public static String getAddBookingCommand(Booking booking) {
        return AddBookingCommand.COMMAND_WORD + " " + getBookingDetails(booking);
    }

    /**
     * Returns the part of command string for the given {@code booking}'s details.
     */
    public static String getBookingDetails(Booking booking) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DESCRIPTION + booking.getDescription().value + " ");
        sb.append(PREFIX_DATETIME + booking.getDateTime().toString() + " ");
        sb.append(PREFIX_PACKAGETYPE + booking.getPackageType().name() + " ");
        booking.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditBookingDescriptor}'s details.
     */
    public static String getEditBookingDescriptorDetails(EditBookingDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_DESCRIPTION)
                .append(description).append(" "));
        descriptor.getDateTime().ifPresent(dateTime -> sb.append(PREFIX_DATETIME)
                .append(dateTime.toString()).append(" "));
        descriptor.getPackageType().ifPresent(packageType -> sb.append(PREFIX_PACKAGETYPE)
                .append(packageType.name()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
