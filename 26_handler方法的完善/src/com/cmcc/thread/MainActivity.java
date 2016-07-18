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
import android.widget.Toast;

import com.cmcc.handler.R;

public class MainActivity extends Activity {

    private final int REQUESTSUCCESS = 0;
    private final int REQUESTFAILURE = 1;
    private final int REQUESTEXCEPTION = 2;
    
    private EditText et;
    private TextView tv;
    
    Handler handler = new Handler() {
        //�÷����������߳���ִ��
        public void handleMessage(Message msg) {
            
            switch (msg.what) {
            case REQUESTSUCCESS:
                String content = (String) msg.obj;
                tv.setText(content);
                break;
            case REQUESTFAILURE:
                //ToastҲ��View�������߳��޷�ֱ�Ӳ���
                Toast.makeText(getApplicationContext(), (String)msg.obj, 0).show();
                break;
            case REQUESTEXCEPTION:
                Toast.makeText(getApplicationContext(), (String)msg.obj, 0).show();
                break;

            default:
                break;
            }
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
                            
                            Message msg = new Message();
                            msg.obj = content;
                            //msg�е�what����������ʶ��msg���Ա�Looper��messageQueue�����ҳ�ָ����msg
                            msg.what = REQUESTSUCCESS;
                            handler.sendMessage(msg);
                        }else {
                            Message msg = new Message();
                            msg.obj = "������æ�����Ժ�...";
                            msg.what = REQUESTFAILURE;
                            handler.sendMessage(msg);
                        }
                    }
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.obj = "�����쳣";
                    msg.what = REQUESTEXCEPTION;
                    handler.sendMessage(msg);
                    
                    e.printStackTrace();
                }
                
            }
        };
        
        thread.start();
    }
}
