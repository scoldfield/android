package com.cmcc.asyncHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//数据流处理工具
public class StreamUtils {

    //将InputStream流转换成字符串
    public static String toString(InputStream in) {
        String res = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while((line = reader.readLine()) != null) {
                res += line;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
