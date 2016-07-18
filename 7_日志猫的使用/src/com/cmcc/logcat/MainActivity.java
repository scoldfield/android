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
         * logcat��־è���
         * 1����־è��Ϊverbose��info��debug��warn��error����
         * 2����׿�ṩ��Log������ӡ��־
         */
        Log.v(tag, "����v����");    //tag����LogCat��Tag�У�һ�㶼д���Լ������������������ҳ��Լ����д�ӡ����־
        Log.v(tag, "����info����");
        Log.v(tag, "����debug����");
        Log.v(tag, "����warn����");
        Log.v(tag, "����error����");
        System.out.println("����syso�������Ϣ��");
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
