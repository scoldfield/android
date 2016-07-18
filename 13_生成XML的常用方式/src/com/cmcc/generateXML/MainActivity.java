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
    
    //ʹ��XMLSerializer������XML�ļ�
    public void click(View view) {
        try {
            //[1]��ȡXmlSerializer����ͨ��Xml���������(�൱��Utils)����ȡ
            XmlSerializer serializer = Xml.newSerializer();
            //[2]����xmlserializer���л�������
            File file = new File(Environment.getExternalStorageDirectory().getPath(), "smsBackup.xml"); //File(String dirPath, String name)
            FileOutputStream fos;
            fos = new FileOutputStream(file);
            serializer.setOutput(fos, "utf-8");
            
            //[3]��ʼдxml�ĵ���ͷ
            serializer.startDocument("utf-8", true);
            
            //[4]��ʼдxml�ĸ��ڵ�namespace�����ռ�
            serializer.startTag(null, "smss");
            //[5]ѭ��дsms�ڵ�
            for(Sms sms : smsList) {
                serializer.startTag(null, "sms");
                
                //��ʼaddress�ڵ�
                serializer.startTag(null, "address");
                serializer.text(sms.getAddress());
                serializer.endTag(null, "address");
                
                //��ʼbody�ڵ�
                serializer.startTag(null, "body");
                serializer.text(sms.getBody());
                serializer.endTag(null, "body");
                
                //��ʼdate�ڵ�
                serializer.startTag(null, "date");
                serializer.text(sms.getDate());
                serializer.endTag(null, "date");
                
                //sms�ڵ����
                serializer.endTag(null, "sms");
            }
            
            //[6]дxml��β�ڵ�
            serializer.endTag(null, "smss");
            
            //[7]д�ĵ���β
            serializer.endDocument();
            
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
