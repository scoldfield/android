package com.cmcc.login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

public class UserInfoUtils {

    public static boolean saveInfo(String username, String pwd) throws Exception {
        //[1]待存储的文件内容
        String result = username + "#" + pwd;
        //[2]创建file类指定我们要把数据存储的位置
        File file = new File("/data/data/com.cmcc.login/info.txt");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(result.getBytes());
        fos.close();
        return true;
    }
    
    public static Map<String, String> readInfo() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        File file = new File("/data/data/com.cmcc.login/info.txt");
        FileInputStream fis = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        String content = reader.readLine();
        
        String[] split = content.split("#");
        String username = split[0];
        String password = split[1];
        map.put("username", username);
        map.put("password", password);
        return map;
    }
}
