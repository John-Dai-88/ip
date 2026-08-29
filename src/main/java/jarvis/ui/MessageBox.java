package jarvis.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Test
 */
public class MessageBox extends HBox {

    private Label textLabel;
    private ImageView displayPicture;

    /**
     * Test
     *
     * @param message
     * @param image
     * @param isUser
     */
    public MessageBox(String message, Image image, boolean isUser) {
        textLabel = new Label(message);
        textLabel.setWrapText(true);
        textLabel.setMaxWidth(350);
        textLabel.setMinHeight(40);
        textLabel.setPadding(new Insets(10, 15, 10, 15));

        displayPicture = new ImageView(image);
        displayPicture.setFitHeight(50);
        displayPicture.setFitWidth(50);
        displayPicture.setPreserveRatio(true);
        displayPicture.setSmooth(true);
        displayPicture.setCache(true);

        this.setSpacing(10);
        this.setPadding(new Insets(5, 10, 5, 10));
        this.setAlignment(Pos.CENTER);
        this.setFillHeight(true);
        this.setMaxWidth(Double.MAX_VALUE);

        if (isUser) {
            // User message - aligned to the right
            this.setAlignment(Pos.CENTER_RIGHT);

            // Style for user message
            textLabel.setStyle("-fx-background-color: #DCF8C6; "
                    + "-fx-background-radius: 15px; "
                    + "-fx-padding: 10px 15px 10px 15px; "
                    + "-fx-font-size: 13px; "
                    + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 2, 0, 0, 1);");

            // User: text first, then image
            this.getChildren().addAll(textLabel, displayPicture);

        } else {
            // Jarvis message - aligned to the left
            this.setAlignment(Pos.CENTER_LEFT);

            // Style for Jarvis message
            textLabel.setStyle("-fx-background-color: #E8E8E8; "
                    + "-fx-background-radius: 15px; "
                    + "-fx-padding: 10px 15px 10px 15px; "
                    + "-fx-font-size: 13px; "
                    + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 2, 0, 0, 1);");

            // Jarvis: image first, then text
            this.getChildren().addAll(displayPicture, textLabel);
        }
    }
}
