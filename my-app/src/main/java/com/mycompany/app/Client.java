import java.io.IOException; // libraries 
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JButton ;
import javax.swing.JFrame ;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;


import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Client {
     // private classes for the clien
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

            Scanner sc = new Scanner(System.in);

            while(socket.isConnected()){
                String messageToSend = sc.nextLine();
                obj.addProperty("name", name);
                obj.addProperty("message", messageToSend);
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
                    System.out.println(buffReader);
                    Gson gson = new Gson();

                    msfFromGroupChat = buffReader.readLine();
                    JsonObject jsonElement = gson.fromJson(msfFromGroupChat, JsonObject.class);
                    JsonObject test2 ;
                    if(jsonElement.get("name").getAsString().equals("Server")){
                        if (jsonElement.isJsonObject()) {
                            JsonObject jsonObject = jsonElement.getAsJsonObject();
                            // Handle JSON object
                        } else if (jsonElement.isJsonPrimitive()) {
                            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
                            System.out.println(jsonPrimitive);
                            // Handle JSON primitive
                        } else {
                            // Handle other types (e.g., JsonArray, JsonNull)
                            System.out.println(jsonElement);
                        }
                        // test2 = test.get("message").getAsJsonObject();
                        System.out.println(jsonElement.get("message").getAsString());
                        System.out.println("who do u want to text to");
                        // System.out.println(test2);
                        continue;
                    }
                    else{
                        System.out.println(msfFromGroupChat);
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
    Socket socket = new Socket("localhost", 1234);
    Client client = new Client(socket, name);
    client.readMessage();
    client.sendMessage();
    }

}