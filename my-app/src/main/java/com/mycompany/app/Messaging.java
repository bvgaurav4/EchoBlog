package com.mycompany.app;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.beans.value.ChangeListener;

import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.URL;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;

import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;

import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

import java.net.HttpURLConnection;

public class Messaging extends Application {
    @SuppressWarnings("unused")
    private String myEmail;
    @SuppressWarnings("unused")
    private String sEmail;
    private String Password;
    private Client client;

    public Messaging(String Email, String sEmail, String Password) {
        this.myEmail = Email;
        this.sEmail = sEmail;
        this.Password = Password;
    }

    public Messaging() {
        this.myEmail = "email";
        this.Password = "password";

    }

    @SuppressWarnings("deprecation")
    private String sending_messages(String Email, String Email1, String message) {
        String url = "http://localhost:4567/sending";
        JsonObject lol = new JsonObject();
        lol.addProperty("source", Email);
        lol.addProperty("target", Email1);
        lol.addProperty("message", message);
        lol.addProperty("timestamp", LocalDateTime.now().toString());
        System.out.println(lol);
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
            out.write(lol.toString());
            out.flush();
            out.close();
            int responseCode = con.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }

    private String mgs(String Email, String Email1) {
        String url = "http://localhost:4567/message?Email=" + Email + "&Email1=" + Email1;
        String lol = "";
        try {
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
            lol = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lol;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void start(Stage primaryStage) {
        StackPane root2Pane = new StackPane();
        VBox blogContainerWrapper = new VBox();
        blogContainerWrapper.setSpacing(20);
        blogContainerWrapper.setPadding(new Insets(20));
        JsonArray blogs = new JsonArray();
        JsonArray messages = new JsonArray();

        HBox sendingBox = new HBox();
        sendingBox.setSpacing(20);
        sendingBox.setPadding(new Insets(20));
        sendingBox.setStyle("-fx-background: transparent; -fx-background-color: rgba(255, 255, 255, 0.5);");
        sendingBox.setAlignment(Pos.CENTER_LEFT);

        javafx.scene.control.TextField messageField = new javafx.scene.control.TextField();
        messageField.setText("");

        Button sendButton = new Button("Send");
        sendButton.setPrefWidth(50);
        sendButton.setPrefHeight(25);
        sendButton.setStyle("-fx-font-size: 18px;");

        sendingBox.getChildren().add(messageField);
        sendingBox.getChildren().add(sendButton);

        VBox messaging = new VBox();
        messaging.setSpacing(20);
        messaging.setPadding(new Insets(20));
        messaging.setStyle("-fx-background: transparent; -fx-background-color: rgba(255, 255, 255, 0.5);");
        messaging.setAlignment(Pos.CENTER_LEFT);

        String Email = "DIO";
        String Email1 = "JOTARO";
        String lollol = mgs(Email1, Email);
        @SuppressWarnings("unused")
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(lollol);
        Nav l = new Nav(primaryStage, Email, Password, Email1);
        VBox root = new VBox();
        root.setSpacing(50);

        if (element.isJsonArray()) {
            blogs = element.getAsJsonArray();
        }
        System.out.println(blogs);
        lollol = mgs(Email, Email1);
        element = parser.parse(lollol);
        if (element.isJsonArray()) {
            messages = element.getAsJsonArray();
        }
        System.out.println(messages);
        blogs.addAll(messages);

        for (int i = 0; i < blogs.size(); i++) {
            JsonObject blog = blogs.get(i).getAsJsonObject();
            MessageContainer bl = new MessageContainer();
            bl.setTitle("lol");
            bl.setContent("lol");
            blogContainerWrapper.getChildren().add(bl);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(blogContainerWrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(1000);
        blogContainerWrapper.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
                    Number newSceneHeight) {
                scrollPane.setVvalue(1.0d);
            }
        });
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: rgba(255, 255, 255, 0.5);");

        messaging.getChildren().add(scrollPane);
        messaging.getChildren().add(sendingBox);

        VBox vbox1 = new VBox();
        vbox1.setSpacing(20);
        vbox1.setPadding(new Insets(20));
        for (int i = 0; i < 5; i++) {
            container container = new container(2);
            container.setTitle("DIO");
            container.setStage(primaryStage);
            container.setContent("DIO");
            vbox1.getChildren().add(container);
        }
        HBox hbox = new HBox(vbox1);
        sendButton.setOnAction(e -> {
            if (client != null) {
                sendButton.setDisable(true);
                if (messageField.getText().length() > 0) {
                    MessageContainer bl = new MessageContainer();
                    bl.setTitle("You");
                    bl.setContent(messageField.getText());
                    blogContainerWrapper.getChildren().add(bl);
                    String mf = sending_messages(Email, Email1, messageField.getText());
                    System.out.println(mf);
                    if (mf.equals("false")) {
                        System.out.println("ur offline");
                    }
                    client.sendMessage(Email1, messageField.getText());
                    messageField.clear();
                }
                sendButton.setDisable(false);
            }
        });
        hbox.setSpacing(50);
        hbox.getChildren().add(messaging);
        root.getChildren().addAll(l);
        root.getChildren().addAll(hbox);

        hbox.setId("gridpane");
        root2Pane.getChildren().add(root);
        Scene scene = new Scene(root2Pane, 1920, 1080, Color.TRANSPARENT);
        // scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        try {
            Socket socket = new Socket("localhost", 1234);
            client = new Client(socket, Email, (message) -> {
                Platform.runLater(() -> {
                    System.out.println("Message received: " + message);
                    FadeTransition fade = new FadeTransition();
                    BlogContainer bl = new BlogContainer();
                    bl.setTitle("Title");
                    bl.setContent(message);
                    fade.setDuration(javafx.util.Duration.millis(5000));
                    fade.setNode(bl);
                    blogContainerWrapper.getChildren().add(bl);

                    blogContainerWrapper.applyCss();
                    blogContainerWrapper.layout();

                    fade.play();
                    scrollPane.setVvalue(1.0);
                });
            });
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            client.readMessage();
            client.sendMessage("", "");
            while (true) {
                client.sendMessage("term", "");
            }
        }).start();
        scrollPane.setVvalue(1.0);
        primaryStage.setTitle("Blog Containers with Scrollbar");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}