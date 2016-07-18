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

    private String[] smss = {"�ҵ�ף����Ե����յ����û���գ������ҹ�����ԣ��ҵĶ��������ã�������ƶ����ܣ�����������ң���ҵ���Ϣ���ο����Ҹ����������֣����γ����ڽ񳯣��ҵ�ףԸ���ŵ������յ����б��ϣ�����Ц����Χ�ƣ�",
                             "�����ҵ��������������ǵ�����¶�ظ�������ҵ�ǣ�����ͷף����ǵ����ϲ���ձ��������ҵ�̹���й��٣����ǵ�������Ϭ�����������ҵ��ʺ��л��������ǵĹذ��鲻�Խ��������ҵ�ף�����˷ܣ�ף�����տ��ֿ��ģ�",
                             "���������ƻã���ҹ���˼�����Ѫ�������������������֪��һ����Ե��ǣ���Ҹ���Զ��ֿ���������������Ŀ����ޱߣ�֪��������ů�������໥�������������ǣ�����������Ը��ף������Բ������д׳��ʫƪ��",
                             "һ�����������⣬�������ӷ���ѿ��������˼ǧ���һ���ļ����»���һ��ϸ����ʻ���������羰ɫ�ѣ������˼��֦�⣬ǧ˿���ƷŹ⻪��һ�����Ÿ��㷢��ף�㿪�������������տ��ְ����㣬�������ǰ���Ǣ��",
                             "һ���ļ�����æ�����౼��Ҳ���������Ѷ�������ϣ������ﶬ������������Ǹ�����ױ����˯�����ĳ�����������Ҫ̫������ݽ������������ѳ������������������ྡ���࣬�ҵĶ���Բ���룬���տ��ֲ�Դ�㣡",
                             "��˪ѩ����һ·����ҹ��̲�ͣ����Ц�������˫Ŀ��ǰ;������ɢ����һ��ɽˮ���Ѹ���ɽ��ˮ����Ƽ�������г�ϼϦ��Ļ�������߹����Ǹ�����;�ߵͱ��ں�����������׬�Ƹ����ҵĶ����ƻ�¯�����տ��ֳ��ػ���"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //��������activity_main.xml����
        setContentView(R.layout.activity_main);
        
        //[1]ListView������
        lv = (ListView) findViewById(R.id.lv);
        //[1.1]adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item, smss);
        //[1.2]�趨adapter
        lv.setAdapter(adapter);
        
        //[2]ΪListView����onClick�¼�
        lv.setOnItemClickListener(new OnItemClickListener() {

            /*
             * �������ͣ�
             * parent����ΪListView����
             * view����Ϊitem����
             * position��id��������
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String content = smss[position];
                
                //[3]ҳ����ת��������ʽ��ת����Ϊ�漰��mimeType��data
                Intent intent = new Intent();
                //[3.1]����ϵͳ�Ķ��ſؼ������Ȳ���ŵ��嵥�ļ������ã���LogCat�п���������ĸ�activity
                /*
                                                 ���п��Կ���
                         <activity android:name=".ui.ComposeMessageActivity">
                             <intent-filter>
                                   <action android:name="android.intent.action.SEND" />
                                   <category android:name="android.intent.category.DEFAULT" />
                                   <data android:mimeType="text/plain" />
                             </intent-filter>
                         </activity>
                                                 ��������Ͷ��Ź�������������������е��������ö������������
                 */
                intent.setAction("android.intent.action.SEND");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setType("text/plain");
                
                //[3.2]����Ҫ��������ݡ�key���������д��Ӧ������л�ȡ��Ϣ��keyֵ��ͬ������ȥԴ��ComposeMessageActivity.java�в���getStringExtra���������ĸ�������ĸ�
                intent.putExtra("sms_body", content);
                
                //[3.2]������תҳ��
                startActivity(intent);
            }
        });
    }
}
