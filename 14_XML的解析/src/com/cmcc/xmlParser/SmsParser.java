package com.cmcc.xmlParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class SmsParser {

    /*
     * ����������������ʽ�����ݷ��ص�
     */
    
    public static List<Sms> parserXml(InputStream in){
        
        List<Sms> smss = null;
        Sms sms = null;

        try {

            //[0]
            
            //[1]��ȡXmlPullParser
            XmlPullParser parser = Xml.newPullParser();
            
            //[2]����XmlPullParser�Ĳ���
            parser.setInput(in, "utf-8");
            
            //[3]��ȡ�¼�����
            int type = parser.getEventType();
            while(type != XmlPullParser.END_DOCUMENT) {
                
                switch(type) {
                case XmlPullParser.START_TAG:      //������ʼ���
                    //[4]�����ж�һ�£����������ĸ���ʼ��־
                    if("smss".equals(parser.getName())) {
                        //[5]����һ�����϶���
                        smss = new ArrayList<Sms>();
                    }else if("sms".equals(parser.getName())) {
                        //[6]����Sms����
                        sms = new Sms();
                    }else if("address".equals(parser.getName())) {
                        //��ȡaddress������
                        String address = parser.nextText();
                        sms.setAddress(address);
                    }else if("body".equals(parser.getName())) {
                        String body = parser.nextText();
                        sms.setBody(body);
                    }else if("date".equals(parser.getName())) {
                        String date = parser.nextText();
                        sms.setDate(date);
                    }
                    
                    break;
                case XmlPullParser.END_TAG: //�����������
                    //�ж�Ҫ�����Ľ�����ǩ
                    if("sms".equals(parser.getName())) {
                        //��javabean����浽������
                        smss.add(sms);
                    }
                    break;
                }
                
                //��ͣ�����½���
                type = parser.next();
                
            }
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return smss;
    }
}
