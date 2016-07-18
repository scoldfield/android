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

//文件的多线程下载
public class MultiDownload {

    private static String PATH = "http://localhost:8080/test.exe";
    private static final int THREADCOUNT = 3;   //开启3个线程用于下载
    
    public static void main(String[] args) {
        try {
            //[1]获取服务器文件的大小，计算每个线程下载的开始位置和结束位置
            URL url = new URL(PATH);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            int code = conn.getResponseCode();
            if(code == 200) {
                //[1.1]获取文件的长度
                int length = conn.getContentLength();
                System.out.println("文件的长度为："+length);
                
                //[2]创建一个大小和服务器一模一样的文件。目的是提前把空间申请出来
                RandomAccessFile raf = new RandomAccessFile("d:\\temp.exe", "rw");
                raf.setLength(length);

                //[3]计算每个线程下载的开始位置和结束位置
                //[3.1]每个线程下载的文件大小
                int blockSize = length / THREADCOUNT;
                for(int i = 0; i < THREADCOUNT; i++) {
                    //[3.2]每个线程下载的起始于结束位置
                    int startIndex = i * blockSize;
                    int endIndex = (i + 1) * blockSize - 1;
                    //[3.3]特殊情况：最后一个线程的结束位置肯定是文件末尾
                    if(i == THREADCOUNT - 1) {
                        endIndex = length - 1;
                    }
                    
                    //[4]开启线程去服务器下载文件
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
                //[4.1]设置一个请求头属性Range。告诉服务器从哪个位置开始下载，从哪个位置结束
                conn.setRequestProperty("Range", "bytes="+startIndex+"-"+endIndex);
                
                int code = conn.getResponseCode();
                //[4.2]200：表示获取服务器全部资源成功；206：表示获取服务器部分资源成功
                if(code == 206) {
                    //[4.3]拿到已经创建的文件，准备向其中写入数据。注意：必须每个线程中单独获取该句柄，不能使用同一个，不知道为什么
                    RandomAccessFile raf = new RandomAccessFile("d:\\temp.exe", "rw");
                    //[4.4]设置每个线程要从自己的位置开始写
                    raf.seek(startIndex);
                    
                    InputStream in = conn.getInputStream();
                    
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while((len = in.read(buffer)) != -1) {
                        raf.write(buffer, 0, len);
                    }
                    raf.close();
                    
                    System.out.println("线程"+threadId+"下载完成");
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
