package com.cmcc.cacheAndFileDir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button bt1 = (Button) findViewById(R.id.bt1);
        Button bt2 = (Button) findViewById(R.id.bt2);
    }
    
    public void click1(View v) {
        try {
            File file = new File(getCacheDir(), "info.txt");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write("cache File".getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void click2(View v) {
        try {
            File file = new File(getFilesDir(), "info.txt");
            FileOutputStream fos;
            fos = new FileOutputStream(file);
            fos.write("haha".getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
