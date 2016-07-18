package com.cmcc.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmcc.picture.R;

public class MainActivity extends Activity {

    private EditText et;
    private ImageView iv;
    
    Handler handler = new Handler() {
        //�÷����������߳���ִ��
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            iv.setImageBitmap(bitmap);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        et = (EditText) findViewById(R.id.et);
        iv = (ImageView) findViewById(R.id.iv);
        
    }
    
    public void click(View v) {
        
        /*
         * ͨ�������µ��߳���ʵ�ַ�������
         */
        Thread thread = new Thread() {
            public void run() {
                try {
                    String path = et.getText().toString().trim();
                    if(path != null && !"".equals(path)) {
                        File file = new File(getCacheDir(), Base64.encodeToString(path.getBytes(), Base64.DEFAULT));    //ʹ��File(String dir, String name)���������ļ�·����getCacheDir()�����ǻ�ȡ��Ӧ�õĻ���·�������������Googleд�õģ�ÿ��Ӧ�ö���һ��cache�����ļ�
                        
                        if(file != null && file.length() > 0) {
                            //ͼƬ�ļ��Ѿ��ڻ����д���
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            Message msg = Message.obtain();
                            msg.obj = bitmap;
                            handler.sendMessage(msg);
                            System.out.println("ͼƬ�Ѿ����ڻ�����");
                        }else {
                            //������û�и�ͼƬ�ļ������������أ����洢��������
                            URL url = new URL(path);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.setConnectTimeout(5000);
                            int code = conn.getResponseCode();
                            if(code == 200) {
                                InputStream in = conn.getInputStream();
                                
                                //�ļ����뻺��
                                FileOutputStream fos = new FileOutputStream(file);
                                int len = 0;
                                byte[] buffer = new byte[1024];
                                while((len = in.read(buffer)) > 0) {
                                    fos.write(buffer, 0, len);
                                }
                                in.close();
                                fos.close();
                                
                                //ͼƬ���͸����߳�
                                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                Message msg = Message.obtain();
                                msg.obj = bitmap;
                                handler.sendMessage(msg);
                                System.out.println("��һ�δ������л�ȡͼƬ");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
        };
        
        thread.start();
    }
}
