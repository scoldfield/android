package com.cmcc.thread;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private EditText et;
    private TextView tv;
    
    Handler handler = new Handler() {
        //该方法是在主线程中执行
        public void handleMessage(Message msg) {
            String content = (String) msg.obj;
            tv.setText(content);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        et = (EditText) findViewById(R.id.et);
        tv = (TextView) findViewById(R.id.tv);
        
    }
    
    public void click(View v) {
        
        /*
         * 通过开启新的线程来实现访问网络
         */
        Thread thread = new Thread() {
            public void run() {
                try {
                    String path = et.getText().toString().trim();
                    
                    if(path != null && !"".equals(path)) {
                        URL url = new URL(path);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(5000);
                        int code = conn.getResponseCode();
                        if(code == 200) {
                            InputStream in = conn.getInputStream();
                            String content = StreamTools.readStream(in);
                            
//                            tv.setText(content);      //子线程无法直接操作主线程的控件
                            
                            //将子线程获取的数据存储到Message对象中
                            Message msg = new Message();
                            msg.obj = content;
                            
                            //将消息对象传递到Handler对象中
                            handler.sendMessage(msg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
        };
        
        thread.start();
    }
}
