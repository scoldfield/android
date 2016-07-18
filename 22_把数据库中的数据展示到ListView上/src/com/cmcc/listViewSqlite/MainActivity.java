package com.cmcc.listViewSqlite;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private MySqliteOpenHelper mySqliteOpenHelper;
    private SQLiteDatabase db;
    private List<Person> persons;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //[0]从数据库中取出所有数据，存到List中
        persons = new ArrayList<Person>();

        //[1]关联数据库
        mySqliteOpenHelper = new MySqliteOpenHelper(getApplicationContext());
        
        //[2]获取我们需要的控件
        lv = (ListView) findViewById(R.id.lv);
        
        
    }
    
    /*
     * 使用Google封装的API实现
     */
    
    //增
    public void click1(View v) {
        db = mySqliteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", "赵六");
        values.put("phone", "15144575475");
        db.insert("info", null, values);
        db.close();
    }
    
    //删
    public void click2(View v) {
        db = mySqliteOpenHelper.getWritableDatabase();
        int res = db.delete("info", "name=?", new String[] {"王五"});
        if(res > 0) {
            Toast.makeText(getApplicationContext(), "删除成功！", 1).show();
        }else {
            Toast.makeText(getApplicationContext(), "删除失败！", 1).show();
        }
        db.close();
    }
    
    //改
    public void click3(View v) {
        db = mySqliteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone", "114");
        int res = db.update("info", values, "name=?", new String[] {"赵六"});
        if(res > 0) {
            Toast.makeText(getApplicationContext(), "修改成功", 1).show();
        }else {
            Toast.makeText(getApplicationContext(), "修改失败", 1).show();
        }
        db.close();
    }
    
    //查
    public void click4(View v) {
        db = mySqliteOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("info", null, null, null, null, null, null);   //查出所有数据
        if(cursor != null && cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                //columnIndex代表查询结果的索引
                String name = cursor.getString(0);
                String phone = cursor.getString(1);
                
                //把数据封装到javabean中
                Person p = new Person();
                p.setName(name);
                p.setPhone(phone);
                
                //数据存储到List中
                persons.add(p);
            }
        }
        db.close();
        
        //点击按键的时候将adapter和listView进行绑定
        lv.setAdapter(new MyAdapter());
    }
    
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return persons.size();
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
            View view;
            //获取view对象
            if(convertView == null) {
                view = View.inflate(getApplicationContext(), R.layout.item, null);
            }else {
                view = convertView;
            }

            //找到控件用来显示数据(这里必须要加上"view."表示是从view对象中查找id，如果不加，就默认是从R.layout.activity_main中查找id了)
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);

            //如何显示数据。position是从0开始计数的
            Person p = persons.get(position);
            tv_name.setText(p.getName());
            tv_phone.setText(p.getPhone());
            
            return view;
        }
        
    }
}
