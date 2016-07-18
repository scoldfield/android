package com.cmcc.xmlParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;
import com.cmcc.xmlParser.R;
import com.cmcc.xmlParser.R.id;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private List<Sms> smsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        try {
            //[1]显示天气信息
            TextView tv_sms = (TextView) findViewById(id.tv_sms);
            
            //[2]获取资产的管理者。通过上下文Context
            InputStream is = getAssets().open("smsBackup.xml");
            
            
            //[3]调用我们定义的解析xml的业务方法
            List<Sms> smss = SmsParser.parserXml(is);
            
            StringBuffer sb = new StringBuffer();
            for(Sms sms : smss) {
                sb.append(sms.toString());
            }
            
            //[4]把数据展示到textView上
            tv_sms.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
