package com.cmcc.multiActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //[3.1]����ʱ����activity_result.xml����
        setContentView(R.layout.activity_result);
        
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        TextView tv_sex = (TextView) findViewById(R.id.tv_sex);
        TextView tv_res = (TextView) findViewById(R.id.tv_res);
        
        //[4.1]��ȡIntent����
        Intent intent = getIntent();
        //[4.2]��ȡ��һ��activity���ݹ���������
        String name = intent.getStringExtra("name");
        int sex = intent.getIntExtra("sex", 0);   //0��ʾĬ��ֵ
        String result = intent.getStringExtra("result");
        
        tv_name.setText(name);
        tv_res.setText(result);
        switch (sex) {
        case 1:
            tv_sex.setText("��");
            break;
        case 2:
            tv_sex.setText("Ů");
            break;
        case 3:
            tv_sex.setText("����");
            break;
        }
    }

}
