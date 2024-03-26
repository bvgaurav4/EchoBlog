package com.mycompany.app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PopUpWithoutNewWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pop-Up Example");

        // Create a button
        Button button = new Button("Show Pop-Up");
        button.setOnAction(e -> showPopUp(primaryStage));

        // Add button to layout
        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        // Set scene
        primaryStage.setScene(new Scene(layout, 300, 250));
        primaryStage.show();
    }

    // Function to show the pop-up window
    private void showPopUp(Stage primaryStage) {
        // Create pop-up content
        Label popupLabel = new Label("This is a pop-up window");
        Button closeButton = new Button("Close");

        VBox popupLayout = new VBox(10);
        popupLayout.getChildren().addAll(popupLabel, closeButton);
        popupLayout.setAlignment(Pos.CENTER);

        // Overlay the pop-up content over the main scene
        StackPane overlayPane = new StackPane();
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3)"); // Semi-transparent background
        overlayPane.getChildren().add(popupLayout);
        overlayPane.setAlignment(Pos.CENTER);

        Scene scene = primaryStage.getScene();
        StackPane root = (StackPane) scene.getRoot();
        root.getChildren().add(overlayPane);
        closeButton.setOnAction(e -> root.getChildren().remove(overlayPane));
    }
}
