package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandList;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://se-education.org/addressbook-level3/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

    public static final String COMMAND_USAGE = CommandList.ALL_COMMANDS.stream().toString();

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML private Button copyButton;

    @FXML private Label helpMessage;

    @FXML
    private ScrollPane commandUsage;

    @FXML
    private VBox commandContainer;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, new Stage());

        Stage stage = getRoot();
        helpMessage.setText(HELP_MESSAGE);
        stage.initOwner(root);
        stage.initModality(javafx.stage.Modality.WINDOW_MODAL);

    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /** Populates the scrollable list of commands dynamically. */
    private void populateCommandList() {
        Label intro = new Label("List of all available commands:");
        intro.getStyleClass().add("command-intro");
        commandContainer.getChildren().add(intro);

        CommandList.ALL_COMMANDS.forEach(cmd -> {
            Label title = new Label(cmd.word());
            title.getStyleClass().add("command-title");
            Label usage = new Label(cmd.usage());
            usage.getStyleClass().add("command-usage");
            usage.setWrapText(true);
            VBox commandBox = new VBox(3, title, usage);

            commandBox.getStyleClass().add("command-box");
            commandContainer.getChildren().add(commandBox);

        });
    }

    private void dimBackground(boolean dim) {
        Stage owner = (Stage) getRoot().getOwner();
        if (owner == null) {
            return;
        }

        if (dim) {
            owner.getScene().getRoot().setEffect(new javafx.scene.effect.BoxBlur(5, 5, 3));
        } else {
            owner.getScene().getRoot().setEffect(null);
        }
    }

    /**
     * * Shows the help window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other
     *                               than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        dimBackground(true);
        getRoot().centerOnScreen();
        populateCommandList();
        getRoot().showAndWait(); // Wait until Help window closes
        dimBackground(false);
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
