package com.cmcc.handler2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tv = (TextView) findViewById(R.id.tv);
        
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            
            //postDelayed()�������ӳ�ִ�С���Ҫִ�еĶ����ŵ����β�Runnable()�����е�run()�����м���
            @Override
            public void run() {
                tv.setText("��������");
            }
        }, 3000);
    }
    
}
