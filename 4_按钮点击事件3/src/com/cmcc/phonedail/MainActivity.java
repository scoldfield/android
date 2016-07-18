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
import com.cmcc.click3.R;

public class MainActivity extends Activity implements OnClickListener {

    private EditText et_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //����һ������
        setContentView(R.layout.activity_main);
        
        //[1]�ҵ�������Ҫ�Ŀؼ���edittext button
        et_number = (EditText) findViewById(R.id.editText1);
        //[2]�ҵ���ť
        Button btn1 = (Button) findViewById(R.id.button1);
        Button btn2 = (Button) findViewById(R.id.button2);
        Button btn3 = (Button) findViewById(R.id.button3);
        //[3]��button��ť����һ������¼���
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        
    }

    //��ť����¼�3��ʹ����ʵ��OnClickListener�ӿڣ����ڸ����ڲ���дonClick���������õ�ʱ���������ʹ��this
    @Override
    public void onClick(View v) {
        //ͨ������Viewȥ�жϾ����������ĸ���ť
        String number = et_number.getText().toString().trim();

        switch (v.getId()) {
        case R.id.button1:
            dial(number);
            break;
        case R.id.button2:
            dial(number);
            break;
        case R.id.button3:
            dial(number);
            break;

        default:
            break;
        }
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
