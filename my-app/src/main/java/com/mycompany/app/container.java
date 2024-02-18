import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class container extends StackPane {
    
    private Rectangle background;
    private Label text;
    
    public container(double width, double height, String labelText , Stage primaryStage) {

        // Create a rectangle as the background
        background = new Rectangle(width, height, Color.web("#8F00FF",0.7));
        background.setArcWidth(10);
        background.setArcHeight(10);
        Button button = new Button("message");
        // Create a label with the provided text
        text = new Label(labelText);
        text.setTextFill(Color.BLACK);
        
        // Add the rectangle and label to the container
        getChildren().addAll(background, text, button);
        
        // Set padding for the label
        StackPane.setMargin(text, new Insets(10)); // Padding around the text
        button.setOnAction(e -> {
            System.out.println("Home button clicked");
            pages myapp = new pages();
            try {
                myapp.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
    }
    
    // Additional methods can be added here for customization
    
}
