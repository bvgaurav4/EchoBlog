import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

import java.io.File;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.OutputStream;

import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import org.bson.Document;
import org.bson.json.JsonParseException;

public class Profile extends Application {

@Override
public void start(Stage primaryStage) {
    primaryStage.setTitle("Profile");

    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(8);
    grid.setHgap(10);
    grid.setId("gridpane");
    // Name Label
    Label nameLabel = new Label("Username:");
    GridPane.setConstraints(nameLabel, 0, 0);

    // Name Input
    TextField nameInput = new TextField();
    nameInput.setPromptText("username");
    GridPane.setConstraints(nameInput, 1, 0);

    // Password Label
    Label passLabel = new Label("Password:");
    GridPane.setConstraints(passLabel, 0, 1);

    // Password Input
    PasswordField passInput = new PasswordField();
    passInput.setPromptText("password");
    GridPane.setConstraints(passInput, 1, 1);

    // Email Label
    Label emailLabel = new Label("Email:");
    GridPane.setConstraints(emailLabel, 0, 2);

    // Email Input
    TextField emailInput = new TextField();
    emailInput.setPromptText("email");
    GridPane.setConstraints(emailInput, 1, 2);

    // Profile Picture Label
    Label profilePicLabel = new Label("Profile Picture:");
    GridPane.setConstraints(profilePicLabel, 0, 3);

    // Profile Picture Input
    TextField profilePicInput = new TextField();
    profilePicInput.setPromptText("profile picture");
    GridPane.setConstraints(profilePicInput, 1, 3);

    // Profile Picture Button
    Button profilePicButton = new Button("Choose File");
    GridPane.setConstraints(profilePicButton, 2, 3);

    // Submit Button
    Button submitButton = new Button("Save Changes");
    GridPane.setConstraints(submitButton, 1, 4);

    // Add everything to grid
    grid.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, emailLabel, emailInput, profilePicLabel, profilePicInput, profilePicButton, submitButton);

    // Profile Picture Button
    profilePicButton.setOnAction(e -> {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            profilePicInput.setText(file.getAbsolutePath());
        }
    });

        Scene scene = new Scene(grid);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        // scene.getStylesheets().add("/styles.css");
// scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setFullScreen(true);
        primaryStage.show();

}
public static void main(String[] args) {
    launch(args);
}}