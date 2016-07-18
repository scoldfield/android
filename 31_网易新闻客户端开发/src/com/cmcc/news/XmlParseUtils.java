package com.cmcc.news;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class XmlParseUtils {
    
    public static List parseXml(InputStream in) {
        
        List<Sms> smss = null;
        Sms sms = null;
        
        try {
            //xml数据解析
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(in, "utf-8");
            int type = parser.getEventType();
            while(type != XmlPullParser.END_DOCUMENT) {
                
                switch (type) {
                case XmlPullParser.START_TAG:
                    if("smss".equals(parser.getName())) {
                        smss = new ArrayList<Sms>();
                    }else if("sms".equals(parser.getName())) {
                        sms = new Sms();
                    }else if("address".equals(parser.getName())) {
                        sms.setAddress(parser.nextText());
                    }else if ("body".equals(parser.getName())) {
                        sms.setBody(parser.nextText());
                    }else if ("date".equals(parser.getName())) {
                        sms.setDate(parser.nextText());
                    }else if ("image".equals(parser.getName())) {
                        sms.setImage(parser.nextText());
                    }
                    break;
                    
                case XmlPullParser.END_TAG:
                    if("sms".equals(parser.getName())) {
                        smss.add(sms);
                    }
                    break;
                }
                
                //不停向下解析
                type = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return smss;
    }

}
