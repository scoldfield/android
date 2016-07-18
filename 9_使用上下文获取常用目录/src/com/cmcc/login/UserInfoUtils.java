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

    public static boolean saveInfo(Context context, String username, String pwd) throws Exception {
        //[1]待存储的文件内容
        String result = username + "#" + pwd;
        //[2]创建file类指定我们要把数据存储的位置
        //File file = new File("/data/data/com.cmcc.login/info.txt");
        String path = context.getFilesDir().getPath();
        File file = new File(path, "info.txt");
        
        //FileOutputStream fos = new FileOutputStream(file);
        //[3]FileOutputStream也可以由Context获取
        FileOutputStream fos = context.openFileOutput("infoo.txt", 0);  //会自动在软件安装目录新建一个files文件夹，并将文件存储进去
        fos.write(result.getBytes());
        fos.close();
        return true;
    }
    
    public static Map<String, String> readInfo(Context context) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        //File file = new File("/data/data/com.cmcc.login/info.txt");
        String path = context.getFilesDir().getPath();
        File file = new File(path, "info.txt");
        //FileInputStream fis = new FileInputStream(file);
        //可以通过Context获取FileOutputStream
        FileInputStream fis = context.openFileInput("info.txt");
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
