package com.cmcc.simpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //[0]准备要显示的数据
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "张三");
        map.put("phone", "110");
        data.add(map);
        
        map = new HashMap<String, String>();
        map.put("name", "李四");
        map.put("phone", "119");
        data.add(map);
        
        map = new HashMap<String, String>();
        map.put("name", "王五");
        map.put("phone", "120");
        data.add(map);
        
        //[1]找到控件
        ListView lv = (ListView) findViewById(R.id.lv);
        
        //[2]设置适配器
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.item, new String[] {"name", "phone"}, new int[] {R.id.tv_name, R.id.tv_phone});
        
        //[3]绑定适配器
        lv.setAdapter(adapter);
    }
    
}