package com.Server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.Database.Database;

public class Server {

    private Database db;
    private ServerSocket serverSocket;
    private ExecutorService threadPool; 

    public Server(int port) {
        this.db = new Database();
        db.load();

        this.threadPool = Executors.newFixedThreadPool(10); 

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server Started on port " + port);

            while (true) {
                System.out.println("Waiting for Client...");

                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client Connected!");
                    threadPool.execute(new HandleClient(clientSocket, db));
                } catch (IOException e) {
                    System.out.println("Client Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port: " + port);
        } finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
                threadPool.shutdown();
            } catch (IOException e) {
                System.out.println("Error closing server socket: " + e.getMessage());
            }
        }
    }

    public static void main(String args[]) {
        new Server(5000);
    }
}