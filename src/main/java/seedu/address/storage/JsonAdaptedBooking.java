package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Description;
import seedu.address.model.booking.PackageType;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
public class JsonAdaptedBooking {


    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Booking's %s field is missing!";

    private final String description;
    private final String name;
    private final String dateTime;
    private final String packageType;
    private final List<JsonAdaptedTag> notes = new ArrayList<>();
    private final String isDone;

    /**
     * Constructs a {@code JsonAdaptedBooking} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedBooking(@JsonProperty("description") String description,
                              @JsonProperty("name") String name,
                              @JsonProperty("dateTime") String dateTime,
                              @JsonProperty("packageType") String packageType,
                              @JsonProperty("tags") List<JsonAdaptedTag> tags,
                              @JsonProperty("isDone") String isDone) {
        this.description = description;
        this.name = name;
        this.dateTime = dateTime;
        this.packageType = packageType;
        this.isDone = isDone;

        if (notes != null) {
            this.notes.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedBooking(Booking source) {
        name = source.getName().toString();
        description = source.getDescription().value;
        dateTime = source.getDateTime().toString();
        packageType = source.getPackageType().toString();
        isDone = Boolean.toString(source.isDone());
        notes.addAll(source.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Booking toModelType() throws IllegalValueException {
        final List<Tag> bookingNotes = new ArrayList<>();
        for (JsonAdaptedTag tag : notes) {
            bookingNotes.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                LocalDate.class.getSimpleName()));
        }

        LocalDateTime modelDate;
        try {
            modelDate = LocalDateTime.parse(dateTime);
        } catch (DateTimeParseException e) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                modelDate = LocalDateTime.parse(dateTime, formatter);
            } catch (DateTimeParseException ex) {
                throw new IllegalValueException("Use: dd/MM/yyyy>");
            }
        }
        if (packageType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                PackageType.class.getSimpleName()));
        }

        PackageType modelPackageType = PackageType.valueOf(packageType);

        if (isDone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Boolean.class.getSimpleName()));
        }

        boolean modelIsDone = Boolean.parseBoolean(isDone);

        final Set<Tag> modelNotes = new HashSet<>(bookingNotes);

        return new Booking(modelDescription, modelName, modelDate, modelPackageType, modelNotes, modelIsDone);
    }
}

