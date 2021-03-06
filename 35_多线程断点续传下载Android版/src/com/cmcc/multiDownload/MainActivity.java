package com.cmcc.multiDownload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    private EditText tv_path;

    private EditText tv_thread;

    private LinearLayout ll;

    private static int runningThread; // 表示当前正在运行的线程

    private String path;

    private String threadCnt;

    private int threadCount;

    private List<ProgressBar> pbList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_path = (EditText) findViewById(R.id.tv_path);
        tv_thread = (EditText) findViewById(R.id.tv_thread);
        ll = (LinearLayout) findViewById(R.id.ll);

        // [0]添加一个集合，用来存进度条的引用
        pbList = new ArrayList<ProgressBar>();

    }

    public void click(View v) {
        path = tv_path.getText().toString().trim();
        threadCnt = tv_thread.getText().toString().trim();
        threadCount = Integer.parseInt(threadCnt);
        runningThread = threadCount;

        for (int i = 0; i < threadCount; i++) {
            ProgressBar pb = (ProgressBar) View.inflate(getApplicationContext(),
                    R.layout.item, null);
            // 把pb添加到集合中
            pbList.add(pb);
            // 动态的添加进度条到LinearLayout中
            ll.addView(pb);
        }

        new Thread() {
            public void run() {
                try {
                    // [1]获取服务器文件的大小，计算每个线程下载的开始位置和结束位置
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(3000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        // [1.1]获取文件的长度
                        int length = conn.getContentLength();
                        System.out.println("文件的长度为：" + length);

                        // [2]创建一个大小和服务器一模一样的文件。目的是提前把空间申请出来
                        RandomAccessFile raf = new RandomAccessFile(
                                getFilename(path), "rw");
                        raf.setLength(length);

                        // [3]计算每个线程下载的开始位置和结束位置
                        // [3.1]每个线程下载的文件大小
                        int blockSize = length / threadCount;
                        for (int i = 0; i < threadCount; i++) {
                            // [3.2]每个线程下载的起始于结束位置
                            int startIndex = i * blockSize;
                            int endIndex = (i + 1) * blockSize - 1;
                            // [3.3]特殊情况：最后一个线程的结束位置肯定是文件末尾
                            if (i == threadCount - 1) {
                                endIndex = length - 1;
                            }

                            // [4]开启线程去服务器下载文件
                            ThreadDownload thread = new ThreadDownload(
                                    startIndex, endIndex, i);
                            thread.start();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            };
        }.start();
    }

    public class ThreadDownload extends Thread {

        private int startIndex;

        private int endIndex;

        private int threadId;

        private int pbMaxSize; // 表示当前线程下载的最大值

        private int pbLastPosition; // 如果中断过，获取上次下载的位置

        public ThreadDownload(int startIndex, int endIndex, int threadId) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.threadId = threadId;
        }

        @Override
        public void run() {
            try {
                // [4.1.0]计算当前进度条的最大值
                pbMaxSize = endIndex - startIndex;

                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);

                // [4.1.1]这里需要判断存储下载位置的文件是否存在
                File file = new File(
                        getFilename(path) + "_" + threadId + ".txt");
                if (file.exists() && file.length() > 0) {
                    // 文件存在
                    BufferedReader reader = new BufferedReader(
                            new FileReader(file));
                    String lastPosition = reader.readLine();
                    // [4.1.2]给我们定义的进度条上次下载的位置赋值
                    pbLastPosition = Integer.parseInt(lastPosition)
                            - startIndex;

                    // 将下载的起始位置换成上次记录的位置的下一位
                    startIndex = Integer.parseInt(lastPosition);

                    System.out.println("线程" + threadId + "真实下载位置：" + startIndex
                            + "------" + endIndex);
                    reader.close();
                }

                conn.setRequestProperty("Range",
                        "bytes=" + startIndex + "-" + endIndex);

                int code = conn.getResponseCode();
                // [4.2]200：表示获取服务器全部资源成功；206：表示获取服务器部分资源成功
                if (code == 206) {
                    // [4.3]拿到已经创建的文件，准备向其中写入数据。注意：必须每个线程中单独获取该句柄，不能使用同一个，不知道为什么
                    RandomAccessFile raf = new RandomAccessFile(
                            getFilename(path), "rw");
                    // [4.4]设置每个线程要从自己的位置开始写
                    raf.seek(startIndex);

                    InputStream in = conn.getInputStream();

                    byte[] buffer = new byte[1024 * 1024];
                    int len = -1;
                    // [4.5.1]currentPosition用来记录当前从服务器上下载到的文件的位置
                    int currentPosition = startIndex;
                    while ((len = in.read(buffer)) != -1) {
                        raf.write(buffer, 0, len);

                        currentPosition += len;
                        // [4.6]将位置信息存储到外部文件中。使用RandomAccessFile类，而不是File类，因为File类会首先将信息存储到缓存中，一旦断电，可能来不及存储到硬盘中；而RandomAccessFile可以解决此问题，"rwd"方法会直接将数据存储到硬盘中，不经过缓存
                        RandomAccessFile raff = new RandomAccessFile(
                                getFilename(path) + "_" + threadId + ".txt",
                                "rwd");
                        raff.write((currentPosition + "").getBytes());
                        raff.close();

                        // [4.6.1]设置一下当前进度条的最大值 和 当前进度
                        pbList.get(threadCount).setMax(pbMaxSize);
                        pbList.get(threadCount).setProgress(pbLastPosition);

                    }
                    raf.close();

                    System.out.println("线程" + threadId + "下载完成");

                    // [4.7]当所有线程下载完成后，把存储下载位置的文件.txt删除。通过runningThread来判断是否所有线程都下载完成
                    runningThread--;
                    if (runningThread == 0) {
                        // 所有线程都执行完毕了
                        for (int i = 0; i < threadCount; i++) {
                            File file1 = new File(
                                    getFilename(path) + "_" + i + ".txt");
                            file1.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 获取文件名字："http://localhost:8080/test.exe";
    public static String getFilename(String path) {
        return Environment.getExternalStorageDirectory().getPath() + "/"
                + path.substring(path.lastIndexOf("/") + 1);
    }
}
