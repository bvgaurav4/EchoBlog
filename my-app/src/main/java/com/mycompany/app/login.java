import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class login extends Application {
    public boolean sending(String email, String password) {
        // This constructor is only used to create a new instance of the class
        String url = "http://localhost:4567/";
        boolean lol = false;
        try{
        url = url + "login?Email=" + email + "&LoginId=" + password;
        System.out.println(url);
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
        lol =response.toString().equals("true");
        System.out.println(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lol;
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Login Form");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(100, 100, 100, 100));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        Label nameLabel = new Label("Email:");
        TextField nameInput = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passInput = new PasswordField();
        Button createButton = new Button("Create Account");
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            System.out.println("Username: " + nameInput.getText());
            System.out.println("Password: " + passInput.getText());
            boolean lol = sending(nameInput.getText(), passInput.getText());
            System.out.println(lol);
            // Create a new Stage for the second window
            // Stage secondStage = new Stage();
            if(lol == true) {
            // Messaging myapp = new Messaging();
            pages myapp = new pages();

            try {
                // Call the start method of the second window with the new Stage
                // myapp.start(primaryStage,nameInput.getText(),passInput.getText());
                myapp.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            }
        });
        createButton.setOnAction(e -> {
            createAccount myapp = new createAccount();
            try {
                myapp.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }        });
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameInput, 1, 0);
        gridPane.add(passLabel, 0, 1);
        gridPane.add(passInput, 1, 1);
        gridPane.add(loginButton, 1, 2);
        gridPane.add(createButton, 1, 3);
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}