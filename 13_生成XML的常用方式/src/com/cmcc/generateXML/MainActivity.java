package com.cmcc.generateXML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;
import com.cmcc.generateXML.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.View;

public class MainActivity extends Activity {

    private List<Sms> smsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        smsList = new ArrayList<Sms>();
        for(int i = 0; i < 10; i++) {
            Sms sms = new Sms();
            sms.setAddress("10008"+i);
            sms.setBody("nihao"+i);
            sms.setDate("201"+i);
            
            smsList.add(sms);
        }
        
    }
    
    //使用XMLSerializer来生成XML文件
    public void click(View view) {
        try {
            //[1]获取XmlSerializer对象。通过Xml这个工具类(相当于Utils)来获取
            XmlSerializer serializer = Xml.newSerializer();
            //[2]设置xmlserializer序列化器参数
            File file = new File(Environment.getExternalStorageDirectory().getPath(), "smsBackup.xml"); //File(String dirPath, String name)
            FileOutputStream fos;
            fos = new FileOutputStream(file);
            serializer.setOutput(fos, "utf-8");
            
            //[3]开始写xml文档开头
            serializer.startDocument("utf-8", true);
            
            //[4]开始写xml的根节点namespace命名空间
            serializer.startTag(null, "smss");
            //[5]循环写sms节点
            for(Sms sms : smsList) {
                serializer.startTag(null, "sms");
                
                //开始address节点
                serializer.startTag(null, "address");
                serializer.text(sms.getAddress());
                serializer.endTag(null, "address");
                
                //开始body节点
                serializer.startTag(null, "body");
                serializer.text(sms.getBody());
                serializer.endTag(null, "body");
                
                //开始date节点
                serializer.startTag(null, "date");
                serializer.text(sms.getDate());
                serializer.endTag(null, "date");
                
                //sms节点结束
                serializer.endTag(null, "sms");
            }
            
            //[6]写xml的尾节点
            serializer.endTag(null, "smss");
            
            //[7]写文档结尾
            serializer.endDocument();
            
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
