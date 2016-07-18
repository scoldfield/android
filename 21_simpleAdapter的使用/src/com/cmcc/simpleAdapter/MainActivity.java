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
        
        //[0]׼��Ҫ��ʾ������
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "����");
        map.put("phone", "110");
        data.add(map);
        
        map = new HashMap<String, String>();
        map.put("name", "����");
        map.put("phone", "119");
        data.add(map);
        
        map = new HashMap<String, String>();
        map.put("name", "����");
        map.put("phone", "120");
        data.add(map);
        
        //[1]�ҵ��ؼ�
        ListView lv = (ListView) findViewById(R.id.lv);
        
        //[2]����������
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.item, new String[] {"name", "phone"}, new int[] {R.id.tv_name, R.id.tv_phone});
        
        //[3]��������
        lv.setAdapter(adapter);
    }
    
}