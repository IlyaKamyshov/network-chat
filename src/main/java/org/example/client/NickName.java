package org.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;

public class NickName {

    public static String nickName(BufferedReader nameReader) throws IOException {
        Random random = new Random();
        System.out.print("Enter Your name: ");
        String name = nameReader.readLine();
        if (name.equals("")) {
            name = "user#" + random.ints(97, 122)
                    .limit(10)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        }
        return name;
    }

}
