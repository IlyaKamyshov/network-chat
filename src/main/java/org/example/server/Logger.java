package org.example.server;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import static org.example.server.Main.LOG_PATH;

public class Logger {

    public static void log(String msg) {
        try(PrintWriter writer = new PrintWriter(new FileWriter(LOG_PATH, true))) {
            String msgWrite = "[" + LocalDateTime.now() + "] " + msg + "\n";
            System.out.println(msgWrite);
            writer.write(msgWrite);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
