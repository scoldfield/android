package com.cmcc.phonedail;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.cmcc.click2.R;

public class MainActivity extends Activity {

    private EditText et_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //����һ������
        setContentView(R.layout.activity_main);
        
        //[1]�ҵ�������Ҫ�Ŀؼ���edittext button
        et_number = (EditText) findViewById(R.id.editText1);
        //[2]�ҵ���ť
        Button btn_call = (Button) findViewById(R.id.button1);
        //[3]��button��ť����һ������¼���
        //��ť����¼�2�������ڲ��෽��
        btn_call.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String num = et_number.getText().toString().trim();
                if("".equals(num)) {
                    Toast.makeText(MainActivity.this, "number����Ϊ��", Toast.LENGTH_LONG).show();
                    return;
                }
                dial(num);
            }
            
        });
        
    }
    
    //��绰����
    public void dial(String number) {
        //[5]���в���绰��ʹ��"��ͼ"intent
        Intent intent = new Intent();       //����һ����ͼ���󡣴�  è����
        //[5.1]Ϊ��ͼ���ö���
        intent.setAction(Intent.ACTION_CALL);   //���ô�绰�Ķ���
        //[5.2]����Ҫ���������
        intent.setData(Uri.parse("tel:" + number));    //"tel:"�Ǵ�绰�Ĺ̶�д��
        
        //[6]������ͼ
        startActivity(intent);
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
