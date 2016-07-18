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
        
        //[1]�ҵ�������Ҫ�Ŀؼ�
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        cb_ischeck = (CheckBox) findViewById(R.id.cb_ischeck);
        
        
        //[2]��config.xml�ļ��а����ǹ��ĵ�����ȡ������Ȼ����ʾ��editText�ռ���
        SharedPreferences sp = getSharedPreferences("config", 0);
        String name = sp.getString("name", "");
        String pwd = sp.getString("password", "");
        
        //[3]��name��pwd���õ�editText��
        et_username.setText(name);
        et_password.setText(pwd);
        
        Toast.makeText(this, "username = "+name+", password="+pwd, Toast.LENGTH_LONG).show();
        
        //TODO  ������"TODO"��������ǵģ���������ȥ�Է����߸�ʲô�����һ�£�����Ҫ��ʲô���ɵ�������
    }
    
    public void login(View view) throws Exception {
        //[2.1]��ȡ�û���������
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        
        //[2.2]�ж�username��password�Ƿ�Ϊ��
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "�û�����������Ϊ��", Toast.LENGTH_LONG).show();
        }else {
            //[2.3]���е�½���߼�
            System.out.println("���ӷ��������е�½");
            
            //[2.4]���û���������洢����
            if(cb_ischeck.isChecked()) {
                //[2.4]ʹ��SharedPreferencesȥ��������
                
                //[2.4.1]���ȣ��õ�SharedPreferencesʵ�������У�name �������������һ��xml�ļ���mode ģʽ
                SharedPreferences sp = getSharedPreferences("config", 0);
                
                //[2.4.2]��ȡsp�ı༭��
                Editor edit = sp.edit();
                edit.putString("name", username);
                edit.putString("password", password);
                
                //[2.4.3]�ǵð�edit�����ύ
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
