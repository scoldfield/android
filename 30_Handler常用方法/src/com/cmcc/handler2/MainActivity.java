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
            
            //postDelayed()方法是延迟执行。将要执行的动作放到其形参Runnable()方法中的run()方法中即可
            @Override
            public void run() {
                tv.setText("哈哈哈哈");
            }
        }, 3000);
    }
    
}
