package com.cmcc.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    private SQLiteDatabase db;
    private MySqliteOpenHelper mySqliteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mySqliteOpenHelper = new MySqliteOpenHelper(getApplicationContext());
        
        //打开 或者 创建 数据库。如果是第一次就是创建。
//      db = mySqliteOpenHelper.getWritableDatabase();
        //打开 或者 创建 数据库。如果是第一次就是创建。如果磁盘满了，就返回只读的
//      db = mySqliteOpenHelper.getReadableDatabase();
    }

    /*
     * 自己手动写的CURL
     * 优点：可以进行多表查询
    
    //增
    public void click1(View v) {
        db = mySqliteOpenHelper.getWritableDatabase();
        db.execSQL("insert into info(name, phone) values(?,?)", new Object[]{"王五","13866749751"});
        db.close();
    }
    
    //删
    public void click2(View v) {
        db = mySqliteOpenHelper.getWritableDatabase();
        db.execSQL("delete from info where _id=?", new Object[]{"1"});
        db.close();
    }
    
    //改
    public void click3(View v) {
        db = mySqliteOpenHelper.getWritableDatabase();
        db.execSQL("update info set name=? where _id=?", new Object[]{"李四","1"});
        db.close();
    }
    
    //查
    public void click4(View v) {
        db = mySqliteOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name from info where _id=?", new String[]{"1"});
        if(cursor != null && cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                //columnIndex代表列的索引
                String name = cursor.getString(0);  //获取查询结果的第0列
                System.out.println("name="+name);
            }
        }
        
        db.close();
    }
    
    */
    
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
        Cursor cursor = db.query("info", new String[] {"name"}, "name=?", new String[] {"赵六"}, null, null, null);
        if(cursor != null && cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                //columnIndex代表查询结果的索引
                String name = cursor.getString(0);
                System.out.println("name = "+ name);
                Toast.makeText(getApplicationContext(), "name = "+name, 0).show();
            }
        }
        
        db.close();
    }
}
