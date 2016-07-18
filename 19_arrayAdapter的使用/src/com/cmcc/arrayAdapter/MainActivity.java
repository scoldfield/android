package com.cmcc.arrayAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    String data[] = {"����","����","����","����"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //[1]��ȡ������Ҫ�Ŀؼ�
        ListView lv = (ListView) findViewById(R.id.lv);
        
        //[2]����ArrayAdapter���󡣲���Ҫ��BaseAdapterһ������Ҫ�Զ���ʵ���࣬��ΪBaseAdapter�ǳ����࣬�޷����ɶ���
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item, R.id.tv, data);
        lv.setAdapter(adapter);
    }
}
