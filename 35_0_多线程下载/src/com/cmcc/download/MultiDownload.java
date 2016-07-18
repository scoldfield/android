package com.cmcc.download;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.cmcc.download.MultiDownload.ThreadDownload;

//�ļ��Ķ��߳�����
public class MultiDownload {

    private static String PATH = "http://localhost:8080/test.exe";
    private static final int THREADCOUNT = 3;   //����3���߳���������
    
    public static void main(String[] args) {
        try {
            //[1]��ȡ�������ļ��Ĵ�С������ÿ���߳����صĿ�ʼλ�úͽ���λ��
            URL url = new URL(PATH);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            int code = conn.getResponseCode();
            if(code == 200) {
                //[1.1]��ȡ�ļ��ĳ���
                int length = conn.getContentLength();
                System.out.println("�ļ��ĳ���Ϊ��"+length);
                
                //[2]����һ����С�ͷ�����һģһ�����ļ���Ŀ������ǰ�ѿռ��������
                RandomAccessFile raf = new RandomAccessFile("d:\\temp.exe", "rw");
                raf.setLength(length);

                //[3]����ÿ���߳����صĿ�ʼλ�úͽ���λ��
                //[3.1]ÿ���߳����ص��ļ���С
                int blockSize = length / THREADCOUNT;
                for(int i = 0; i < THREADCOUNT; i++) {
                    //[3.2]ÿ���߳����ص���ʼ�ڽ���λ��
                    int startIndex = i * blockSize;
                    int endIndex = (i + 1) * blockSize - 1;
                    //[3.3]������������һ���̵߳Ľ���λ�ÿ϶����ļ�ĩβ
                    if(i == THREADCOUNT - 1) {
                        endIndex = length - 1;
                    }
                    
                    //[4]�����߳�ȥ�����������ļ�
                    ThreadDownload thread = new ThreadDownload(startIndex, endIndex, i);
                    thread.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ThreadDownload extends Thread{
        
        private int startIndex;
        private int endIndex;
        private int threadId;
//        private RandomAccessFile raf;
        
        public ThreadDownload(int startIndex, int endIndex, int threadId) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.threadId = threadId;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(PATH);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                //[4.1]����һ������ͷ����Range�����߷��������ĸ�λ�ÿ�ʼ���أ����ĸ�λ�ý���
                conn.setRequestProperty("Range", "bytes="+startIndex+"-"+endIndex);
                
                int code = conn.getResponseCode();
                //[4.2]200����ʾ��ȡ������ȫ����Դ�ɹ���206����ʾ��ȡ������������Դ�ɹ�
                if(code == 206) {
                    //[4.3]�õ��Ѿ��������ļ���׼��������д�����ݡ�ע�⣺����ÿ���߳��е�����ȡ�þ��������ʹ��ͬһ������֪��Ϊʲô
                    RandomAccessFile raf = new RandomAccessFile("d:\\temp.exe", "rw");
                    //[4.4]����ÿ���߳�Ҫ���Լ���λ�ÿ�ʼд
                    raf.seek(startIndex);
                    
                    InputStream in = conn.getInputStream();
                    
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while((len = in.read(buffer)) != -1) {
                        raf.write(buffer, 0, len);
                    }
                    raf.close();
                    
                    System.out.println("�߳�"+threadId+"�������");
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
