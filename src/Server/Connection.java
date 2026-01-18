package Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*; 

public class Connection {
    private Socket s = null;
    private BufferedReader socketInput = null; 
    private PrintWriter socketOutput = null; 
    private BufferedReader consoleInput = null;
    //private DataInputStream in = null;
    //private DataOutput out = null;
    
    public Connection(String ipString, int port) {
        try {
            s = new Socket(ipString, port);
            socketInput = new BufferedReader(new InputStreamReader(s.getInputStream())); 
            socketOutput = new PrintWriter(s.getOutputStream());
            consoleInput = new BufferedReader(new InputStreamReader(System.in)); 
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
            System.exit(-1); 
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1); 
        }
        String stream = ""; 
        //$!$ temperory stream ender
        while(true) {
            try{
                stream = consoleInput.readLine();
                if (stream.equals("$!$")) {
                    socketOutput.println(stream); 
                    socketOutput.flush();
                    break;
                }
                socketOutput.println(stream); 
                socketOutput.flush();
                String response = socketInput.readLine();
                if(response == null || response == "") {
                    System.out.println("No response from server.");
                    continue; 
                }
                System.out.println("Server: " + response);
            } catch (IOException i) {
                System.out.println(i);
            }
        }

        try {
            socketInput.close();
            socketOutput.close(); 
            consoleInput.close();
            s.close(); 
        }
        catch (IOException i) {
            System.out.println(i);
        }
    }


    
    public static void main(String[] args) {
        Connection c = new Connection("127.0.0.1", 5000); 
    }
    
}
