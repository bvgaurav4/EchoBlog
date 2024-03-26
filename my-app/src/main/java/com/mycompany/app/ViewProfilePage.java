package com.mycompany.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ViewProfilePage extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Header
        Label headerLabel = new Label("View Profile");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        HBox headerBox = new HBox(headerLabel);
        headerBox.setAlignment(Pos.CENTER);
        root.setTop(headerBox);

        // Profile Picture
        Image profilePicture = new Image("/logo.jpg");
        ImageView profilePictureView = new ImageView(profilePicture);
        profilePictureView.setFitWidth(150);
        profilePictureView.setFitHeight(150);
        profilePictureView.setPreserveRatio(true);

        // Profile Information
        VBox profileInfoBox = new VBox(10);
        profileInfoBox.setAlignment(Pos.CENTER_LEFT);
        Label nameLabel = new Label("John Doe");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        Label emailLabel = new Label("john.doe@example.com");
        Label phoneLabel = new Label("(123) 456-7890");

        profileInfoBox.getChildren().addAll(nameLabel, emailLabel, phoneLabel);

        // Combine Profile Picture and Information
        HBox profileBox = new HBox(20);
        profileBox.setAlignment(Pos.CENTER);
        profileBox.getChildren().addAll(profilePictureView, profileInfoBox);

        root.setCenter(profileBox);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("View Profile");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}