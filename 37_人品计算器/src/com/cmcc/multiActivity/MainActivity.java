package com.cmcc.multiActivity;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText et_name;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //[1]启动时自动加载的布局
        setContentView(R.layout.activity_main);

        et_name = (EditText) findViewById(R.id.et_name);
        //[2]获取RadioGroup控件
        rg = (RadioGroup) findViewById(R.id.rg);
    
    }
    
    public void click(View v) {
        String name = et_name.getText().toString().trim();
        int rbId = rg.getCheckedRadioButtonId();
        int sex = 0;    //性别标记，用来判断RadioButton控件是否被选中，以及选中的是哪个        
        
        if(TextUtils.isEmpty(name)) {
            //name为空
            Toast.makeText(getApplicationContext(), "请输入姓名", 0).show();
            return;
        }
        
        switch (rbId) {
        case R.id.rb_male:
            sex = 1;
            break;
        case R.id.rb_female:
            sex = 2;
            break;
        case R.id.rb_other:
            sex = 3;
            break;
        }
        
        if(sex == 0) {
            //RadioButton没有被选中
            Toast.makeText(getApplicationContext(), "请选择性别", 0).show();
            return;
        }
        
        //[3]跳转到第二个页面，显示跳转。ResultActivity.class：第二个页面的activity
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        //[4]传递数据到第二个页面
        intent.putExtra("name", name);
        intent.putExtra("sex", sex);
        intent.putExtra("result", getResult());
        
        //[5]启动该activity
        startActivity(intent);
    }
    
    public String getResult() {
        Random random = new Random();
        int nextInt = random.nextInt(100);
        
        if(nextInt > 60) {
            return "运气太好";
        }else if(nextInt > 40) {
            return "运气一般";
        }else {
            return "运气太差";
        }
    }
}
