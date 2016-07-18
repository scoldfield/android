package com.cmcc.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //[1]找到我们关心的控件
        ListView lv = (ListView) findViewById(R.id.lv);
        
        //[2]显示数据 和 其他普通控件(textview)有点区别，数据来源于数据适配器
        lv.setAdapter(new MyListView());
    }
    
    /*
     * [3]定义listview的数据适配器
     * 只需要实现getCount()和getView()两个方法即可
     */
    private class MyListView extends BaseAdapter{

        //一共有多少条数据需要显示
        @Override
        public int getCount() {
            return 100000000;
        }

        //返回指定position位置对应的对象
        @Override
        public Object getItem(int position) {
            return null;
        }

        //返回position位置对应的id
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        //获取一个view，用来显示listview的数据，会作为listview的一个条目出现
        //convertView 是历史缓存对象。就是页面中被手划上去的那部分view。
        //因为刚启动的时候是没有任何view会被划上去的，因此convertView为null
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = null;
            if(convertView == null) {
                tv = new TextView(MainActivity.this);
                System.out.println("创建新的view对象---" + position);
            }else {
                tv = (TextView) convertView;
                System.out.println("复用历史缓存的view对象---" + position);
            }
            
            tv.setText("哈哈"+position);
            return tv;
        }
    }
}
