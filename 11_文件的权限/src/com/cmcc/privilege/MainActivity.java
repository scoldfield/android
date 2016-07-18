package com.cmcc.privilege;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    //点击生成一个私有文件
    public void click1(View v) {
        /*
         * 我的写法
        try {
            File file = new File("/data/data/com.cmcc.privilege/private.txt");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write("private".getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        
        /*
         * 老师的写法
         */
        try {
            FileOutputStream fos = this.openFileOutput("private.txt", MODE_PRIVATE);
            fos.write("private".getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void click2(View v) {
        try {
            FileOutputStream fos = this.openFileOutput("append.txt", MODE_APPEND);
            fos.write("append".getBytes());
            fos.close();
            Toast.makeText(this, "文件创建成功！", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void click3(View v) {
        try {
            FileOutputStream fos = this.openFileOutput("read.txt", MODE_WORLD_READABLE);
            fos.write("read".getBytes());
            fos.close();
            Toast.makeText(this, "文件创建成功！", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void click4(View v) {
        try {
            FileOutputStream fos = this.openFileOutput("write.txt", MODE_WORLD_WRITEABLE);
            fos.write("write".getBytes());
            fos.close();
            Toast.makeText(this, "文件创建成功！", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void click5(View v) {
        try {
            File file = new File("/data/data/com.cmcc.privilege/files/private.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String content = br.readLine();
            Toast.makeText(this, content, Toast.LENGTH_LONG).show();
            br.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
