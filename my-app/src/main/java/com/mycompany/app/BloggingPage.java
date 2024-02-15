import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BloggingPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Blogging Page");

        TextField titleField = new TextField();
        titleField.setPromptText("Enter your blog title");

        TextArea contentArea = new TextArea();
        contentArea.setPromptText("Enter your blog content");

        Button submitButton = new Button("Submit Post");
        submitButton.setOnAction(e -> {
            String title = titleField.getText();
            String content = contentArea.getText();

            // Here you can handle the submission of the blog post.
            // For example, you can save the title and content to a database.
            System.out.println("Title: " + title);
            System.out.println("Content: " + content);
        });

        VBox vbox = new VBox(titleField, contentArea, submitButton);
        Scene scene = new Scene(vbox, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}