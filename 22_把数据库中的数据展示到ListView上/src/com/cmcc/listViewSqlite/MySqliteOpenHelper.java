package com.cmcc.listViewSqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

    /*
     * �̳�SQLiteOpenHelper���������Ҫʵ����һ�����캯��������ͨ��super���̳в�ʵ���丸�๹�캯��
     * name�����ݿ���
     * factory����ʾ���λ��
     * version���汾��
     */
    public MySqliteOpenHelper(Context context) {
        super(context, "itheima.db", null, 3);
        
    }

    /*
     * �÷���ֻ�е����ݿ��һ�� ���� ��ʱ�����
     * �÷��������� "��ĳ�ʼ��"
     * 
     * db����ʾ���Ǵ��������ݿ�
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //idһ����_id����ʾ������
        db.execSQL("create table info(_id integer primary key autoincrement, name varchar(20))");
    }

    /*
     * �÷��������ݿ�汾������ʱ�����
     * �÷����ʺ���"��ṹ�ĸ���"
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //����Ҫ��������MySqliteOpenHelper���캯���еİ汾�Ų���������update�ɹ�
        db.execSQL("alter table info add phone varchar(20)");
    }

}
