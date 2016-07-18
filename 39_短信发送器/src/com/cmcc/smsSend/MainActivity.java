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
        //���ز���
        setContentView(R.layout.activity_main);
        
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_cont = (EditText) findViewById(R.id.et_cont);
        
    }
    
    //��ͨѶ¼��Ӻ��빦��
    public void add(View v) {
        Intent intent = new Intent(getApplicationContext(), PhoneActivity.class);
        //startActivity(intent);
        
        //ע�⣺����ת���µ�ҳ��������Ҫ���µ�ҳ�淵�����ݣ���ô�Ͳ�Ҫ��startActivity()������������startActivityForResult()���������У�1��ʾ������
        startActivityForResult(intent, 1);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if(resultCode == 10) {
            //�绰���뷵��
            String phone = data.getStringExtra("phone");
            et_phone.setText(phone);
        }else {
            //�������ݷ���
            String content = data.getStringExtra("content");
            et_cont.setText(content);
        }
    }
    
    //����ģ����Ź���
    public void insert(View v) {
        Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
        startActivityForResult(intent, 2);
    }
    
    //���Ͷ��Ź��ܡ�����Google��API������ʹ��ϵͳ���Ź���
    public void send(View v) {
        String number = et_phone.getText().toString().trim();
        String content = et_cont.getText().toString().trim();

        SmsManager smsManager = SmsManager.getDefault();
        //����̫�����ֿ�����
        ArrayList<String> divideMessage = smsManager.divideMessage(content);
        for(String div : divideMessage) {
            smsManager.sendTextMessage(number, null, div, null, null);
        }
    }
}
