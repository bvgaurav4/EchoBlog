package com.mycompany.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Login extends Application {
    public boolean sending(String email, String password) {
        String url = "http://localhost:4567/";
        boolean lol = false;
        try {
            url = url + "login?Email=" + email + "&LoginId=" + password;
            System.out.println(url);
            @SuppressWarnings("deprecation")
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            lol = response.toString().equals("true");
            System.out.println(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lol;
    }

    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Join the club");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Label welcomeLabel = new Label("Welcome");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label emailLabel = new Label("Email or username");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();

        Button forgotPasswordLabel = new Button("Lost Password?");
        forgotPasswordLabel.setTextFill(Color.PURPLE);

        Button register = new Button("Create an account");
        register.setTextFill(Color.PURPLE);
        register.setOnAction(e -> {
            createAccount stage = new createAccount();
            stage.start(primaryStage);

        });

        Button loginButton = new Button("Log into your account");
        loginButton.setStyle("-fx-background-color: purple; -fx-text-fill: white;");

        GridPane loginPane = new GridPane();
        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.add(emailLabel, 0, 0);
        loginPane.add(emailField, 1, 0);
        loginPane.add(passwordLabel, 0, 1);
        loginPane.add(passwordField, 1, 1);

        VBox loginFormPane = new VBox(10);
        loginFormPane.setMaxHeight(300);
        loginFormPane.setAlignment(Pos.CENTER);
        loginFormPane.getChildren().addAll(welcomeLabel, loginPane, forgotPasswordLabel, loginButton);

        VBox leftPane = new VBox(20);
        leftPane.setAlignment(Pos.CENTER_LEFT);
        leftPane.setMaxHeight(500);
        leftPane.setPadding(new Insets(20));
        leftPane.setStyle("-fx-background-color: purple;");
        leftPane.getChildren().addAll(titleLabel, new Label("Lorem ipsum dolor sit amet, consectetur"),
                new Label("adipiscing elit. Ut elit tellus, luctus."), register);

        HBox root = new HBox(20);
        root.setAlignment(Pos.CENTER);
        root.setMaxHeight(500);
        root.getChildren().addAll(leftPane, loginFormPane);
        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            if (sending(email, password)) {
                Blogpage stage = new Blogpage();
                stage.setemail(email);
                stage.start(primaryStage);
            } else {
                root.getChildren().add(new Label("Not logged in"));
            }
        });

        Scene scene = new Scene(root, 800, 500);
        // scene.getStylesheets().add("style.css");
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Echoblog");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}