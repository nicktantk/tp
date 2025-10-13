package seedu.address.ui.booking;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.booking.Booking;
import seedu.address.ui.UiPart;


/**
 * Panel containing the list of Bookings.
 */
public class BookingListPanel extends UiPart<Region> {
    private static final String FXML = "booking/BookingListPanel.fxml";

    @FXML
    private ListView<Booking> bookingListView;

    /**
     * Creates a {@code BookingListPanel} with the given {@code ObservableList}.
     */
    public BookingListPanel(ObservableList<Booking> bookingList) {
        super(FXML);
        bookingListView.setItems(bookingList);
        bookingListView.setCellFactory(listView -> new BookingListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Booking} using a {@code BookingCard}.
     */
    class BookingListViewCell extends ListCell<Booking> {
        @Override
        protected void updateItem(Booking booking, boolean empty) {
            super.updateItem(booking, empty);

            if (empty || booking == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new BookingCard(booking, getIndex() + 1).getRoot());
            }
        }
    }
}
