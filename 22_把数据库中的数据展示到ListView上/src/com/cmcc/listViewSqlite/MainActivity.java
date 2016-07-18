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
        
        //[0]�����ݿ���ȡ���������ݣ��浽List��
        persons = new ArrayList<Person>();

        //[1]�������ݿ�
        mySqliteOpenHelper = new MySqliteOpenHelper(getApplicationContext());
        
        //[2]��ȡ������Ҫ�Ŀؼ�
        lv = (ListView) findViewById(R.id.lv);
        
        
    }
    
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
        Cursor cursor = db.query("info", null, null, null, null, null, null);   //�����������
        if(cursor != null && cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                //columnIndex�����ѯ���������
                String name = cursor.getString(0);
                String phone = cursor.getString(1);
                
                //�����ݷ�װ��javabean��
                Person p = new Person();
                p.setName(name);
                p.setPhone(phone);
                
                //���ݴ洢��List��
                persons.add(p);
            }
        }
        db.close();
        
        //���������ʱ��adapter��listView���а�
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
            //��ȡview����
            if(convertView == null) {
                view = View.inflate(getApplicationContext(), R.layout.item, null);
            }else {
                view = convertView;
            }

            //�ҵ��ؼ�������ʾ����(�������Ҫ����"view."��ʾ�Ǵ�view�����в���id��������ӣ���Ĭ���Ǵ�R.layout.activity_main�в���id��)
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);

            //�����ʾ���ݡ�position�Ǵ�0��ʼ������
            Person p = persons.get(position);
            tv_name.setText(p.getName());
            tv_phone.setText(p.getPhone());
            
            return view;
        }
        
    }
}
