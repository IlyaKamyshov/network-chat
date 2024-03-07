package org.example.client;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import static org.example.client.Main.LOG_PATH;

public class Logger {

    public static void log(String msg) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_PATH, true))) {
            writer.write("[" + LocalDateTime.now() + "] " + msg + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


