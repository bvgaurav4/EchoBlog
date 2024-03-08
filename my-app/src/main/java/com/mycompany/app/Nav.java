package com.mycompany.app;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Nav extends Pane {

    public Nav(Stage primaryStage, String email1, String password1, String email2) {
        super();

        Rectangle topBar = new Rectangle(0, 0, 2000, 100); 
        HBox.setHgrow(topBar, Priority.ALWAYS);
        topBar.setFill(Color.web("#3F51B5"));

        Button homeButton = new Button("Home");
        Button aboutButton = new Button("Profile");
        Button contactButton = new Button("");


        homeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        aboutButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        contactButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        homeButton.setOnAction(e -> {
            System.out.println("Home button clicked");
        });
        aboutButton.setOnAction(e -> {
            Messaging l = new Messaging(email1,email2,password1);
            l.start(primaryStage);
        });
        contactButton.setOnAction(e -> {
            System.out.println("Contact button clicked");
        });

        HBox navBar = new HBox(10, homeButton, aboutButton, contactButton);
        navBar.setPadding(new Insets(10, 10, 10, 10)); 

        getChildren().addAll(topBar, navBar);
    }
}
