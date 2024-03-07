package org.example.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.example.client.Main.CFG_PATH;

public class ReadConfig {
    public static String getAddress() {
        String path = new File(CFG_PATH).getAbsolutePath();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            Properties properties = new Properties();
            properties.load(bufferedReader);
            return properties.getProperty("address");
        } catch (IOException e) {
            System.out.println("Failed to read configuration file.");
            return null;
        }
    }
    public static int getPort() {

        String path = new File(CFG_PATH).getAbsolutePath();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            Properties properties = new Properties();
            properties.load(bufferedReader);
            return Integer.parseInt(properties.getProperty("port"));
        } catch (IOException e) {
            System.out.println("Failed to read configuration file.");
            return 8000;
        }

    }

}
