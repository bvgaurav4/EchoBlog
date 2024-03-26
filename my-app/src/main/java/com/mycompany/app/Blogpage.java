package com.mycompany.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import javafx.scene.paint.Color;

import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.bson.Document;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class Blogpage extends Application {
    private String email;
    private String password;

    void setemail(String email) {
        this.email = email;
    }

    public JsonArray getmessage(String email, String password) {
        String url = "http://localhost:4567/";
        JsonArray lol = new JsonArray();
        try {
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
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(response.toString());
            if (element.isJsonArray()) {
                lol = element.getAsJsonArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lol;
    }

    public String seearcString(String name) {
        String url = "http://localhost:4567/";
        String lol = "";
        try {
            url = url + "search";
            System.out.println(url);
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            JsonObject obj2 = new JsonObject();
            obj2.addProperty("Email", name);
            String putJsonData = obj2.toString();
            con.setDoOutput(true);
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
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            lol = response.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lol;
    }

    public String blog(String Email) {
        String url = "http://localhost:4567/";
        String lol = "";
        try {
            url = url + "blog?Email=" + Email;
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
            lol = response.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lol;
    }

    public String get_cons(String Email) {
        String url = "http://localhost:4567/";
        String lol = "";
        try {
            url = url + "connections?Email=" + Email;
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
            lol = response.toString();
            System.out.println(lol);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lol;
    }

    @Override
    public void start(Stage primaryStage) {
        String cons = get_cons("Dio@jojo.com");

        JsonArray consArry = new JsonArray();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(cons);
        if (element.isJsonArray()) {
            System.out.println(cons);
            consArry = element.getAsJsonArray();
            System.out.println(consArry);
        }

        VBox blogContainerWrapper = new VBox();
        blogContainerWrapper.setSpacing(20);
        blogContainerWrapper.setPadding(new Insets(20));
        JsonArray blogs = new JsonArray();
        String Email = "";
        String lollol = blog(Email);

        element = parser.parse(lollol);

        Nav l = new Nav(primaryStage, email, "", Email);
        VBox root = new VBox();
        root.setSpacing(50);
        root.getChildren().addAll(l);

        if (element.isJsonArray()) {
            blogs = element.getAsJsonArray();
        }
        for (int i = 0; i < blogs.size(); i++) {
            JsonObject blog = blogs.get(i).getAsJsonObject();
            BlogContainer blogContainer = new BlogContainer();
            blogContainer.setDoc(blog);
            blogContainer.setStage(primaryStage);
            blogContainer.setTitle(blog.get("title").getAsString());
            blogContainer.setContent(blog.get("content").getAsString());
            // blogContainer.setImage("/logo.jpg");
            blogContainer.setPrefWidth(600);
            blogContainerWrapper.getChildren().add(blogContainer);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(blogContainerWrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefWidth(1000);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: rgba(255, 255, 255, 0.5);");
        VBox vbox1 = new VBox(20);
        vbox1.setSpacing(20);
        vbox1.setPadding(new Insets(20));

        ScrollPane scrollPane1 = new ScrollPane();
        scrollPane1.setFitToWidth(true);
        scrollPane1.setFitToHeight(true);
        scrollPane1.setPrefWidth(400);
        scrollPane1.setPadding(new Insets(20));
        scrollPane1.setStyle("-fx-background: transparent; -fx-background-color: rgba(255, 255, 255, 0.5);");

        for (int i = 0; i < consArry.size(); i++) {
            System.out.println(consArry.get(i));
            container container = new container(1);
            container.setTitle("DIO");
            container.setContent(consArry.get(i).getAsJsonObject().get("target").getAsString());
            container.setStage(primaryStage);

            vbox1.getChildren().add(container);
        }
        scrollPane1.setContent(vbox1);
        HBox hbox = new HBox(scrollPane1);
        hbox.setSpacing(50);
        root.getChildren().add(hbox);
        hbox.getChildren().add(scrollPane);
        VBox searcjBox_super = new VBox();
        VBox searcjBox = new VBox();
        searcjBox.setSpacing(20);
        searcjBox.setPadding(new Insets(20));
        ScrollPane scrollPane2 = new ScrollPane();
        scrollPane2.setPadding(new Insets(20));
        searcjBox.setSpacing(20);

        for (int i = 0; i < 5; i++) {
            container container = new container(2);
            container.setTitle("DIO");
            container.setContent("DIO");
            container.setStage(primaryStage);
            searcjBox.getChildren().add(container);
        }

        scrollPane2.setContent(searcjBox);
        scrollPane2.setStyle("-fx-background:transparent; -fx-background-color: rgba(255, 255, 255, 0.5);");

        TextField search = new TextField("Search");
        search.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;-fx-fill: #8F00FF;");
        Button button = new Button("search");
        button.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;-fx-fill: #8F00FF;");
        button.setOnAction(e -> {
            String name = search.getText();
            String seaString = seearcString(name);
            List<JsonObject> searchArray = new ArrayList();
            JsonArray searchArray1 = new JsonArray();
            JsonParser parser1 = new JsonParser();
            JsonElement element1 = parser1.parse(seaString);
            if (element1.isJsonArray()) {
                searchArray1 = element1.getAsJsonArray();
            }
            for (int i = 0; i < searchArray1.size(); i++) {
                searchArray.add(searchArray1.get(i).getAsJsonObject());
            }
            searcjBox.getChildren().clear();
            System.out.println(seaString);
            System.out.println(searchArray);
            for (JsonObject document : searchArray) {
                JsonObject obj = new JsonObject();

                container container = new container(2);
                container.setTitle(document.get("Name").getAsString());
                container.setContent(document.get("Email").getAsString());
                container.setStage(primaryStage);
                searcjBox.getChildren().add(container);
            }
        });
        HBox s_hbox = new HBox();
        s_hbox.setSpacing(20);
        s_hbox.setAlignment(Pos.CENTER);
        s_hbox.getChildren().add(search);
        s_hbox.getChildren().add(button);
        searcjBox_super.getChildren().add(s_hbox);
        searcjBox_super.getChildren().add(scrollPane2);
        hbox.setId("gridpane");
        hbox.getChildren().add(searcjBox_super);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(root);
        Scene scene = new Scene(stackPane, 1920, 1080, Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setTitle("Blog Containers with Scrollbar");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
