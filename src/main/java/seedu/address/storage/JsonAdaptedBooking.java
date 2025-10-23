package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.DateTime;
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
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String isPaid;

    /**
     * Constructs a {@code JsonAdaptedBooking} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedBooking(@JsonProperty("description") String description,
                              @JsonProperty("name") String name,
                              @JsonProperty("dateTime") String dateTime,
                              @JsonProperty("packageType") String packageType,
                              @JsonProperty("tags") List<JsonAdaptedTag> tags,
                              @JsonProperty("isPaid") String isPaid) {
        this.description = description;
        this.name = name;
        this.dateTime = dateTime;
        this.packageType = packageType;
        this.isPaid = isPaid;

        if (tags != null) {
            this.tags.addAll(tags);
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
        isPaid = Boolean.toString(source.isPaid());
        tags.addAll(source.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted booking object into the model's {@code Booking} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Booking toModelType() throws IllegalValueException {
        final List<Tag> bookingTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            bookingTags.add(tag.toModelType());
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
                    DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        final DateTime modelDateTime = new DateTime(dateTime);

        if (packageType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                PackageType.class.getSimpleName()));
        }
        if (!PackageType.isValidPackageType(packageType)) {
            throw new IllegalValueException(PackageType.MESSAGE_CONSTRAINTS);
        }

        PackageType modelPackageType = PackageType.valueOf(packageType);

        if (isPaid == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Boolean.class.getSimpleName()));
        }

        boolean modelIsPaid = Boolean.parseBoolean(isPaid);

        final Set<Tag> modelTags = new HashSet<>(bookingTags);

        return new Booking(modelDescription, modelName, modelDateTime, modelPackageType, modelTags, modelIsPaid);
    }
}

