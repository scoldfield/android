package com.cmcc.asyncHttpClient;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import com.cmcc.asyncHttpClient.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText et_name;

    private EditText et_pwd;

    private String username;

    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = (EditText) findViewById(R.id.et_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);

    }

    // get��ʽ�ύ����
    public void click1(View v) {
        username = et_name.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();
        String path = "http://192.168.6.118:8080/32_0_LoginServlet/LoginServlet?username=" + username + "&password=" + pwd;

        //[1]����asynchttpclient
        AsyncHttpClient client = new AsyncHttpClient();
        //[2]post�����������Ӳ���������
        client.get(path, new AsyncHttpResponseHandler() {
            //����ɹ�����������
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                try {
                    Toast.makeText(getApplicationContext(), new String(arg2, "utf-8"), 0).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                    Throwable arg3) {
                // TODO Auto-generated method stub
                
            }
        });
        
    }

    // post��ʽ�ύ����
    public void click2(View v) {
        username = et_name.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();
        String path = "http://192.168.6.118:8080/32_0_LoginServlet/LoginServlet";
        
        //[1]����asynchttpclient
        AsyncHttpClient client = new AsyncHttpClient();
        //[2]׼�������������
        RequestParams params = new RequestParams();
        params.add("username", username);
        params.add("password", pwd);
        //[3]post�����������Ӳ���������
        client.post(path, params, new AsyncHttpResponseHandler() {
            //����ɹ�����������
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                try {
                    Toast.makeText(getApplicationContext(), new String(arg2, "utf-8"), 0).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }                
            }
            
            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                    Throwable arg3) {
                // TODO Auto-generated method stub
                
            }
        });
    }
}
