import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class gui3 extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Text area for writing a blog post
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        root.setCenter(textArea);

        // Button to submit a blog post
        Button submitButton = new Button("Submit");

        // ListView to display existing blog posts
        ListView<String> listView = new ListView<>();
        listView.setPrefWidth(200);
        listView.getItems().addAll(
                "Blog Post 1",
                "Blog Post 2",
                "Blog Post 3"
        );

        // Add blog post to the list when submit button is clicked
        submitButton.setOnAction(event -> {
            String post = textArea.getText();
            if (!post.isEmpty()) {
                listView.getItems().add(post);
                textArea.clear();
            }
        });

        // Layout for submit button and list view
        HBox bottomBox = new HBox(10);
        bottomBox.getChildren().addAll(submitButton, listView);
        root.setBottom(bottomBox);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blogging Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
