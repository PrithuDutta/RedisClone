package Server;
import java.io.*;
import java.net.*; 

public class Server {
    

    private Socket s = null; 
    private ServerSocket server = null;
    private BufferedReader in = null;
    private PrintWriter out = null;  

    public Server (int port) {

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Server Started"); 
            System.out.println("Waiting for Client Connection...");
            s = server.accept();
            System.out.println("Client Accepted");

            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);


        } catch (IOException e) {
            System.out.println(e);
            System.exit(-1); 
        }

        String message = "";
        while(true) {
            try {
                message = in.readLine();
                System.out.println("Client sent: " + message);
                // Stop if client says "$!$"
                if (message.equals("$!$") || message == null) {
                    break;
                }
                //test communication
                if(message.equals("TEST COMM")) {
                    out.println("TEST COMM RECEIVED");
                    out.flush();
                } else {
                    out.println("Message received");
                    out.flush();
                }
            } catch (IOException i) {
                System.out.println(i);
            }

        }
        try{
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