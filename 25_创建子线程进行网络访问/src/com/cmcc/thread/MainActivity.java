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
        //�÷����������߳���ִ��
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
         * ͨ�������µ��߳���ʵ�ַ�������
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
                            
//                            tv.setText(content);      //���߳��޷�ֱ�Ӳ������̵߳Ŀؼ�
                            
                            //�����̻߳�ȡ�����ݴ洢��Message������
                            Message msg = new Message();
                            msg.obj = content;
                            
                            //����Ϣ���󴫵ݵ�Handler������
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
