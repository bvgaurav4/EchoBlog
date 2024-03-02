import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;


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
import clienthandler.ClientHandler;
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

import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Messaging extends Application {
    private String Email;
    private String Password;
    public Messaging(String Email, String Password) {
        this.Email = Email;
        this.Password = Password;
    }
    public Messaging() {
        this.Email = "email";
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
        String Email = "";
        String Email1 = "email";
        String lollol= mgs(Email1,Email);
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(lollol);

        Lol l = new Lol();
        VBox root = new VBox();
        root.setSpacing(50);
        root.getChildren().addAll(l);


        if(element.isJsonArray()){
            blogs = element.getAsJsonArray();
        }
        for(int i=0;i<blogs.size();i++)
        {
            JsonObject blog = blogs.get(i).getAsJsonObject();
            BlogContainer blogContainer = new BlogContainer();

            blogContainer.setTitle(blog.get("title").getAsString());
            blogContainer.setContent(blog.get("content").getAsString());
            blogContainer.setImage("logo.jpg"); 
            blogContainer.setPrefWidth(600);
            blogContainerWrapper.getChildren().add(blogContainer);
        }
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(blogContainerWrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefWidth(600);
        // scrollPane.setStyle("-fx-background-color: t/ransparent;");
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox vbox1 = new VBox();
        vbox1.setSpacing(20);
        vbox1.setPadding(new Insets(20));
        for(int i=0;i<5;i++)
        {
            container container = new container(200, 100, "Hello World", primaryStage);
            vbox1.getChildren().add(container);
        }
        HBox hbox = new HBox(vbox1);
        Button button = new Button("send");
        // button.setOnAction(e->{
        //     client.sendMessage("zipan","Za warudo");
        // }
        // );
        // hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(50);
        root.getChildren().add(hbox);
        // hbox.setPadding(new Insets(50));
        hbox.getChildren().add(scrollPane);

        hbox.setId("gridpane");
        Scene scene = new Scene(root, 600, 300, Color.TRANSPARENT);
                scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        // new Thread(()->        {
        // }).start();
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your name");
            String name = sc.nextLine();
            System.out.println(name);

            Socket socket = new Socket("localhost", 1234);
            Client client = new Client(socket, name);
            // client.readMessage();
            // client.sendMessage("","");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Blog Containers with Scrollbar");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();

    }

    public static void main(String[] args) {

            new Thread(()->launch(args)).start();

    }
}
