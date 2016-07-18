package com.cmcc.listview2;

import java.util.zip.Inflater;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //[1]��ȡ������Ҫ�Ŀؼ�
        ListView lv = (ListView) findViewById(R.id.lv);
        
        //[3]ΪListView����adapter
        lv.setAdapter(new MyAdapter());
        
    }
    
    //[2]����listview������������
    //ֻ��Ҫʵ��getCount()��getView()������������
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 6;       //��ʾ6������
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
            /*
             * ���Ų����м����ı����֣�Ҳ��ͼƬ���֣��ֱ���ҪTextView��ImageView����View��ʵ�֣�
             * ��getView()������ֻ�ܷ���һ��View���������ͨ����������View�ŵ�һ��layout�����У�
             * Ȼ�����layout����ת����View���ؼ��ɡ�
             * ������ֵ����ֿ�����һ������Ϊitem������layout�ļ�����
             */
            
            //[1]��취�����Ƕ����layout����itemת���һ��View����
            View view;
            if(convertView == null) {
                /*
                 * [2]inflate()�������׳ƴ���Ͳ�����԰Ѳ���layout��Դת����һ��view����
                 * resource�������Զ���Ĳ����ļ�
                 * root ��view��viewGroup���ֽ׶β�ʹ��
                 */
                
                //��ȡ����Ͳ�ķ���һ��
//                view = View.inflate(getApplicationContext(), R.layout.item, null);
                
                //��ȡ����Ͳ�ķ�������
//                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item, null);
                
                //��ȡ����Ͳ�ķ�������
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item, null);
            }else {
                //������ʷ�������convertView
                view = convertView;
            }
            
            return view;
        }
        
    }
}
