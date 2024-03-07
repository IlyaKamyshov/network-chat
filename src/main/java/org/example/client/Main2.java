package org.example.client;

import java.io.IOException;

public class Main2 {
    final static String CFG_PATH = "src/main/java/org/example/client/client.cfg";
    final static String LOG_PATH = "src/main/java/org/example/client/client2.log";

    public static void main(String[] args) throws IOException {
        Client.start();
    }

}
