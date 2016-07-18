package com.cmcc.transaction;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    private MySqliteOpenHelper sqliteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //获取数据库句柄
        sqliteOpenHelper = new MySqliteOpenHelper(getApplicationContext());
        
    }

    public void click1(View v) {
        SQLiteDatabase db = sqliteOpenHelper.getWritableDatabase();
        //开启事务
        db.beginTransaction();
        
        //"事务"的Google推荐的标准写法
        try {
            db.execSQL("update customers set money=money-100 where name=?", new String[] {"张三"});
            db.execSQL("update customers set money=money+100 where name=?", new String[] {"李四"});

            int res = 10/0;
            
            //如果能执行到这一步，说明sql语句都执行正确，此时可以向事物设置一个成功的标记
            db.setTransactionSuccessful();
            Toast.makeText(getApplicationContext(), "转账成功！", 0).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "系统繁忙，请稍后再试...", 0).show();
        }finally {
            //无论成功与否一定要关闭事物
            db.endTransaction();
        }
        
    }
    
}
