package org.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {

    public static void start() throws IOException {

        AtomicBoolean exit = new AtomicBoolean(false);

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String name = NickName.nickName(consoleReader);

        Socket clientSocket = new Socket(ReadConfig.getAddress(), ReadConfig.getPort());
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        new Thread(() -> {
            while (!exit.get()) {
                try {
                    if (reader.ready()) {
                        String msgFromServer = reader.readLine();
                        System.out.println(msgFromServer);
                        Logger.log(msgFromServer);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                reader.close();
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            writer.println(name);
            writer.flush();
            System.out.println("Welcome in our perfect private chat, " + name + "! To leave the chat type /exit.");
            while (true) {
                try {
                    if (consoleReader.ready()) {
                        String msg = consoleReader.readLine();
                        if (msg.equalsIgnoreCase("/exit")) {
                            writer.println(msg);
                            writer.close();
                            exit.set(true);
                            break;
                        }
                        writer.println(msg);
                        writer.flush();
                        Logger.log(msg);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

}
