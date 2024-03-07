package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private static final Map<Integer, User> usersList = new HashMap<>();

    public static void start() {

        try (ServerSocket serverSocket = new ServerSocket(ReadConfig.getPort())) {
            Logger.log("Server started");

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> {
                        try (PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {
                            User user = new User(clientSocket, writer);
                            usersList.put(clientSocket.getPort(), user);
                            Logger.log(user + " connected.");
                            waitAndSend(clientSocket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                clientSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void waitAndSend(Socket clientSocket) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            boolean hasName = false;
            User user = usersList.get(clientSocket.getPort());
            while (true) {
                if (reader.ready()) {
                    String msg = reader.readLine();
                    if (msg.equalsIgnoreCase("/exit")) {
                        clientExit(clientSocket, user);
                        break;
                    }
                    if (!hasName) {
                        user.setName(msg);
                        Logger.log(user + " has nick: " + user.getName());
                        hasName = true;
                        sendMessage("New user connected: " + user.getName(), clientSocket);
                    } else {
                        sendMessage(user.getName() + ": " + msg, clientSocket);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void sendMessage(String msg, Socket clientSocket) {
        Logger.log(msg);
        for (Map.Entry<Integer, User> entry : usersList.entrySet()) {
            if (entry.getKey() != clientSocket.getPort()) {
                entry.getValue().sendMsg(msg);
                Logger.log("Message sent to " + entry.getValue().getName());
            }
        }
    }

    public static void clientExit(Socket clientSocket, User user) {
        try {
            usersList.remove(clientSocket.getPort(), user);
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Logger.log(user + " disconnected.");
        sendMessage("User " + user.getName() + " left the chat", clientSocket);
    }
}


