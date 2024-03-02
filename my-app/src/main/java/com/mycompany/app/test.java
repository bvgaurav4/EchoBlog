import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class test extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Logo
        Image logoImage = new Image(getClass().getResourceAsStream("logo.jpg"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitHeight(40);
        logoImageView.setPreserveRatio(true);

        Label logoLabel = new Label("Your Logo");
        logoLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");

        HBox logoBox = new HBox(logoImageView, logoLabel);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        logoBox.setSpacing(5);

        // Search icon
        Image searchImage = new Image(getClass().getResourceAsStream("def_profile.jpg"));
        ImageView searchImageView = new ImageView(searchImage);
        searchImageView.setFitHeight(20);
        searchImageView.setPreserveRatio(true);

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");

        HBox.setHgrow(searchField, Priority.ALWAYS);

        Lol l = new Lol();
        VBox root1 = new VBox();
        root1.setSpacing(50);
        root1.getChildren().addAll(l);
        

        Button searchButton = new Button("", searchImageView);
        searchButton.setStyle("-fx-background-color: transparent;");

        HBox searchBox = new HBox(searchField, searchButton);
        searchBox.setAlignment(Pos.CENTER_RIGHT);
        searchBox.setSpacing(5);

        // Home button
        Button homeButton = new Button("Home");

        // Message button
        Image messageImage = new Image(getClass().getResourceAsStream("logo.jpg"));
        ImageView messageImageView = new ImageView(messageImage);
        messageImageView.setFitHeight(20);
        messageImageView.setPreserveRatio(true);

        Button messageButton = new Button("", messageImageView);
        messageButton.setStyle("-fx-background-color: transparent;");

        HBox buttonBox = new HBox(homeButton, root1,messageButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setSpacing(10);

        // Navigation bar
        VBox navBar = new VBox(logoBox, searchBox, buttonBox);
        navBar.setStyle("-fx-background-color: #333333;");
        navBar.setPrefHeight(60);
        navBar.setPadding(new javafx.geometry.Insets(10));

        // Main content
        StackPane root = new StackPane();
        root.getChildren().add(navBar);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Navigation Bar Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
