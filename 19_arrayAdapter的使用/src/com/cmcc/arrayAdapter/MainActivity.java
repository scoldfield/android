package com.cmcc.arrayAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    String data[] = {"张三","李四","王五","赵六"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //[1]获取我们需要的控件
        ListView lv = (ListView) findViewById(R.id.lv);
        
        //[2]生成ArrayAdapter对象。不需要像BaseAdapter一样，需要自定义实现类，因为BaseAdapter是抽象类，无法生成对象
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item, R.id.tv, data);
        lv.setAdapter(adapter);
    }
}
