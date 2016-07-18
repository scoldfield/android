package com.example.testAndroidProject;

import android.test.AndroidTestCase;

//测试类。必须继承AndroidTestCase类，且在清单文件AndroidManifest.xml中进行引入
public class CalcTest extends AndroidTestCase {
    
    public void testAdd() {
        Calc c = new Calc();
        int res = c.add(5, 6);
        assertEquals(11, res);
    }
}
