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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Represents a message box consisting of an ImageView
 * and a Label containing the message text.
 */
public class MessageBox extends HBox {

    /** Represents all possible message types. */
    public enum MessageType {
        USER,
        JARVIS,
        ERROR
    }

    @FXML
    private Label textLabel;

    @FXML
    private ImageView displayPicture;

    /**
     * Creates a message box.
     *
     * @param message message text
     * @param image speaker image
     * @param messageType the message type
     */
    public MessageBox(String message, Image image, MessageType messageType) {

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

        switch (messageType) {
            case USER:
                getStyleClass().add("user-message");
                // Crop the user profile picture into a circle for user messages
                double radius = Math.min(
                        displayPicture.getFitWidth(),
                        displayPicture.getFitHeight()
                ) / 2;

                Circle clip = new Circle(radius, radius, radius);
                displayPicture.setClip(clip);
                break;

            case JARVIS:
                getStyleClass().add("jarvis-message");
                // Round the corners of JARVIS image for Jarvis messages
                Rectangle jarvisClip = new Rectangle(
                        displayPicture.getFitWidth(),
                        displayPicture.getFitHeight()
                );

                jarvisClip.setArcWidth(15);
                jarvisClip.setArcHeight(15);

                displayPicture.setClip(jarvisClip);

                flip();
                break;

            case ERROR:
                getStyleClass().add("error-message");
                // Round the corners of JARVIS image for error messages
                Rectangle errorClip = new Rectangle(
                        displayPicture.getFitWidth(),
                        displayPicture.getFitHeight()
                );

                errorClip.setArcWidth(15);
                errorClip.setArcHeight(15);

                displayPicture.setClip(errorClip);

                flip();
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown message type: " + messageType
                );
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

        textLabel.getStyleClass().add("reply-label");
    }
}
