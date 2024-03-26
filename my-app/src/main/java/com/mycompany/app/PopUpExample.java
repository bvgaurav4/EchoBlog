package com.mycompany.app;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PopUpExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label popUpLabel = new Label("This is a pop-up!");
        popUpLabel.setStyle("-fx-background-color: #FFD700; -fx-padding: 10px; -fx-font-size: 16px;");

        StackPane popUpContainer = new StackPane(popUpLabel);
        popUpContainer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // Semi-transparent background

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), popUpContainer);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Create the main scene
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 400, 300, Color.WHITE);

        // Show the pop-up when the scene is clicked
        root.setOnMouseClicked(event -> {
            root.getChildren().add(popUpContainer);
            fadeIn.play();
        });

        // Hide the pop-up when the container is clicked
        popUpContainer.setOnMouseClicked(event -> {
            fadeIn.setAutoReverse(true);
            fadeIn.play();
            fadeIn.setOnFinished(e -> root.getChildren().remove(popUpContainer));
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}