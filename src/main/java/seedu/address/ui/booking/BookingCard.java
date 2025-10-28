package seedu.address.ui.booking;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.booking.Booking;
import seedu.address.ui.UiPart;

/**
 * A UI component that displays information of a {@code Booking}.
 */
public class BookingCard extends UiPart<Region> {
    private static final String FXML = "booking/BookingListCard.fxml";

    public final Booking booking;

    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label client;
    @FXML
    private Label date;
    @FXML
    private Label packageType;
    @FXML
    private FlowPane notes;
    @FXML
    private Label status;

    /**
     * Creates a {@code Booking} with the given {@code Booking} and index to display.
     */
    public BookingCard(Booking booking, int displayedIndex) {
        super(FXML);
        this.booking = booking;
        id.setText(displayedIndex + ". ");
        description.setText(booking.getDescription().value);
        client.setText("Client: " + booking.getName().toString());
        date.setText("Date: " + booking.getDateTime().toFormattedString());
        packageType.setText(booking.getPackageType().toString());
        booking.getTags().stream().sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> notes.getChildren().add(new Label(tag.tagName)));
        String isPaid = booking.isPaid() ? "PAID" : "UNPAID";
        status.getStyleClass().removeAll(isPaid);
        status.getStyleClass().add(isPaid);
        status.setText(isPaid);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof BookingCard
                && id.getText().equals(((BookingCard) other).id.getText())
                && booking.equals(((BookingCard) other).booking));
    }
}
