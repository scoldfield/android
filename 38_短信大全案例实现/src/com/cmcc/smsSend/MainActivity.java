package com.cmcc.smsSend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    private ListView lv;

    private String[] smss = {"我的祝福最霸道，收到你就没烦恼，白天黑夜乐淘淘；我的短信最美好，看后愁闷都得跑，轻松愉快才逍遥；我的信息最牢靠，幸福吉祥来笼罩，美梦成真在今朝；我的祝愿最着调，生日当天有爆料，欢歌笑语总围绕！",
                             "你在我的心中扎根，我们的友谊露重更深；你在我的牵挂中缤纷，我们的情感喜大普奔；你在我的坦诚中光临，我们的心有灵犀更近；你在我的问候中欢欣，我们的关爱情不自禁；你在我的祝福中兴奋，祝你生日快乐开心！",
                             "好友如梦似幻，日夜兼程思念，亲朋血脉相连，话语滋润心田，知音一生有缘，牵手幸福永远，挚爱看雨听蝉，开心快乐无边，知己晓得冷暖，经常相互惦念，我心与你相牵，送你灿烂心愿，祝你生日圆满，谱写壮丽诗篇！",
                             "一捧香土藏奇葩，孕育种子发新芽，寄托情思千万里，一年四季绘新画；一滴细雨催鲜花，雨过天晴景色佳，朵朵相思满枝桠，千丝万缕放光华；一条短信给你发，祝你开心美潇洒，生日快乐伴随你，高枕无忧爱融洽！",
                             "一年四季你在忙，辛苦奔波也安康，朋友恩情记心上，春夏秋冬不淡忘，多吃是福巧梳妆，早睡早起心敞亮，工作不要太疯狂，劳逸结合身体棒，朋友常聚人脉旺，游刃有余尽徜徉，我的短信圆梦想，生日快乐财源广！",
                             "风霜雪雨走一路，日夜兼程不停步，笑泪各半掩双目，前途坎坷当散步，一程山水艰难赴，山穷水尽看萍浮，晨有朝霞夕落幕，潇洒走过就是福，仕途高低别在乎，工作舒心赚财富，我的短信似火炉，生日快乐常守护！"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //启动加载activity_main.xml布局
        setContentView(R.layout.activity_main);
        
        //[1]ListView的配置
        lv = (ListView) findViewById(R.id.lv);
        //[1.1]adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item, smss);
        //[1.2]设定adapter
        lv.setAdapter(adapter);
        
        //[2]为ListView设置onClick事件
        lv.setOnItemClickListener(new OnItemClickListener() {

            /*
             * 参数解释：
             * parent：即为ListView对象
             * view：即为item对象
             * position和id都是索引
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String content = smss[position];
                
                //[3]页面跳转。采用隐式跳转，因为涉及到mimeType和data
                Intent intent = new Intent();
                //[3.1]调用系统的短信控件。首先查短信的清单文件的配置，从LogCat中看其调用了哪个activity
                /*
                                                 从中可以看出
                         <activity android:name=".ui.ComposeMessageActivity">
                             <intent-filter>
                                   <action android:name="android.intent.action.SEND" />
                                   <category android:name="android.intent.category.DEFAULT" />
                                   <data android:mimeType="text/plain" />
                             </intent-filter>
                         </activity>
                                                 中这个发送短信过滤器，就用这个。所有的属性设置都看这个过滤器
                 */
                intent.setAction("android.intent.action.SEND");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setType("text/plain");
                
                //[3.2]插入要传输的数据。key不能随便乱写，应与短信中获取信息的key值相同，所以去源码ComposeMessageActivity.java中查找getStringExtra方法，看哪个像就找哪个
                intent.putExtra("sms_body", content);
                
                //[3.2]启动跳转页面
                startActivity(intent);
            }
        });
    }
}
