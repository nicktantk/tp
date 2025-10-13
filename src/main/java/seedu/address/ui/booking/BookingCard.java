package seedu.address.ui.booking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.booking.Booking;
import seedu.address.ui.UiPart;

import java.util.Comparator;

public class BookingCard extends UiPart<Region> {
    private static final String FXML = "booking/BookingListCard.fxml";

    public final Booking booking;

    @FXML
    private HBox cardPane;
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

    public BookingCard(Booking booking, int displayedIndex) {
        super(FXML);
        this.booking = booking;
        id.setText(displayedIndex + ". ");
        description.setText(booking.getDescription().value);
        client.setText("Client: " + booking.getClient().getName().toString());
        date.setText("Date: " + booking.getDate().toString());
        packageType.setText(booking.getPackageType().toString());
        booking.getNotes().stream().sorted(Comparator.comparing(notes -> notes.tagName))
                .forEach(tag -> notes.getChildren().add(new Label (tag.tagName)));
        String isDone = booking.isDone() ? "Done" : "Pending";
        status.getStyleClass().removeAll(isDone);
        status.getStyleClass().add(isDone);
        status.setText(isDone);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof BookingCard
                && id.getText().equals(((BookingCard) other).id.getText())
                && booking.equals(((BookingCard) other).booking));
    }
}
