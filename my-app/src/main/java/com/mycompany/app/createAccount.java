import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.layout.StackPane;

import java.io.File;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.OutputStream;

import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import org.bson.Document;
import org.bson.json.JsonParseException;

public class createAccount extends Application {
    public boolean sending(String email, String password, String username, String mobile, String profilepic) {
        // This constructor is only used to create a new instance of the class
        String url = "http://localhost:4567/";
        boolean lol = false;
        try{
        URL uurl = new URL(url+"create");
        HttpURLConnection con = (HttpURLConnection) uurl.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type","application/json");
        JsonObject obj = new JsonObject();
        obj.addProperty("Email", email);
        obj.addProperty("LoginId", password);
        obj.addProperty("Username", username);
        obj.addProperty("ProfilePic", "profilepic");
        obj.addProperty("Mobile", mobile);
        
        String putJsonData = obj.toString();
        con.setDoOutput(true);  
        OutputStream os = con.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(putJsonData);
        writer.flush();
        writer.close();
        os.close();

        int responseCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + putJsonData);
        System.out.println("Response Code : " + responseCode);  
        BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
        lol =response.toString().equals("true");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lol;
    }
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Creating account for My Application");

        StackPane root = new StackPane();

        

        Label nameLabel = new Label("Email :");
        TextField nameInput = new TextField();
        TextField Username = new TextField();
        Label nameLabel2 = new Label("Username :");
        Label profilpic = new Label("Profile Picture :");

        Label passLabel = new Label("Password:");
        PasswordField passInput = new PasswordField();
        Button button1 = new Button("Create Account");
        Label phoneLabel = new Label("Phone Number:");
        TextField phoneInput = new TextField();
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100); // Set the width of the ImageView to 100
        imageView.setFitHeight(100); // Set the height of the ImageView to 100
        imageView.setPreserveRatio(true);
        Circle circleClip = new Circle(50, 50, 50); // Centered at (50, 50) with radius 50
        imageView.setClip(circleClip);
        Button selectImageButton = new Button("Select Image");
        selectImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
            }
        });
        button1.setOnAction(e -> {
            System.out.println("Email: " + nameInput.getText());
            System.out.println("Username: " + Username.getText());
            System.out.println("Password: " + passInput.getText());
            boolean lol = sending(nameInput.getText(), passInput.getText(), Username.getText(), phoneInput.getText(), "profilepic");
            System.out.println(lol);
            if(lol == true) {
                login myapp = new login();
                try {
                    myapp.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}