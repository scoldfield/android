package com.cmcc.xutils;


import java.io.File;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
    
    private EditText tv_path;

    private String path;

    private ProgressBar pb;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tv_path = (EditText) findViewById(R.id.tv_path);
        pb = (ProgressBar) findViewById(R.id.pb);
    }
    
    public void click(View v) {
        path = tv_path.getText().toString().trim();
        /*
         * 通过开源项目实现断点只需要两步
         */
        //[1]创建httpUtils对象
        HttpUtils http = new HttpUtils();
        //[2]实现断点下载
        http.download(path, "/mnt/sdcard/haha.mp3", true, new RequestCallBack<File>() {
            //下载成功
            @Override
            public void onSuccess(ResponseInfo<File> arg0) {
                Toast.makeText(getApplicationContext(), "下载成功", 0).show();
            }
            
            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                pb.setMax((int)total);
                pb.setProgress((int)current);
            }
            
            @Override
            public void onFailure(HttpException arg0, String arg1) {
                
            }
        });
    }
}
