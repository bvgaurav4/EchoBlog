package com.mycompany.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javafx.geometry.Pos;
import javafx.scene.Scene;

public class Nav extends Pane {
    private void creating_blog(String email, String title, String content, String image) {
        String url = "http://localhost:4567/";
        try {
            URL uurl = new URL(url + "post_blog");
            HttpURLConnection con = (HttpURLConnection) uurl.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            JsonObject obj = new JsonObject();
            obj.addProperty("email", email);
            obj.addProperty("title", title);
            obj.addProperty("content", content);
            obj.addProperty("image", image);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPopUp(Stage primaryStage) {
        Label popupLabel = new Label("This is a pop-up window");
        Button closeButton = new Button("Discard");
        Button createButton = new Button("Create");

        VBox popupLayout = new VBox(10);
        popupLayout.getChildren().addAll(popupLabel, closeButton);
        popupLayout.setAlignment(Pos.CENTER);

        Label popupLabel1 = new Label("title");
        Label popupLabel2 = new Label("content");

        TextField title = new TextField();
        TextField content = new TextField();

        HBox popupLayout1 = new HBox(10);
        popupLayout1.getChildren().addAll(popupLabel1, title);
        popupLayout1.setAlignment(Pos.CENTER);

        HBox popupLayout2 = new HBox(10);
        popupLayout2.getChildren().addAll(popupLabel2, content);
        popupLayout2.setAlignment(Pos.CENTER);

        HBox popupLayout4 = new HBox(10);
        popupLayout4.getChildren().addAll(closeButton, createButton);
        popupLayout4.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        Button selectImageButton = new Button("Select Image");
        selectImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
            }
        });
        HBox popupLayout5 = new HBox(10);
        popupLayout5.getChildren().addAll(imageView, selectImageButton);
        popupLayout5.setAlignment(Pos.CENTER);

        VBox popupLayout3 = new VBox(10);
        popupLayout3.getChildren().addAll(popupLayout1, popupLayout2, popupLayout5, popupLayout4);
        popupLayout3.setAlignment(Pos.CENTER);

        StackPane overlayPane = new StackPane();
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7)");
        overlayPane.getChildren().add(popupLayout3);
        overlayPane.setAlignment(Pos.CENTER);

        Scene scene = primaryStage.getScene();
        StackPane root = (StackPane) scene.getRoot();
        root.getChildren().add(overlayPane);

        createButton.setOnAction(e -> {
            creating_blog("Dio@jojo.com", title.getText(), content.getText(), "image");
            root.getChildren().remove(overlayPane);
        });

        closeButton.setOnAction(e -> root.getChildren().remove(overlayPane));
    }

    public Nav(Stage primaryStage, String email1, String password1, String email2) {
        super();
        Rectangle topBar = new Rectangle(0, 0, 2000, 100);
        HBox.setHgrow(topBar, Priority.ALWAYS);
        topBar.setFill(Color.web("#3F51B5"));

        Button homeButton = new Button("Home");
        Button aboutButton = new Button("Profile");
        Button contactButton = new Button("create new blog");

        homeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        aboutButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        contactButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        homeButton.setOnAction(e -> {
            Blogpage l = new Blogpage();
            l.start(primaryStage);
        });
        aboutButton.setOnAction(e -> {
            Messaging l = new Messaging(email1, email2, password1);
            l.start(primaryStage);
        });
        contactButton.setOnAction(e -> {
            showPopUp(primaryStage);
        });

        HBox navBar = new HBox(10, homeButton, aboutButton, contactButton);
        navBar.setPadding(new Insets(10, 10, 10, 10));

        getChildren().addAll(topBar, navBar);
    }
}
