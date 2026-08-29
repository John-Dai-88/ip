package jarvis.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a message box consisting of an ImageView
 * and a Label containing the message text.
 */
public class MessageBox extends HBox {

    @FXML
    private Label textLabel;

    @FXML
    private ImageView displayPicture;

    /**
     * Creates a message box.
     *
     * @param message message text
     * @param image speaker image
     * @param isUser whether this message belongs to the user
     */
    public MessageBox(String message, Image image, boolean isUser) {

        FXMLLoader loader = new FXMLLoader(
                MessageBox.class.getResource("/view/MessageBox.fxml")
        );

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load MessageBox.fxml", e
            );
        }

        textLabel.setText(message);
        displayPicture.setImage(image);

        if (!isUser) {
            getStyleClass().add("jarvis-message");
            flip();
        } else {
            getStyleClass().add("user-message");
        }
    }

    /**
     * Flips the message box so that the image is on the left
     * and the message is on the right.
     */
    private void flip() {

        ObservableList<Node> children =
                FXCollections.observableArrayList(getChildren());

        Collections.reverse(children);

        getChildren().setAll(children);

        setAlignment(Pos.CENTER_LEFT);
    }
}
