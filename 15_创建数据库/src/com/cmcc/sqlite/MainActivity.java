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
        
        //�� ���� ���� ���ݿ⡣����ǵ�һ�ξ��Ǵ�����
//      db = mySqliteOpenHelper.getWritableDatabase();
        //�� ���� ���� ���ݿ⡣����ǵ�һ�ξ��Ǵ���������������ˣ��ͷ���ֻ����
//      db = mySqliteOpenHelper.getReadableDatabase();
    }

    /*
     * �Լ��ֶ�д��CURL
     * �ŵ㣺���Խ��ж���ѯ
    
    //��
    public void click1(View v) {
        db = mySqliteOpenHelper.getWritableDatabase();
        db.execSQL("insert into info(name, phone) values(?,?)", new Object[]{"����","13866749751"});
        db.close();
    }
    
    //ɾ
    public void click2(View v) {
        db = mySqliteOpenHelper.getWritableDatabase();
        db.execSQL("delete from info where _id=?", new Object[]{"1"});
        db.close();
    }
    
    //��
    public void click3(View v) {
        db = mySqliteOpenHelper.getWritableDatabase();
        db.execSQL("update info set name=? where _id=?", new Object[]{"����","1"});
        db.close();
    }
    
    //��
    public void click4(View v) {
        db = mySqliteOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name from info where _id=?", new String[]{"1"});
        if(cursor != null && cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                //columnIndex�����е�����
                String name = cursor.getString(0);  //��ȡ��ѯ����ĵ�0��
                System.out.println("name="+name);
            }
        }
        
        db.close();
    }
    
    */
    
    /*
     * ʹ��Google��װ��APIʵ��
     */
    
    //��
    public void click1(View v) {
        db = mySqliteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", "����");
        values.put("phone", "15144575475");
        db.insert("info", null, values);
        db.close();
    }
    
    //ɾ
    public void click2(View v) {
        db = mySqliteOpenHelper.getWritableDatabase();
        int res = db.delete("info", "name=?", new String[] {"����"});
        if(res > 0) {
            Toast.makeText(getApplicationContext(), "ɾ���ɹ���", 1).show();
        }else {
            Toast.makeText(getApplicationContext(), "ɾ��ʧ�ܣ�", 1).show();
        }
        db.close();
    }
    
    //��
    public void click3(View v) {
        db = mySqliteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone", "114");
        int res = db.update("info", values, "name=?", new String[] {"����"});
        if(res > 0) {
            Toast.makeText(getApplicationContext(), "�޸ĳɹ�", 1).show();
        }else {
            Toast.makeText(getApplicationContext(), "�޸�ʧ��", 1).show();
        }
        db.close();
    }
    
    //��
    public void click4(View v) {
        db = mySqliteOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("info", new String[] {"name"}, "name=?", new String[] {"����"}, null, null, null);
        if(cursor != null && cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                //columnIndex�����ѯ���������
                String name = cursor.getString(0);
                System.out.println("name = "+ name);
                Toast.makeText(getApplicationContext(), "name = "+name, 0).show();
            }
        }
        
        db.close();
    }
}
