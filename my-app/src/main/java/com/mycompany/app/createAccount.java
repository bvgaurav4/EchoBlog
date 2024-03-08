package com.mycompany.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

import java.io.File;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

import com.google.gson.JsonObject;


public class createAccount extends Application {
    public boolean sending(String email, String password, String username, String mobile, String profilepic) {
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

        primaryStage.setTitle("EchoBlog");

        Label nameLabel = new Label("Email :");
        TextField nameInput = new TextField();
        
        Label nameLabel2 = new Label("Username :");
        TextField Username = new TextField();
        
        Label passLabel = new Label("Password:");        
        PasswordField passInput = new PasswordField();
        
        Button button1 = new Button("Create Account");
        
        Label phoneLabel = new Label("Phone Number:");
        TextField phoneInput = new TextField();
        
        Label profilpic = new Label("Profile Picture :");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100); 
        imageView.setFitHeight(100); 
        imageView.setPreserveRatio(true);
        Circle circleClip = new Circle(50, 50, 50); 
        imageView.setClip(circleClip);
        Button selectImageButton = new Button("Select Image");

        GridPane  createforms = new GridPane();
        
        createforms.setHgap(10);
        createforms.setVgap(10);
        createforms.add(nameLabel, 0, 0);
        createforms.add(nameInput, 1, 0);
        createforms.add(nameLabel2, 0, 1);
        createforms.add(Username, 1, 1);
        createforms.add(passLabel, 0, 2);
        createforms.add(passInput, 1, 2);
        createforms.add(phoneLabel, 0, 3);
        createforms.add(phoneInput, 1, 3);
        createforms.add(profilpic, 0, 4);
        createforms.add(imageView, 0, 5);
        createforms.add(selectImageButton, 1, 4);
        createforms.setAlignment(Pos.CENTER);
        createforms.setPadding(new Insets(10));
        createforms.setMaxHeight(500);

        
        VBox left = new VBox(20);
        left.setMaxHeight(500);
        left.setPadding(new Insets(10));
        left.setStyle("-fx-background-color: #8F00FF;");

        left.setAlignment(Pos.CENTER_LEFT);
        left.getChildren().addAll(new Label("Create ur Echoblog account"),createforms, button1);
        
        VBox right = new VBox(20);
        right.setMaxHeight(500);
        right.setPadding(new Insets(10));
        right.setStyle("-fx-background-color: #D3D3D3;");

        right.setAlignment(Pos.CENTER_RIGHT);
        right.getChildren().addAll(new Label("Lorem ipsum dolor sit amet, consectetur"), new Label("adipiscing elit. Ut elit tellus, luctus."));
        
        
        HBox center = new HBox();
        center.setPadding(new Insets(10));
        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(left, right);


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

            boolean lol = sending(nameInput.getText(), passInput.getText(), Username.getText(), phoneInput.getText(), "profilepic");
            System.out.println(lol);
            if(lol == true) {
                Login myapp = new Login();
                try {
                    myapp.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else{
                
            }
        });


        center.setAlignment(Pos.CENTER);


        Scene scene = new Scene(center, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}