package com.mycompany.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class gui extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create UI components
        Label titleLabel = new Label("Beehive");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Label welcomeLabel = new Label("Welcome");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label emailLabel = new Label("Email or username");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();

        CheckBox rememberCheckBox = new CheckBox("Remember");
        Button forgotPasswordButton = new Button("Lost Password?");
        forgotPasswordButton.setStyle("-fx-text-fill: purple; -fx-background-color: transparent;");

        Button loginButton = new Button("Log into your account");
        loginButton.setStyle("-fx-background-color: purple; -fx-text-fill: white;");

        Button signupButton = new Button("Signup is disabled");
        signupButton.setStyle("-fx-text-fill: gray; -fx-background-color: transparent;");

        // Create layout containers for login form
        GridPane loginPane = new GridPane();
        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.add(emailLabel, 0, 0);
        loginPane.add(emailField, 1, 0);
        loginPane.add(passwordLabel, 0, 1);
        loginPane.add(passwordField, 1, 1);

        HBox rememberPane = new HBox(10);
        rememberPane.setAlignment(Pos.CENTER_RIGHT);
        rememberPane.getChildren().addAll(rememberCheckBox, forgotPasswordButton);

        VBox loginFormPane = new VBox(10);
        loginFormPane.setAlignment(Pos.CENTER);
        loginFormPane.getChildren().addAll(welcomeLabel, loginPane, rememberPane, loginButton, signupButton);

        // Create layout containers for marketing section
        Label clubLabel = new Label("Join the club");
        clubLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Label joinLabel = new Label("Join millions of people");
        joinLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Label descriptionLabel = new Label("Connect with others, share experiences, and explore new opportunities.");
        descriptionLabel.setWrapText(true);

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: purple; -fx-text-fill: white;");

        // Image image = new Image("placeholder_image.jpg"); // Replace with your image file path
        // ImageView imageView = new ImageView(image);
        // imageView.setFitWidth(300);
        // imageView.setPreserveRatio(true);

        VBox marketingPane = new VBox(20);
        marketingPane.setAlignment(Pos.CENTER_LEFT);
        marketingPane.setPadding(new Insets(20));
        marketingPane.getChildren().addAll(clubLabel, joinLabel, descriptionLabel, registerButton);

        // Create the root layout container
        BorderPane root = new BorderPane();
        root.setLeft(loginFormPane);
        root.setRight(marketingPane);

        // Create and configure the scene
        Scene scene = new Scene(root, 800, 500);
        // scene.getStylesheets().add("your_stylesheet.css"); // Optional: Add a CSS stylesheet

        // Set the scene and show the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Beehive");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}