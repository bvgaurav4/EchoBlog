import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Lol extends Pane {

    public Lol() {
        super();

        // Create a colored rectangle
        Rectangle topBar = new Rectangle(0, 0, 2000, 50); // Width is set to 2000 for demonstration
        topBar.setFill(Color.DODGERBLUE);

        // Create buttons for navigation items
        Button homeButton = new Button("Home");
        Button aboutButton = new Button("messages");
        Button contactButton = new Button("");

        // Set styles for the buttons
        homeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        aboutButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        contactButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        homeButton.setOnAction(e -> {
            System.out.println("Home button clicked");
        });
        aboutButton.setOnAction(e -> {
            System.out.println("About button clicked");
        });
        contactButton.setOnAction(e -> {
            System.out.println("Contact button clicked");
        });

        // Add padding to the HBox
        HBox navBar = new HBox(10, homeButton, aboutButton, contactButton);
        navBar.setPadding(new Insets(10, 10, 10, 10)); // Add padding

        // Add the colored rectangle and navigation bar to this container
        getChildren().addAll(topBar, navBar);
    }
}
