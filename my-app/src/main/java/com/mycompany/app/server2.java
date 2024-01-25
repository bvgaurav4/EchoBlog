import java.net.*;
import java.io.*;

public class server2
{
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    public server2(int port)
    {
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

            in = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));

            String line = "";

            while (!line.equals("Over"))
            {
                try
                {
                    line = in.readUTF();
                    System.out.println(line);
                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        try {
            server2 server = new server2(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}