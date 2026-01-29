package Server;
import java.io.*;
import java.net.*;
import java.util.HashMap; 
import Commands.Protocol; 
import Database.Database;

public class Server {
    
    private Database db; 

    private Socket s = null; 
    private ServerSocket server = null;
    private BufferedReader in = null;
    private PrintWriter out = null;  

    private static final HashMap<String, Commands.Commands> commandMap = new HashMap<>();

    static {
        commandMap.put("GET", new Commands.GetCommand());
        commandMap.put("SET", new Commands.SetCommand());
        commandMap.put("DEL", new Commands.DelCommand());
        commandMap.put("EXISTS", new Commands.ExistsCommand());
        commandMap.put("SIZE", new Commands.SizeCommand());
        
    }

    public Server (int port) {
        this.db = new Database();
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
                // //test communication
                // if(message.equals("TEST COMM")) {
                //     out.println("TEST COMM RECEIVED");
                //     out.flush();
                // } else {
                //     out.println("Message received");
                //     out.flush();
                // }
                String[] parts = message.split(" ");
                String commandName = parts[0].toUpperCase();
                Commands.Commands command = commandMap.get(commandName);
                String response;

                if (command != null) {
                    response = command.execute(db, parts);
                } else {
                    response = Protocol.error("ERROR: Unknown command.");
                }
                out.println(response);
                out.flush();
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