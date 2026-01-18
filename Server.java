import java.io.*;
import java.net.*; 

public class Server {
    

    private Socket s = null; 
    private ServerSocket server = null;
    private BufferedReader in = null;

    public Server (int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server Started"); 
            System.out.println("Waiting for Client Connection...");

            s = server.accept();
            System.out.println("Client Accepted"); 

            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String message = "";

            // Reads message from client until "Over" is sent
            while ((message = in.readLine()) != null) {
                
                System.out.println("Client sent: " + message);

                // Stop if client says "Over"
                if (message.equals("$!$")) {
                    break;
                }
            }

            System.out.println("Closing connection");
            s.close();
            in.close();
            
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        Server s = new Server(5000);
    }
}