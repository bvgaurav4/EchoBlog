package com.mycompany.app;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class container extends BorderPane {
    private VBox textContainer;
    private Text titleLabel;
    private Text contentText;
    private int id;
    private Stage stage;
    private String email;
    private ImageView imageView;

    private void showPopUp(Stage primaryStage) {
        // Create pop-up content

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.setId("gridpane");
        Label nameLabel = new Label("Username:");
        GridPane.setConstraints(nameLabel, 0, 0);

        TextField nameInput = new TextField();
        nameInput.setPromptText("username");
        GridPane.setConstraints(nameInput, 1, 0);

        Label passLabel = new Label("Password:");
        GridPane.setConstraints(passLabel, 0, 1);

        PasswordField passInput = new PasswordField();
        passInput.setPromptText("password");
        GridPane.setConstraints(passInput, 1, 1);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 2);

        TextField emailInput = new TextField();
        emailInput.setPromptText("email");
        GridPane.setConstraints(emailInput, 1, 2);

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
        grid.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, emailLabel, emailInput, profilePicLabel,
                profilePicInput, profilePicButton, submitButton);

        // Profile Picture Button
        profilePicButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                profilePicInput.setText(file.getAbsolutePath());
            }
        });
        Button closeButton = new Button("Close");

        VBox popupLayout = new VBox(10);
        popupLayout.getChildren().addAll(grid, closeButton);
        popupLayout.setAlignment(Pos.CENTER);

        StackPane overlayPane = new StackPane();
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7)");
        overlayPane.getChildren().add(popupLayout);
        overlayPane.setAlignment(Pos.CENTER);
        grid.setAlignment(Pos.CENTER);

        Scene scene = primaryStage.getScene();
        StackPane root = (StackPane) scene.getRoot();
        root.getChildren().add(overlayPane);
        closeButton.setOnAction(e -> root.getChildren().remove(overlayPane));
    }

    public container(int lolol) {

        super();
        this.id = lolol;
        textContainer = new VBox();
        textContainer.setSpacing(5);
        textContainer.setMaxWidth(500);

        Button button = new Button("message");
        Button commnet = new Button("view profile");

        commnet.setOnAction(e -> {
            showPopUp(stage);
        });

        Button reqButton = new Button("send request");
        button.setOnAction(e -> {
            System.out.println("message");
            Messaging my_stage = new Messaging(email, email, "lol");
            my_stage.start(stage);
        });
        reqButton.setOnAction(e -> {
            System.out.println("send request");

        });

        titleLabel = new Text();
        titleLabel.setWrappingWidth(500);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;-fx-fill: #8F00FF;");

        contentText = new Text();
        contentText.setWrappingWidth(500);

        textContainer.getChildren().addAll(titleLabel, contentText);
        if (id == 1) {
            textContainer.getChildren().addAll(button, commnet);
        } else {
            textContainer.getChildren().addAll(commnet, reqButton);
        }
        imageView = new ImageView();
        imageView.setFitWidth(20);
        imageView.setPreserveRatio(true);

        setPadding(new Insets(10));
        setCenter(textContainer);
        setLeft(imageView);

        Rectangle borderRect = new Rectangle();
        borderRect.setFill(null);
        borderRect.setStroke(Color.web("#8F00FF", 0.7));
        borderRect.setStrokeWidth(1.5);
        setBorder(Border.EMPTY);

        setMaxWidth(Double.MAX_VALUE);
    }

    public void setTitle(String title) {
        titleLabel.setText(title);

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setContent(String content) {
        contentText.setText(content);
    }

    public void setImage(String imageUrl) {
        Image image = new Image(imageUrl);
        imageView.setImage(image);
    }
}
