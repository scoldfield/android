package com.cmcc.smsSend;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class PhoneActivity extends Activity {

    private ListView lv;
    
    private List<Person> persons;

    private TextView tv_name;

    private TextView tv_phone; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_phone);
        
        lv = (ListView) findViewById(R.id.lv);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        
        persons = new ArrayList<Person>();
        for(int i = 0; i < 10; i++) {
            Person p = new Person();
            p.setName("张三" + i);
            p.setPhone("11"+i);
            
            persons.add(p);
        }
        
        lv.setAdapter(new MyAdapter());
        
        //为ListView添加单击事件
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                //注意：是通过上面的数组数据中来获取数据的，而不是控件
                String phone = persons.get(position).getPhone();
                
                /*
                 * 将拿到的数据返回给原Activity
                 */
                //[1]依然是通过Intent对象，将数据返回给调用者
                Intent intent = new Intent();
                intent.putExtra("phone", phone);
                
                //[2]将结果返回给调用者。其中，10表示返回码
                setResult(10, intent);
                
                //[3]关闭当前页面
                finish();
                
                //[4]MainActivity中通过onActivityResult()方法中的Intent参数来接收intent，并获取返回的数据
                
            }
        });
        
    }
    
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return persons.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view;
            if(convertView == null) {
                view = View.inflate(getApplicationContext(), R.layout.item_phone, null);
            }else {
                view = convertView;
            }
            
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            
            tv_name.setText(persons.get(position).getName());
            tv_phone.setText(persons.get(position).getPhone());
            
            return view;
        }
        
    }
}
