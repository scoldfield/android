package com.cmcc.phonedail;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.cmcc.click3.R;

public class MainActivity extends Activity implements OnClickListener {

    private EditText et_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载一个布局
        setContentView(R.layout.activity_main);
        
        //[1]找到我们需要的控件：edittext button
        et_number = (EditText) findViewById(R.id.editText1);
        //[2]找到按钮
        Button btn1 = (Button) findViewById(R.id.button1);
        Button btn2 = (Button) findViewById(R.id.button2);
        Button btn3 = (Button) findViewById(R.id.button3);
        //[3]给button按钮设置一个点击事件。
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        
    }

    //按钮点击事件3：使该类实现OnClickListener接口，并在该类内部重写onClick方法，调用的时候参数可以使用this
    @Override
    public void onClick(View v) {
        //通过参数View去判断具体点击的是哪个按钮
        String number = et_number.getText().toString().trim();

        switch (v.getId()) {
        case R.id.button1:
            dial(number);
            break;
        case R.id.button2:
            dial(number);
            break;
        case R.id.button3:
            dial(number);
            break;

        default:
            break;
        }
    }
    
    //打电话方法
    public void dial(String number) {
        //[5]进行拨打电话。使用"意图"intent
        Intent intent = new Intent();       //创建一个意图对象。打  猫狗等
        //[5.1]为意图设置动作
        intent.setAction(Intent.ACTION_CALL);   //设置打电话的动作
        //[5.2]设置要拨打的数据
        intent.setData(Uri.parse("tel:" + number));    //"tel:"是打电话的固定写法
        
        //[6]开启意图
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
