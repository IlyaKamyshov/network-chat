package org.example.server;

import java.io.PrintWriter;
import java.net.Socket;

public class User {

    private Socket clientSocket;
    private PrintWriter msg;
    private String name;

    public User (Socket clientSocket, PrintWriter msg) {
        this.clientSocket = clientSocket;
        this.msg = msg;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName (){
        return name;
    }

    public void sendMsg(String message) {
        msg.println(message);
        msg.flush();
    }

}
