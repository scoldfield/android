package com.cmcc.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamTools {

    public static String readStream(InputStream in) {
        String result = "";

        try {
            String line = "";
            if(in != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while((line = reader.readLine()) != null) {
                    result += line;
                }
                
                reader.close();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }
        
}
