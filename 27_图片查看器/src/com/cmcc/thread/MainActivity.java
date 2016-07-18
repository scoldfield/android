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
        //该方法是在主线程中执行
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
         * 通过开启新的线程来实现访问网络
         */
        Thread thread = new Thread() {
            public void run() {
                try {
                    String path = et.getText().toString().trim();
                    if(path != null && !"".equals(path)) {
                        File file = new File(getCacheDir(), Base64.encodeToString(path.getBytes(), Base64.DEFAULT));    //使用File(String dir, String name)方法创建文件路径；getCacheDir()方法是获取该应用的缓存路径，这个方法是Google写好的，每个应用都有一个cache缓存文件
                        
                        if(file != null && file.length() > 0) {
                            //图片文件已经在缓存中存在
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            Message msg = Message.obtain();
                            msg.obj = bitmap;
                            handler.sendMessage(msg);
                            System.out.println("图片已经存在缓存中");
                        }else {
                            //缓存中没有该图片文件，从网上下载，并存储到缓存中
                            URL url = new URL(path);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.setConnectTimeout(5000);
                            int code = conn.getResponseCode();
                            if(code == 200) {
                                InputStream in = conn.getInputStream();
                                
                                //文件存入缓存
                                FileOutputStream fos = new FileOutputStream(file);
                                int len = 0;
                                byte[] buffer = new byte[1024];
                                while((len = in.read(buffer)) > 0) {
                                    fos.write(buffer, 0, len);
                                }
                                in.close();
                                fos.close();
                                
                                //图片发送给主线程
                                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                Message msg = Message.obtain();
                                msg.obj = bitmap;
                                handler.sendMessage(msg);
                                System.out.println("第一次从网络中获取图片");
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
