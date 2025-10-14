package seedu.address.storage;

import java.time.LocalDate;
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
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
public class JsonAdaptedBooking {


    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Booking's %s field is missing!";

    private final JsonAdaptedPerson client;
    private final String description;
    private final String date;
    private final String packageType;
    private final List<JsonAdaptedTag> notes = new ArrayList<>();
    private final String isDone;

    /**
     * Constructs a {@code JsonAdaptedBooking} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedBooking(@JsonProperty("client") JsonAdaptedPerson client,
                              @JsonProperty("description") String description,
                              @JsonProperty("date") String date, @JsonProperty("packageType") String packageType,
                              @JsonProperty("notes") List<JsonAdaptedTag> tags, @JsonProperty("isDone") String isDone) {
        this.client = client;
        this.description = description;
        this.date = date;
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
        client = new JsonAdaptedPerson(source.getClient());
        description = source.getDescription().value;
        date = source.getDate().toString();
        packageType = source.getPackageType().toString();
        isDone = Boolean.toString(source.isDone());
        notes.addAll(source.getNotes().stream()
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

        Person modelClient = client.toModelType();
        if (modelClient == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                LocalDate.class.getSimpleName()));
        }

        LocalDate modelDate;
        try {
             modelDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu");
                modelDate = LocalDate.parse(date, formatter);
            } catch (DateTimeParseException ex) {
                throw new IllegalValueException("Use: <yyyy-mm-dd | dd/MM/yyyy>");
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

        return new Booking(modelDescription, modelClient, modelDate, modelPackageType, modelNotes, modelIsDone);
       
    }
}

