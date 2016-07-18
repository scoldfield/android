package com.cmcc.smsSend;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    private EditText et_phone;
    private EditText et_cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.activity_main);
        
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_cont = (EditText) findViewById(R.id.et_cont);
        
    }
    
    //从通讯录添加号码功能
    public void add(View v) {
        Intent intent = new Intent(getApplicationContext(), PhoneActivity.class);
        //startActivity(intent);
        
        //注意：在跳转到新的页面后，如果还要从新的页面返回数据，那么就不要用startActivity()方法，而是用startActivityForResult()方法。其中，1表示请求码
        startActivityForResult(intent, 1);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if(resultCode == 10) {
            //电话号码返回
            String phone = data.getStringExtra("phone");
            et_phone.setText(phone);
        }else {
            //短信内容返回
            String content = data.getStringExtra("content");
            et_cont.setText(content);
        }
    }
    
    //插入模板短信功能
    public void insert(View v) {
        Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
        startActivityForResult(intent, 2);
    }
    
    //发送短信功能。调用Google的API，而不使用系统短信功能
    public void send(View v) {
        String number = et_phone.getText().toString().trim();
        String content = et_cont.getText().toString().trim();

        SmsManager smsManager = SmsManager.getDefault();
        //内容太长，分开发送
        ArrayList<String> divideMessage = smsManager.divideMessage(content);
        for(String div : divideMessage) {
            smsManager.sendTextMessage(number, null, div, null, null);
        }
    }
}
