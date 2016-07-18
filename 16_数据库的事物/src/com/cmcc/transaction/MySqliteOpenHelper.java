package com.cmcc.transaction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

    public MySqliteOpenHelper(Context context) {
        super(context, "Account.db", null, 1);
    }

    /*
     * 创建表
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table customers(_id integer primary key autoincrement, name varchar(20), phone varchar(20), money integer)");
        db.execSQL("insert into customers('name', 'phone', 'money') values('张三', '114', '2000')");
        db.execSQL("insert into customers('name', 'phone', 'money') values('李四', '118', '5000')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
