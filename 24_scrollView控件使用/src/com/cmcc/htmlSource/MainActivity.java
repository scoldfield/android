package com.cmcc.htmlSource;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.cmcc.scrollView.R;

public class MainActivity extends Activity {

    private EditText et;
    private TextView tv;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        et = (EditText) findViewById(R.id.et);
        tv = (TextView) findViewById(R.id.tv);
        
    }
    
    public void click(View v) {
        try {
            //要加上这两句，才能在主线程中进行有关网络的操作
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            
            String path = et.getText().toString().trim();
            
            if(path != null && !"".equals(path)) {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                //获取服务器返回的状态码
                int code = conn.getResponseCode();
                if(code == 200) {
                    InputStream inputStream = conn.getInputStream();
                    content = StreamTools.readStream(inputStream);
                }
                
                tv.setText(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
