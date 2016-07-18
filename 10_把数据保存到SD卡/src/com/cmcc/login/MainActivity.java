package com.cmcc.login;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.cmcc.context.R;

public class MainActivity extends Activity {

    private EditText et_username;
    private EditText et_password;
    private CheckBox cb_ischeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //[1]找到我们需要的控件
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        cb_ischeck = (CheckBox) findViewById(R.id.cb_ischeck);
        
       //[1.1]读取data/data 下的info.txt
       try {
            Map<String, String> map = UserInfoUtils.readInfo(MainActivity.this);
            if(map != null) {
                et_username.setText(map.get("username"));
                et_password.setText(map.get("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void login(View view) throws Exception {
        //[2.1]获取用户名和密码
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        
        //[2.2]判断username和password是否为空
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "用户名或者密码为空", Toast.LENGTH_LONG).show();
        }else {
            //[2.3]进行登陆的逻辑
            System.out.println("链接服务器进行登陆");
            
            //[2.4]把用户名和密码存储起来
            if(cb_ischeck.isChecked()) {
                //[2.5]将数据保存到sd卡，先要判断sd卡是否可用
                if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    //先判断有sdcard
                    Toast.makeText(getApplicationContext(), "sd卡可用", Toast.LENGTH_LONG).show();
                    
                    boolean res = UserInfoUtils.saveInfo(MainActivity.this, username, password);
                    if(res) {
                        Toast.makeText(MainActivity.this, "存储成功", Toast.LENGTH_LONG);
                    }
                            
                }else {
                    Toast.makeText(getApplicationContext(), "sd卡不可用", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
