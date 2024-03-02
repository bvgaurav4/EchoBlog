import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class gui extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox card = new VBox(10); // 10 is the spacing between elements in the VBox
        card.setPadding(new Insets(10)); // 10 is the padding of the VBox

        // Create a rectangle to serve as a background for the card
        Rectangle rect = new Rectangle(200, 200, Color.web("#8F00FF"));
        rect.setArcHeight(105.0);
        rect.setArcWidth(105.0);

        // Create a label for the card
        Label label = new Label("This is a card!");

        // Create a button for the card
        Button button = new Button("Click me!");

        // Add the rectangle and label to the card
        card.getChildren().addAll(rect, label);

        // Create a StackPane to hold the card and button
        StackPane root = new StackPane();
        root.getChildren().addAll(rect, card, button);

        // Set alignment of the button to the center of the rectangle
        StackPane.setMargin(button, new Insets(70, 0, 0, 0)); // Adjust top margin as needed

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Card Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
