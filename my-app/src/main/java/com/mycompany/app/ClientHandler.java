package clienthandler;

import java.io.IOException;  
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedWriter;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ClientHandler  implements Runnable{
 
    public static ArrayList<ClientHandler>clientHandlers = new ArrayList<>();
    public Socket socket;
    private BufferedReader buffReader;
    public BufferedWriter buffWriter;
    public String name;
    
    public ClientHandler(Socket socket){
          // Constructors of all the private classes
        try{
        this.socket = socket;
        this.buffWriter = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
        this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.name = buffReader.readLine();
        clientHandlers.add(this);
        JsonObject obj = new JsonObject();
        obj.addProperty("name", "Server");
        obj.addProperty("message", name+" has entered in the room");
        boradcastMessage(obj.toString());

    } catch(IOException e){
    closeAll(socket, buffReader, buffWriter);
    }
}
// run method override
    @Override
    public void run() {

        String messageFromClient;

        while(socket.isConnected()){
            try{
                messageFromClient = buffReader.readLine();
                Gson gson = new Gson();
                if(messageFromClient == null){
                    closeAll(socket, buffReader,  buffWriter);
                    break;
                }
                JsonObject jsonObject = new JsonParser().parse(messageFromClient).getAsJsonObject();
                send_specific(jsonObject.toString(), jsonObject.get("to").getAsString());

            } catch(IOException e){
                closeAll(socket, buffReader,  buffWriter);
                break;
            }
        }
    }
    public void boradcastMessage(String messageToSend){
        for(ClientHandler clientHandler: clientHandlers){
            try{
                if(!clientHandler.name.equals(name)){
                    clientHandler.buffWriter.write(messageToSend);
                    clientHandler.buffWriter.newLine();
                    clientHandler.buffWriter.flush();
                }
            } catch(IOException e){
                closeAll(socket,buffReader, buffWriter);

            }
        }
    }
    public void send_specific(String messageToSend, String to){
        for(ClientHandler clientHandler: clientHandlers){
            try{
                String name = clientHandler.name.toString();
                if(name.equals(to)){
                    clientHandler.buffWriter.write(messageToSend);
                    clientHandler.buffWriter.newLine();
                    clientHandler.buffWriter.flush();
                }
            } catch(IOException e){
                closeAll(socket,buffReader, buffWriter);
            }
        }
    }
    // notify if the user left the chat
    public void removeClientHandler(){
        clientHandlers.remove(this);
        JsonObject obj = new JsonObject();
        obj.addProperty("name", "Server");
        obj.addProperty("message", name + " has left the chat");
        boradcastMessage(obj.toString());
        
    }

    public void closeAll(Socket socket, BufferedReader buffReader, BufferedWriter buffWriter){
      
        // handle the removeClient funciton
        removeClientHandler();
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