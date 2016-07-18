package com.cmcc.news;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private List<Sms> smss;
    private ListView lv;

//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            smss = (List<Sms>) msg.obj;
//        };
//    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //��������ʾ���ؼ���
        lv = (ListView) findViewById(R.id.lv);
        
        //��ȡxml���ݣ�����ʼ��List<Sms>�б�
        initXmlList();
    }
    
    //����������
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return smss.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView == null) {
                view = View.inflate(getApplicationContext(), R.layout.item, null);
            }else {
                view = convertView;
            }
            
            //��view����������
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
            SmartImageView iv = (SmartImageView) view.findViewById(R.id.iv);    //ʹ�ÿ�Դ���SmartImageView
            
            tv_title.setText("Body:" + smss.get(position).getBody());
            tv_desc.setText("data:" + smss.get(position).getDate() + ", Address:" + smss.get(position).getAddress());
            tv_type.setText(smss.get(position).getDate() + "���");
            iv.setImageUrl(smss.get(position).getImage());    //ʹ�ÿ�Դ���SmartImageView
            
            return view;
        }
        
    }
    
    public void initXmlList() {
      //�������磬�����µ��߳�
        new Thread() {
            public void run() {
                try {
                    //xml��ַ��ȡ
                    String path = "http://home.ustc.edu.cn/~ldddhb/smsBackup.xml";
                    URL url;
                    url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(3000);
                    int code = conn.getResponseCode();
                    if(code == 200) {
                        InputStream in = conn.getInputStream();
                        smss = XmlParseUtils.parseXml(in);
                        
//                        Message msg = Message.obtain();
//                        msg.obj = smss;
//                        handler.sendMessage(msg);
                        
                        runOnUiThread(new Runnable() {
                            
                            @Override
                            public void run() {
                                lv.setAdapter(new MyAdapter());
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }
}
