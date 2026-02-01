package Server;

import java.io.*;
import java.net.*;

import Database.Database;

public class Server {

    private Database db;
    private ServerSocket serverSocket;

    public Server(int port) {
        this.db = new Database();
        db.load();

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server Started on port " + port);

            while (true) {
                System.out.println("Waiting for Client...");

                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client Connected!");
                    new Thread(new HandleClient(clientSocket, db)).start();
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
            } catch (IOException e) {
                System.out.println("Error closing server socket: " + e.getMessage());
            }
        }
    }

    public static void main(String args[]) {
        new Server(5000);
    }
}