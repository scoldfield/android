package com.cmcc.listViewSqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

    /*
     * 继承SQLiteOpenHelper抽象类后，需要实现其一个构造函数。这里通过super来继承并实现其父类构造函数
     * name：数据库名
     * factory：表示光标位置
     * version：版本号
     */
    public MySqliteOpenHelper(Context context) {
        super(context, "itheima.db", null, 3);
        
    }

    /*
     * 该方法只有当数据库第一次 创建 的时候调用
     * 该方法适用于 "表的初始化"
     * 
     * db：表示我们创建的数据库
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //id一般用_id来表示变量名
        db.execSQL("create table info(_id integer primary key autoincrement, name varchar(20))");
    }

    /*
     * 该方法在数据库版本升级的时候调用
     * 该方法适合做"表结构的更新"
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //必须要增大上面MySqliteOpenHelper构造函数中的版本号参数，才能update成功
        db.execSQL("alter table info add phone varchar(20)");
    }

}
