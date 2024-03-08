package com.mycompany.app;

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




class Client {
    public interface MessageReceivedCallback {
        void onMessageReceived(String message);
    }
    private ConcurrentHashMap< String,ClientHandler> clients = new ConcurrentHashMap<>();
    private MessageReceivedCallback callback;

    private Socket socket;
    private BufferedReader buffReader;
    private BufferedWriter buffWriter;
    public String name;

    public Client(Socket socket, String name, MessageReceivedCallback callback){
        try{
                this.socket = socket;
                this.buffWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.name = name;
                this.callback = callback;
            
        }catch (IOException e){
            closeAll(socket, buffReader, buffWriter);
        }
    }
public void sendMessage(String lol, String lol1){
    try{
        JsonObject obj = new JsonObject();
        if(lol.equals("")){
            buffWriter.write(name);
            buffWriter.newLine();
            buffWriter.flush();
        }
        Gson gson = new Gson();
        Scanner sc = new Scanner(System.in);

        System.out.println("to?");
        String which = "";
        which = lol;    
        if(lol.equals("term")){
            which = sc.nextLine();
        }
        obj.addProperty("name", name);
        obj.addProperty("to", which);
        System.out.println("message?");
        String messageToSend = "";
        messageToSend = lol1;
        if(lol1.equals("")){
            messageToSend = sc.nextLine();
        }
        
        obj.addProperty("message", messageToSend);
        System.out.println(clients.get(which));
        buffWriter.write(obj.toString());
        buffWriter.newLine();
        buffWriter.flush();

    } catch(IOException e){
        closeAll(socket, buffReader, buffWriter);
    }
}
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
                            if (name.equals("Server")) {
                                jsonElement = parser.parse(obj.get("message").toString());
                                try {
                                    clients = gson.fromJson(obj.get("message").getAsString(), ConcurrentHashMap.class);
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                System.out.println("Server :" + obj.get("message").getAsString());
                                callback.onMessageReceived(obj.toString()); // Fix: Pass a VBox object instead of a JsonObject
                            } else {
                                System.out.println(obj.get("name") + obj.toString());
                                callback.onMessageReceived(obj.toString()); 
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