package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddBookingCommand.MESSAGE_DUPLICATE_BOOKING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PACKAGETYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.DateTime;
import seedu.address.model.booking.Description;
import seedu.address.model.booking.EditBookingDescriptor;
import seedu.address.model.booking.PackageType;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing booking in the address book.
 */
public class EditBookingCommand extends Command {
    public static final String COMMAND_WORD = "editbooking";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the booking identified "
            + "by the index number used in the displayed booking list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_DATETIME + "DATETIME] "
            + "[" + PREFIX_PACKAGETYPE + "PACKAGE TYPE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "Wedding Shoot "
            + PREFIX_PACKAGETYPE + "WEDDING";

    public static final String MESSAGE_EDIT_BOOKING_SUCCESS = "Edited Booking %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditBookingDescriptor editBookingDescriptor;

    /**
     * @param index                of the booking in the filtered booking list to edit
     * @param editBookingDescriptor details to edit the booking with
     */
    public EditBookingCommand(Index index, EditBookingDescriptor editBookingDescriptor) {
        requireNonNull(index);
        requireNonNull(editBookingDescriptor);

        this.index = index;
        this.editBookingDescriptor = new EditBookingDescriptor(editBookingDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code bookingToEdit}
     * edited with {@code editBookingDescriptor}.
     */
    private static Booking createEditedBooking(Booking bookingToEdit, EditBookingDescriptor editBookingDescriptor) {
        assert bookingToEdit != null;

        Description updatedDescription = editBookingDescriptor.getDescription().orElse(bookingToEdit.getDescription());
        DateTime updatedDateTime = editBookingDescriptor.getDateTime().orElse(bookingToEdit.getDateTime());
        PackageType updatedPackageType = editBookingDescriptor.getPackageType().orElse(bookingToEdit.getPackageType());
        Set<Tag> updatedTags = editBookingDescriptor.getTags().orElse(bookingToEdit.getTags());

        return new Booking(updatedDescription, bookingToEdit.getName(), updatedDateTime, updatedPackageType,
                updatedTags, bookingToEdit.isPaid());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Booking> lastShownList = model.getModifiedBookingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
        }

        Booking bookingToEdit = lastShownList.get(index.getZeroBased());
        Booking editedBooking = createEditedBooking(bookingToEdit, editBookingDescriptor);

        if (!bookingToEdit.isSameBooking(editedBooking) && model.hasBooking(editedBooking)) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOKING);
        }

        model.setBooking(bookingToEdit, editedBooking);

        return new CommandResult(String.format(MESSAGE_EDIT_BOOKING_SUCCESS, Messages.format(editedBooking)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditBookingCommand otherEditBookingCommand)) {
            return false;
        }

        return index.equals(otherEditBookingCommand.index)
                && editBookingDescriptor.equals(otherEditBookingCommand.editBookingDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editBookingDescriptor", editBookingDescriptor)
                .toString();
    }
}
