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
import android.os.Environment;
import android.widget.Toast;

public class UserInfoUtils {

    public static boolean saveInfo(Context context, String username, String pwd) throws Exception {
        //[1]���洢���ļ�����
        String result = username + "#" + pwd;
        //[2]����file��ָ������Ҫ�����ݴ洢��λ��
//        File file = new File("/mnt/sdcard/info.txt");
        //����һ�����ͨ����������ʵ��
        String path = Environment.getExternalStorageDirectory().getPath();
        File file = new File(path, "infoo.txt");
        
        FileOutputStream fos = new FileOutputStream(file);      //ʹ��������Ҳ���Ի�ȡ����/�������FileOutputStream fos = context.openFile("infoo.txt", 0);
        fos.write(result.getBytes());
        fos.close();
        return true;
    }
    
    public static Map<String, String> readInfo(Context context) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
//        File file = new File("/mnt/sdcard/info.txt");
        //����һ�����ͨ����������ʵ��
        String path = Environment.getExternalStorageDirectory().getPath();
        File file = new File(path, "infoo.txt");
        
        FileInputStream fis = new FileInputStream(file);      //ʹ��������Ҳ���Ի�ȡ����/�������FileOutputStream fis = context.openFile("infoo.txt");
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
