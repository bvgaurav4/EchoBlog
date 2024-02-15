import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class pages extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("My Application");

        // Button for the first scene
        Button button1 = new Button("Go to Scene 2");
        StackPane layout1 = new StackPane();
        layout1.getChildren().add(button1);
        Scene scene1 = new Scene(layout1, 300, 250);

        // Button for the second scene
        Button button2 = new Button("Go to Scene 1");
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        Scene scene2 = new Scene(layout2, 300, 250);

        // When button1 is clicked, switch to scene2
        button1.setOnAction(e -> primaryStage.setScene(scene2));

        // When button2 is clicked, switch to scene1
        button2.setOnAction(e -> primaryStage.setScene(scene1));

        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}