package com.cmcc.runOnUiThread;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private EditText et;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        et = (EditText) findViewById(R.id.et);
        iv = (ImageView) findViewById(R.id.iv);
        
    }
    
    public void click(View v) {
        
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    String path = et.getText().toString().trim();
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    if(code == 200) {
                        InputStream in = conn.getInputStream();
                        final Bitmap bitmap = BitmapFactory.decodeStream(in);
                        
                        //在子线程中可以通过runOnUiThread()方法来修改主线程中的控件
                        runOnUiThread(new Runnable() {
                            
                            //run()方法肯定是运行在UI线程上的
                            @Override
                            public void run() {
                                iv.setImageBitmap(bitmap);  //内部类访问外部变量，需要使用final
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.run();
            }
        };
        
        thread.start();
    }
}
