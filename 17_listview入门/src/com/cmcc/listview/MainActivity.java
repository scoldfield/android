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
        
        //[1]�ҵ����ǹ��ĵĿؼ�
        ListView lv = (ListView) findViewById(R.id.lv);
        
        //[2]��ʾ���� �� ������ͨ�ؼ�(textview)�е�����������Դ������������
        lv.setAdapter(new MyListView());
    }
    
    /*
     * [3]����listview������������
     * ֻ��Ҫʵ��getCount()��getView()������������
     */
    private class MyListView extends BaseAdapter{

        //һ���ж�����������Ҫ��ʾ
        @Override
        public int getCount() {
            return 100000000;
        }

        //����ָ��positionλ�ö�Ӧ�Ķ���
        @Override
        public Object getItem(int position) {
            return null;
        }

        //����positionλ�ö�Ӧ��id
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        //��ȡһ��view��������ʾlistview�����ݣ�����Ϊlistview��һ����Ŀ����
        //convertView ����ʷ������󡣾���ҳ���б��ֻ���ȥ���ǲ���view��
        //��Ϊ��������ʱ����û���κ�view�ᱻ����ȥ�ģ����convertViewΪnull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = null;
            if(convertView == null) {
                tv = new TextView(MainActivity.this);
                System.out.println("�����µ�view����---" + position);
            }else {
                tv = (TextView) convertView;
                System.out.println("������ʷ�����view����---" + position);
            }
            
            tv.setText("����"+position);
            return tv;
        }
    }
}
