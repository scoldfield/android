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
        
        //将数据显示到控件上
        lv = (ListView) findViewById(R.id.lv);
        
        //获取xml数据，并初始化List<Sms>列表
        initXmlList();
    }
    
    //定义适配器
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
            
            //向view中设置数据
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
            SmartImageView iv = (SmartImageView) view.findViewById(R.id.iv);    //使用开源软件SmartImageView
            
            tv_title.setText("Body:" + smss.get(position).getBody());
            tv_desc.setText("data:" + smss.get(position).getDate() + ", Address:" + smss.get(position).getAddress());
            tv_type.setText(smss.get(position).getDate() + "点击");
            iv.setImageUrl(smss.get(position).getImage());    //使用开源软件SmartImageView
            
            return view;
        }
        
    }
    
    public void initXmlList() {
      //访问网络，开启新的线程
        new Thread() {
            public void run() {
                try {
                    //xml地址获取
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
