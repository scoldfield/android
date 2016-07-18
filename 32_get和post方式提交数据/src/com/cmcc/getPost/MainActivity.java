package com.cmcc.getPost;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.cmcc.getPost.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

    //get方式提交数据
    public void click1(View v) {
        username = et_name.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();

        new Thread() {
            private String line;
            private String res;

            public void run() {

                try {
                    String path = "http://192.168.6.118:8080/32_0_LoginServlet/LoginServlet?username=" + username + "&password=" + pwd;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(3000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream in = conn.getInputStream();
                        res = StreamUtils.toString(in);
                        
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), res, 0).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }
    
    //post方式提交数据
    public void click2(View v) {
        username = et_name.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();

        new Thread() {
            private String line;
            private String res;

            public void run() {

                try {
                    String path = "http://192.168.6.118:8080/32_0_LoginServlet/LoginServlet";
                    //post方法需要传递的数据
                    String data = "username=" + username + "&password=" + pwd;  
                    
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(3000);
                    
                    //一：设置提交的方式为POST
                    conn.setRequestMethod("POST");
                    //二：post比get方法需要多加的两个header参数
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("Content-Length", data.length()+"");
                    //三：设置启动向Servlet传递参数服务，并将需要传递给Servlet的参数以输出流的方式传递过去
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(data.getBytes());
                    
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream in = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(in));
                        line = "";
                        res = "";
                        while ((line = reader.readLine()) != null) {
                            res += line;
                        }
                        reader.close();

                        //解决安卓的中文乱码问题。如果服务器端没有处理乱码问题，那么安卓端应该如下处理
//                        res = new String(res.getBytes(), "gbk");
                        
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), res, 0).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }
}
