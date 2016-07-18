package com.cmcc.htmlSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamTools {

    public static String readStream(InputStream in) {
        String result = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while((line = reader.readLine()) != null) {
                result += line;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
