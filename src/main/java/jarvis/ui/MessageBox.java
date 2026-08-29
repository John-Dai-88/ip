package jarvis.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Test
 */
public class MessageBox extends HBox {

    @FXML
    private Label textLabel;

    @FXML
    private ImageView displayPicture;

    /**
     * Test
     *
     * @param message
     * @param image
     * @param isUser
     */
    public MessageBox(String message, Image image, boolean isUser) {
        // Load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MessageBox.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load MessageBox FXML", e);
        }

        // Set data
        textLabel.setText(message);
        displayPicture.setImage(image);

        // Apply styles based on user type
        if (isUser) {
            this.getStyleClass().add("user-message");
            // User: text first, then image (reorder)
            this.getChildren().setAll(textLabel, displayPicture);
        } else {
            this.getStyleClass().add("jarvis-message");
            // Jarvis: image first, then text (reorder)
            this.getChildren().setAll(displayPicture, textLabel);
        }
    }
}
