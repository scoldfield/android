package com.cmcc.multiActivity;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText et_name;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //[1]����ʱ�Զ����صĲ���
        setContentView(R.layout.activity_main);

        et_name = (EditText) findViewById(R.id.et_name);
        //[2]��ȡRadioGroup�ؼ�
        rg = (RadioGroup) findViewById(R.id.rg);
    
    }
    
    public void click(View v) {
        String name = et_name.getText().toString().trim();
        int rbId = rg.getCheckedRadioButtonId();
        int sex = 0;    //�Ա��ǣ������ж�RadioButton�ؼ��Ƿ�ѡ�У��Լ�ѡ�е����ĸ�        
        
        if(TextUtils.isEmpty(name)) {
            //nameΪ��
            Toast.makeText(getApplicationContext(), "����������", 0).show();
            return;
        }
        
        switch (rbId) {
        case R.id.rb_male:
            sex = 1;
            break;
        case R.id.rb_female:
            sex = 2;
            break;
        case R.id.rb_other:
            sex = 3;
            break;
        }
        
        if(sex == 0) {
            //RadioButtonû�б�ѡ��
            Toast.makeText(getApplicationContext(), "��ѡ���Ա�", 0).show();
            return;
        }
        
        //[3]��ת���ڶ���ҳ�棬��ʾ��ת��ResultActivity.class���ڶ���ҳ���activity
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        //[4]�������ݵ��ڶ���ҳ��
        intent.putExtra("name", name);
        intent.putExtra("sex", sex);
        intent.putExtra("result", getResult());
        
        //[5]������activity
        startActivity(intent);
    }
    
    public String getResult() {
        Random random = new Random();
        int nextInt = random.nextInt(100);
        
        if(nextInt > 60) {
            return "����̫��";
        }else if(nextInt > 40) {
            return "����һ��";
        }else {
            return "����̫��";
        }
    }
}
