package com.example.testAndroidProject;

import android.test.AndroidTestCase;

//�����ࡣ����̳�AndroidTestCase�࣬�����嵥�ļ�AndroidManifest.xml�н�������
public class CalcTest extends AndroidTestCase {
    
    public void testAdd() {
        Calc c = new Calc();
        int res = c.add(5, 6);
        assertEquals(11, res);
    }
}
