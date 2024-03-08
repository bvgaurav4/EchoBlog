package com.mycompany.app;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.IOException; 
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.URL;

import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JButton ;
import javax.swing.JFrame ;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;


import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.animation.FadeTransition;  
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class Messaging extends Application {
    private String myEmail;
    private String sEmail;
    private String Password;
    private Client client;

    public Messaging(String Email,String sEmail, String Password) {
        this.myEmail = Email;
        this.sEmail = sEmail;
        this.Password = Password;
    }
    public Messaging() {
        this.myEmail = "email";
        this.Password = "password";

    }
    public String mgs(String Email,String Email1){
        String url = "http://localhost:4567/message?Email=" + Email + "&Email1=" + Email1;
        String lol = "";
        try{
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
            lol =response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lol;
    }
    @Override
    public void start(Stage primaryStage) {
        VBox blogContainerWrapper = new VBox();
        blogContainerWrapper.setSpacing(20);
        blogContainerWrapper.setPadding(new Insets(20));
        JsonArray blogs = new JsonArray();
        JsonArray messages = new JsonArray();

        String Email = "DIO";
        String Email1 = "JOTARO";
        String lollol= mgs(Email1,Email);
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(lollol);
        Nav l = new Nav(primaryStage, Email, Password, Email1);
        VBox root = new VBox();
        root.setSpacing(50);
        
        
        if(element.isJsonArray()){
            blogs = element.getAsJsonArray();
        }
        System.out.println(blogs);
        lollol= mgs(Email,Email1);
        element = parser.parse(lollol);
        if(element.isJsonArray()){
            messages = element.getAsJsonArray();
        }
        System.out.println(messages);
        blogs.addAll(messages);

        for(int i=0;i<blogs.size();i++)
        {
            JsonObject blog = blogs.get(i).getAsJsonObject();
            MessageContainer bl = new MessageContainer();
            bl.setTitle(blog.get("from").getAsString());
            bl.setContent(blog.get("message").getAsString());
            blogContainerWrapper.getChildren().add(bl);   
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(blogContainerWrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefWidth(1000);
        blogContainerWrapper.heightProperty().addListener(new ChangeListener<Number>() {
            @Override 
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                scrollPane.setVvalue(1.0d);
            }
        });
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
        VBox vbox1 = new VBox();
        vbox1.setSpacing(20);
        vbox1.setPadding(new Insets(20));
        for(int i=0;i<5;i++)
        {
            container container = new container(300, 100, "Hello World", primaryStage);
            vbox1.getChildren().add(container);
        }
        HBox hbox = new HBox(vbox1);
        Button button = new Button("send");
        button.setOnAction(e -> {
            if (client != null) {
                button.setDisable(true);
                client.sendMessage("zipan","Za warudo");
                button.setDisable(false);
            }
        });
            hbox.setSpacing(50);
            hbox.getChildren().add(button);
            root.getChildren().addAll(l);
            root.getChildren().addAll(hbox);
            // hbox.setPadding(new Insets(50));
        hbox.getChildren().add(scrollPane);

        hbox.setId("gridpane");
        Scene scene = new Scene(root, 1920, 1080, Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());


        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your name");
            String name = sc.nextLine();
            System.out.println(name);

            Socket socket = new Socket("localhost", 1234);
            client = new Client(socket, name, (message) -> {
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
        new Thread(()-> {
            client.readMessage();
            client.sendMessage("","");
            while(true)
            {
                client.sendMessage("term","");
            }
        }).start();
        scrollPane.setVvalue(1.0);
        primaryStage.setTitle("Blog Containers with Scrollbar");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
    public static void main(String[] args) {
            launch(args);
    }
}