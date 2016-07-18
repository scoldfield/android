package com.cmcc.xmlParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class SmsParser {

    /*
     * 服务器是以流的形式把数据返回的
     */
    
    public static List<Sms> parserXml(InputStream in){
        
        List<Sms> smss = null;
        Sms sms = null;

        try {

            //[0]
            
            //[1]获取XmlPullParser
            XmlPullParser parser = Xml.newPullParser();
            
            //[2]设置XmlPullParser的参数
            parser.setInput(in, "utf-8");
            
            //[3]获取事件类型
            int type = parser.getEventType();
            while(type != XmlPullParser.END_DOCUMENT) {
                
                switch(type) {
                case XmlPullParser.START_TAG:      //解析开始标记
                    //[4]具体判断一下，解析的是哪个开始标志
                    if("smss".equals(parser.getName())) {
                        //[5]创建一个集合对象
                        smss = new ArrayList<Sms>();
                    }else if("sms".equals(parser.getName())) {
                        //[6]创建Sms对象
                        sms = new Sms();
                    }else if("address".equals(parser.getName())) {
                        //获取address的数据
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
                case XmlPullParser.END_TAG: //解析结束标记
                    //判断要解析的结束标签
                    if("sms".equals(parser.getName())) {
                        //把javabean对象存到集合中
                        smss.add(sms);
                    }
                    break;
                }
                
                //不停的向下解析
                type = parser.next();
                
            }
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return smss;
    }
}
