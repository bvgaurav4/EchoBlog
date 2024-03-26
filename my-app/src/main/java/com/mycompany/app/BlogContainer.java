package com.mycompany.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.net.URL;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class BlogContainer extends BorderPane {
    private JsonObject doc;
    private VBox textContainer;
    private Text titleLabel;
    private Text contentText;
    private String email;
    private ImageView imageView;
    private String blogId;
    private String eemail;
    private Stage prima;

    private void delete_blog() {
        String url = "http://localhost:4567/";
        String path = "delete_blog";

        String data = "blogId=" + blogId;
        try {
            URL url1 = new URL(url + path + data);
            HttpURLConnection con = (HttpURLConnection) url1.openConnection();
            con.setRequestMethod("GET");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
        // connection.setRequestMethod("GET");

    }

    private void showPopUp(Stage primaryStage) {
        // Create pop-up content
        Label popupLabel = new Label("This is a pop-up window");
        Button closeButton = new Button("Close");

        VBox popupLayout = new VBox(10);
        popupLayout.getChildren().addAll(popupLabel, closeButton);
        popupLayout.setAlignment(Pos.CENTER);

        // Overlay the pop-up content over the main scene
        StackPane overlayPane = new StackPane();
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7)");
        overlayPane.getChildren().add(popupLayout);
        overlayPane.setAlignment(Pos.CENTER);

        Scene scene = primaryStage.getScene();
        StackPane root = (StackPane) scene.getRoot();
        root.getChildren().add(overlayPane);
        closeButton.setOnAction(e -> root.getChildren().remove(overlayPane));
    }

    private void like_blog() {
        String url = "http://localhost:4567/";
        System.out.println(doc);
        String path = "like" + "?" + "Email" + "=" + doc.get("email").getAsString() + "&" + "title" + "="
                + titleLabel.getText();
        try {
            URL url1 = new URL(url + path);
            HttpURLConnection con = (HttpURLConnection) url1.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            JsonObject obj = new JsonObject();
            obj.addProperty("likes", "Jotaro@jojo.com");
            con.setDoOutput(true);
            con.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        } catch (Exception e) {

            e.printStackTrace();
        }
        System.out.println("Like blog");
    }

    private void comment_blog() {
        System.out.println("Comment blog");

    }

    public BlogContainer() {
        super();

        textContainer = new VBox();
        textContainer.setSpacing(5);
        textContainer.setMaxWidth(500);

        Button button = new Button("like");
        button.setOnAction(e -> like_blog());
        Button commnet = new Button("comment");
        commnet.setOnAction(e -> {
            showPopUp(prima);
        });
        titleLabel = new Text();
        titleLabel.setWrappingWidth(500);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;-fx-fill: #8F00FF;");

        contentText = new Text();
        contentText.setWrappingWidth(500);

        textContainer.getChildren().addAll(titleLabel, contentText);
        textContainer.getChildren().addAll(button, commnet);
        imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        setPadding(new Insets(10));
        setCenter(textContainer);
        setLeft(imageView);

        Rectangle borderRect = new Rectangle();
        borderRect.setFill(null);
        borderRect.setStroke(Color.web("#8F00FF", 0.7));
        borderRect.setStrokeWidth(1.5);
        setBorder(Border.EMPTY);

        setMaxWidth(Double.MAX_VALUE);
    }

    public void setTitle(String title) {
        titleLabel.setText(title);

    }

    public void setContent(String content) {
        contentText.setText(content);
    }

    public void setImage(String imageUrl) {
        Image image = new Image(imageUrl);
        imageView.setImage(image);
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public void setStage(Stage stage) {
        this.prima = stage;
    }

    public void setDoc(JsonObject Doc) {
        this.doc = Doc;
    }

}
