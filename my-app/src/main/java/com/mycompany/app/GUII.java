import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GUII extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10); // Vertical gap between rows

        // Create a rectangle to serve as a background for the card
        Rectangle rect = new Rectangle(200, 200, Color.web("#8F00FF"));
        rect.setArcHeight(105.0);
        rect.setArcWidth(105.0);

        // Create a label for the card
        Label label = new Label("This is a card!");

        // Create a button for the card
        Button button = new Button("Click me!");

        // Add elements to the gridPane
        gridPane.add(rect, 0, 0, 2, 1); // column=0, row=0, colspan=2, rowspan=1
        gridPane.add(label, 0, 1, 2, 1); // column=0, row=1, colspan=2, rowspan=1
        gridPane.add(button, 0, 2, 2, 1); // column=0, row=2, colspan=2, rowspan=1

        // Set alignment of elements
        GridPane.setMargin(rect, new Insets(0, 0, 10, 0)); // Bottom margin for rectangle
        GridPane.setMargin(button, new Insets(10, 0, 0, 0)); // Top margin for button

        Scene scene = new Scene(gridPane, 300, 250);

        primaryStage.setTitle("Card Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
