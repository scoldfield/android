package com.cmcc.httpClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import com.cmcc.httpClient.R;
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

    // get方式提交数据
    public void click1(View v) {
        username = et_name.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();

        new Thread() {
            private String line;

            private String res;

            public void run() {

                try {
                    String path = "http://192.168.6.118:8080/32_0_LoginServlet/LoginServlet?username="
                            + username + "&password=" + pwd;

                    // 通过HttpClient接口来获取响应
                    HttpResponse response = new DefaultHttpClient()
                            .execute(new HttpGet(path));
                    int code = response.getStatusLine().getStatusCode();

                    if (code == 200) {
                        InputStream in = response.getEntity().getContent();

                        String content = StreamUtils.toString(in);
                        showToast(content);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    // post方式提交数据
    public void click2(View v) {
        username = et_name.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();

        new Thread() {
            private String line;

            private String res;

            public void run() {

                try {
                    String path = "http://192.168.6.118:8080/32_0_LoginServlet/LoginServlet";

                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(path);

                    // 设置post方式传递的数据
                    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                    BasicNameValuePair nameValuePair = new BasicNameValuePair(
                            "username", username);
                    BasicNameValuePair pwdValuePair = new BasicNameValuePair(
                            "password", pwd);
                    parameters.add(nameValuePair);
                    parameters.add(pwdValuePair);
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
                            parameters);
                    post.setEntity(entity);

                    HttpResponse response = client.execute(post);

                    int code = response.getStatusLine().getStatusCode();
                    if (code == 200) {
                        InputStream in = response.getEntity().getContent();

                        String content = StreamUtils.toString(in);
                        showToast(content);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    public void showToast(final String content) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), content, 0).show();
            }
        });
    }
}
