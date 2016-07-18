package com.cmcc.smsSend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContentActivity extends Activity {

    private ListView lv_cont;

    private TextView tv_cont;

    private String[] contents = {"我正在开会，等会回你电话","我正在吃饭，等会回你电话","我正在打电话，等会回你电话","我正在写代码，等会回你电话"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_content);

        lv_cont = (ListView) findViewById(R.id.lv_cont);
        tv_cont = (TextView) findViewById(R.id.tv_cont);
        
        lv_cont.setAdapter(new MyAdapter());
        
        lv_cont.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                String content = contents[position];
                
                Intent intent = new Intent();
                intent.putExtra("content", content);
                
                setResult(11, intent);
                finish();
            }
        });
    }
    
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return contents.length;
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
                view = View.inflate(getApplicationContext(), R.layout.item_content, null);
            }else {
                view = convertView;
            }
            
            TextView tv_cont = (TextView) view.findViewById(R.id.tv_cont);
            tv_cont.setText(contents[position]);
            return view;
        }
        
    }
}
