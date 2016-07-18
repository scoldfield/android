package com.cmcc.login;

import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
        
        
        //[2]在config.xml文件中把我们关心的数据取出来，然后显示到editText空间上
        SharedPreferences sp = getSharedPreferences("config", 0);
        String name = sp.getString("name", "");
        String pwd = sp.getString("password", "");
        
        //[3]把name和pwd设置到editText上
        et_username.setText(name);
        et_password.setText(pwd);
        
        Toast.makeText(this, "username = "+name+", password="+pwd, Toast.LENGTH_LONG).show();
        
        //TODO  这个标记"TODO"是用来标记的，比如中午去吃饭或者干什么，标记一下，下午要干什么，干到哪里了
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
                //[2.4]使用SharedPreferences去保存数据
                
                //[2.4.1]首先，拿到SharedPreferences实例。其中，name 会帮助我们生成一个xml文件；mode 模式
                SharedPreferences sp = getSharedPreferences("config", 0);
                
                //[2.4.2]获取sp的编辑器
                Editor edit = sp.edit();
                edit.putString("name", username);
                edit.putString("password", password);
                
                //[2.4.3]记得把edit进行提交
                edit.commit();
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
