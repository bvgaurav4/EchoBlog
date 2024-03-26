package com.mycompany.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ProfileViewer extends Application {
    
    // User profile data
    private String username = "Default User";
    private int followersCount = 1000; // Default followers count
    
    @Override
    public void start(Stage primaryStage) {
        // Creating UI elements
        Label usernameLabel = new Label("Username:");
        Label followersLabel = new Label("Followers:");
        Label usernameValueLabel = new Label(username);
        Label followersValueLabel = new Label(String.valueOf(followersCount));
        TextField newUsernameField = new TextField();
        Button updateProfileButton = new Button("Update Profile");
        Button viewFollowersButton = new Button("View Followers");
        
        // Setting up UI layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(followersLabel, 0, 1);
        gridPane.add(usernameValueLabel, 1, 0);
        gridPane.add(followersValueLabel, 1, 1);
        gridPane.add(newUsernameField, 1, 2);
        gridPane.add(updateProfileButton, 1, 3);
        gridPane.add(viewFollowersButton, 1, 4);
        
        // Event handler for Update Profile button
        updateProfileButton.setOnAction(e -> {
            String newUsername = newUsernameField.getText();
            if (!newUsername.isEmpty()) {
                usernameValueLabel.setText(newUsername);
                username = newUsername;
                newUsernameField.clear();
            }
        });
        
        // Event handler for View Followers button
        viewFollowersButton.setOnAction(e -> {
            // Display followers list or navigate to followers page
            System.out.println("Viewing followers of " + username);
        });
        
        // Setting up the scene
        Scene scene = new Scene(gridPane, 300, 200);
        
        // Setting up the stage
        primaryStage.setTitle("Profile Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
