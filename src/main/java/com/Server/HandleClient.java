package com.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import com.Commands.Protocol;
import com.Database.Database; 

public class HandleClient implements Runnable {

    private Socket clientSocket;
    private Database db; 

    private BufferedReader in = null;
    private PrintWriter out = null; 
 
    private static final HashMap<String, com.Commands.Commands> commandMap = new HashMap<>();

    static {
        commandMap.put("GET", new com.Commands.GetCommand());
        commandMap.put("SET", new com.Commands.SetCommand());
        commandMap.put("DEL", new com.Commands.DelCommand());
        commandMap.put("EXISTS", new com.Commands.ExistsCommand());
        commandMap.put("SIZE", new com.Commands.SizeCommand());
        commandMap.put("SAVE", new com.Commands.SaveCommand());
        commandMap.put("LOAD", new com.Commands.LoadCommand());

    }


    public HandleClient (Socket socket, Database db) {
        this.db = db; 
        this.clientSocket = socket; 
    }

    @Override
    public void run () {
        System.out.println("Thread ID " + Thread.currentThread().threadId() + " is connected to DB: " + System.identityHashCode(db));

        try {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error initializing I/O streams: " + e.getMessage());
            return;
        }
        
        String message = "";
        try {
            while (true) {
                message = in.readLine();
                System.out.println("Client sent: " + message);
                // Stop if client says "$!$"
                if (message == null || message.equals("$!$")) {
                    break;
                }
                
                String[] parts = message.split(" ");
                String commandName = parts[0].toUpperCase();
                com.Commands.Commands command = commandMap.get(commandName);
                String response;

                if (command != null) {
                    response = command.execute(db, parts);
                } else {
                    response = Protocol.error("ERROR: Unknown command.");
                }
                out.print(response);
                out.flush();
            }
        } catch (IOException i) {
            System.out.println(i);
        }
        try {
            System.out.println("Closing connection");
            clientSocket.close();
            in.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    
}