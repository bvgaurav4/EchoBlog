import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

public class lol4 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create a StackPane
        StackPane stackPane = new StackPane();
        FlowPane flow = new FlowPane();
        FlowPane flow2 = new FlowPane();


        // Set background image
        stackPane.setStyle("-fx-background-image: url('background.jpg');  ");
        flow.setStyle("-fx-alignment: center;-fx-padding: 10; -fx-spacing: 10; ");
        flow2.setStyle("-fx-alignment: center; -fx-padding: 10; -fx-spacing: 10; ");
        // Create a label
        Label label = new Label("Welcome!");
        Rectangle rect = new Rectangle(300, 300, 300, 300);

        // Create a button
        Button button = new Button("like");
        Button commnet = new Button("comment"); 

        // Add elements to the StackPane
        flow.getChildren().addAll( button, commnet, label);
        flow2.getChildren().addAll( rect);
        stackPane.getChildren().addAll(flow2,flow);

        // Set the scene and stage
        Scene scene = new Scene(stackPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("StackPane Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
