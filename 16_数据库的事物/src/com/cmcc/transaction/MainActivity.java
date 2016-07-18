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
        
        //��ȡ���ݿ���
        sqliteOpenHelper = new MySqliteOpenHelper(getApplicationContext());
        
    }

    public void click1(View v) {
        SQLiteDatabase db = sqliteOpenHelper.getWritableDatabase();
        //��������
        db.beginTransaction();
        
        //"����"��Google�Ƽ��ı�׼д��
        try {
            db.execSQL("update customers set money=money-100 where name=?", new String[] {"����"});
            db.execSQL("update customers set money=money+100 where name=?", new String[] {"����"});

            int res = 10/0;
            
            //�����ִ�е���һ����˵��sql��䶼ִ����ȷ����ʱ��������������һ���ɹ��ı��
            db.setTransactionSuccessful();
            Toast.makeText(getApplicationContext(), "ת�˳ɹ���", 0).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ϵͳ��æ�����Ժ�����...", 0).show();
        }finally {
            //���۳ɹ����һ��Ҫ�ر�����
            db.endTransaction();
        }
        
    }
    
}
