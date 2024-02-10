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

public class Client {
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
                                System.out.println("clients :"+clients);
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

// main method
    public static void main(String[] args) throws UnknownHostException, IOException{
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter your name");
    String name = sc.nextLine();
    // URL url = new URL(" https://fa56-101-0-62-94.ngrok-free.app");
    // String host = url.getHost();
    // int port = url.getPort() == -1 ? 80 : url.getPort(); 
    Socket socket = new Socket("localhost", 1234);
    // Socket socket = new Socket(host, port);
    Client client = new Client(socket, name);
    client.readMessage();
    client.sendMessage();
    }

}