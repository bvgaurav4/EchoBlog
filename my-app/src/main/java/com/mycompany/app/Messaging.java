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


import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

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

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Text area for writing a blog post
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        root.setCenter(textArea);
        new Thread(()->        {try {
            // Scanner sc = new Scanner(System.in);
            // System.out.println("Enter your name");
            String name = Email;
            System.out.println(name);
            // URL url = new URL(" https://fa56-101-0-62-94.ngrok-free.app");
            // String host = url.getHost();
            // int port = url.getPort() == -1 ? 80 : url.getPort(); 
            Socket socket = new Socket("localhost", 1234);
            // Socket socket = new Socket(host, port);
            Client client = new Client(socket, name);
            client.readMessage();
            client.sendMessage();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }}).start();
        // Button to submit a blog post
        Button submitButton = new Button("Submit");

        // ListView to display existing blog posts
        ListView<String> listView = new ListView<>();
        listView.setPrefWidth(200);
        listView.getItems().addAll(
                "Blog Post 1",
                "Blog Post 2",
                "Blog Post 3"
        );

        // Add blog post to the list when submit button is clicked
        submitButton.setOnAction(event -> {
            String post = textArea.getText();
            if (!post.isEmpty()) {
                listView.getItems().add(post);
                textArea.clear();
            }
        });

        // Layout for submit button and list view
        HBox bottomBox = new HBox(10);
        bottomBox.getChildren().addAll(submitButton, listView);
        root.setBottom(bottomBox);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blogging Application");
        primaryStage.setFullScreen(true);
        primaryStage.show();

    }

    public static void main(String[] args) {

            new Thread(()->launch(args)).start();

    }
}
 class Client {
     // private classes for the clien
    private ConcurrentHashMap< String,ClientHandler> clients = new ConcurrentHashMap<>();
    private Socket socket;
    private BufferedReader buffReader;
    private BufferedWriter buffWriter;
    public String name;

    public Client(Socket socket, String name){
        try{
              // Constructors of all the private classes
                this.socket = socket;
                this.buffWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.name = name;

            
        }catch (IOException e){
            closeAll(socket, buffReader, buffWriter);
        }
    }
// method to send messages using thread
    public void sendMessage(){
        try{
            JsonObject obj = new JsonObject();
            buffWriter.write(name);
            buffWriter.newLine();
            buffWriter.flush();
            Gson gson = new Gson();

            Scanner sc = new Scanner(System.in);

            while(socket.isConnected()){
                System.out.println("to?");
                String which = sc.nextLine();
                obj.addProperty("name", name);
                obj.addProperty("to", which);
                System.out.println("message?");
                String messageToSend = sc.nextLine();
                obj.addProperty("message", messageToSend);
                System.out.println(clients.get(which));
                buffWriter.write(obj.toString());
                buffWriter.newLine();
                buffWriter.flush();

            }
        } catch(IOException e){
            closeAll(socket, buffReader, buffWriter);
        }
    }
 // method to read messages using thread
    public void readMessage(){
        new Thread( new Runnable() {

            @Override
            public void run() {
                String msfFromGroupChat;

                while(socket.isConnected()){
                    try{
                        Gson gson = new Gson();
                        msfFromGroupChat = buffReader.readLine();
                        if(msfFromGroupChat == null){
                            closeAll(socket, buffReader, buffWriter);
                            break;
                        }
                        JsonParser parser = new JsonParser();
                        JsonElement jsonElement = parser.parse(msfFromGroupChat);
                        if(jsonElement.isJsonObject())
                        {
                            JsonObject obj = gson.fromJson(msfFromGroupChat, JsonObject.class);
                            String name = obj.get("name").getAsString();
                            if(name.equals("Server")){
                                jsonElement=parser.parse(obj.get("message").toString());
                                try{
                                    clients= gson.fromJson(obj.get("message").getAsString(), ConcurrentHashMap.class);
                                }catch( Exception e){
                                    System.out.println(e);
                                }
                                System.out.println("Server :"+obj.get("message").getAsString());
                            }else{
                                System.out.println(obj.toString());
                            }
                        }else if (jsonElement.isJsonPrimitive())
                        {
                            System.out.println("its a primitive");
                        }else {
                                System.out.println("The string is not recognized as a JSON type.");
                        }
                    } catch (IOException e){
                        closeAll(socket, buffReader, buffWriter);
                    }
                } 
            }
            
        }).start();
    }
// method to close everything in the socket 
    public void closeAll(Socket socket, BufferedReader buffReader, BufferedWriter buffWriter){
        try{
            if(buffReader!= null){
                buffReader.close();
            }
            if(buffWriter != null){
                buffWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.getStackTrace();
        }
    }
}