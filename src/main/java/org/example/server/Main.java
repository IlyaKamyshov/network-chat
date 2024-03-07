package org.example.server;

public class Main {

    final static String CFG_PATH = "src/main/java/org/example/server/server.cfg";
    final static String LOG_PATH = "src/main/java/org/example/server/server.log";

    public static void main(String[] args) {
        Server.start();
    }

}