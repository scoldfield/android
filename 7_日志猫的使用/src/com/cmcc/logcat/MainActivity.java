package com.cmcc.logcat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    private static final String tag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*
         * logcat日志猫简介
         * 1、日志猫分为verbose、info、debug、warn、error五种
         * 2、安卓提供了Log类来打印日志
         */
        Log.v(tag, "我是v级别");    //tag是在LogCat中Tag列，一般都写成自己的类名，这样方便找出自己类中打印的日志
        Log.v(tag, "我是info级别");
        Log.v(tag, "我是debug级别");
        Log.v(tag, "我是warn级别");
        Log.v(tag, "我是error级别");
        System.out.println("我是syso输出的信息！");
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
