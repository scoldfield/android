package com.cmcc.listview2;

import java.util.zip.Inflater;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //[1]获取我们需要的控件
        ListView lv = (ListView) findViewById(R.id.lv);
        
        //[3]为ListView设置adapter
        lv.setAdapter(new MyAdapter());
        
    }
    
    //[2]定义listview的数据适配器
    //只需要实现getCount()和getView()两个方法即可
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 6;       //显示6条新闻
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /*
             * 新闻布局中即有文本部分，也有图片部分，分别需要TextView和ImageView两个View来实现，
             * 而getView()方法中只能返回一个View，因此我们通过将这两个View放到一个layout布局中，
             * 然后将这个layout布局转换成View返回即可。
             * 这个布局的名字开发中一般起名为item，放在layout文件夹中
             */
            
            //[1]想办法把我们定义的layout布局item转变成一个View对象
            View view;
            if(convertView == null) {
                /*
                 * [2]inflate()方法，俗称打气筒，可以把布局layout资源转换成一个view对象
                 * resource是我们自定义的布局文件
                 * root 是view的viewGroup，现阶段不使用
                 */
                
                //获取打气筒的方法一：
//                view = View.inflate(getApplicationContext(), R.layout.item, null);
                
                //获取打气筒的方法二：
//                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item, null);
                
                //获取打气筒的方法三：
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item, null);
            }else {
                //复用历史缓存对象convertView
                view = convertView;
            }
            
            return view;
        }
        
    }
}
