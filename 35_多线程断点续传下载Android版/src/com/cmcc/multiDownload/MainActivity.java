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

    private static int runningThread; // ��ʾ��ǰ�������е��߳�

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

        // [0]���һ�����ϣ������������������
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
            // ��pb��ӵ�������
            pbList.add(pb);
            // ��̬����ӽ�������LinearLayout��
            ll.addView(pb);
        }

        new Thread() {
            public void run() {
                try {
                    // [1]��ȡ�������ļ��Ĵ�С������ÿ���߳����صĿ�ʼλ�úͽ���λ��
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(3000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        // [1.1]��ȡ�ļ��ĳ���
                        int length = conn.getContentLength();
                        System.out.println("�ļ��ĳ���Ϊ��" + length);

                        // [2]����һ����С�ͷ�����һģһ�����ļ���Ŀ������ǰ�ѿռ��������
                        RandomAccessFile raf = new RandomAccessFile(
                                getFilename(path), "rw");
                        raf.setLength(length);

                        // [3]����ÿ���߳����صĿ�ʼλ�úͽ���λ��
                        // [3.1]ÿ���߳����ص��ļ���С
                        int blockSize = length / threadCount;
                        for (int i = 0; i < threadCount; i++) {
                            // [3.2]ÿ���߳����ص���ʼ�ڽ���λ��
                            int startIndex = i * blockSize;
                            int endIndex = (i + 1) * blockSize - 1;
                            // [3.3]������������һ���̵߳Ľ���λ�ÿ϶����ļ�ĩβ
                            if (i == threadCount - 1) {
                                endIndex = length - 1;
                            }

                            // [4]�����߳�ȥ�����������ļ�
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

        private int pbMaxSize; // ��ʾ��ǰ�߳����ص����ֵ

        private int pbLastPosition; // ����жϹ�����ȡ�ϴ����ص�λ��

        public ThreadDownload(int startIndex, int endIndex, int threadId) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.threadId = threadId;
        }

        @Override
        public void run() {
            try {
                // [4.1.0]���㵱ǰ�����������ֵ
                pbMaxSize = endIndex - startIndex;

                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);

                // [4.1.1]������Ҫ�жϴ洢����λ�õ��ļ��Ƿ����
                File file = new File(
                        getFilename(path) + "_" + threadId + ".txt");
                if (file.exists() && file.length() > 0) {
                    // �ļ�����
                    BufferedReader reader = new BufferedReader(
                            new FileReader(file));
                    String lastPosition = reader.readLine();
                    // [4.1.2]�����Ƕ���Ľ������ϴ����ص�λ�ø�ֵ
                    pbLastPosition = Integer.parseInt(lastPosition)
                            - startIndex;

                    // �����ص���ʼλ�û����ϴμ�¼��λ�õ���һλ
                    startIndex = Integer.parseInt(lastPosition);

                    System.out.println("�߳�" + threadId + "��ʵ����λ�ã�" + startIndex
                            + "------" + endIndex);
                    reader.close();
                }

                conn.setRequestProperty("Range",
                        "bytes=" + startIndex + "-" + endIndex);

                int code = conn.getResponseCode();
                // [4.2]200����ʾ��ȡ������ȫ����Դ�ɹ���206����ʾ��ȡ������������Դ�ɹ�
                if (code == 206) {
                    // [4.3]�õ��Ѿ��������ļ���׼��������д�����ݡ�ע�⣺����ÿ���߳��е�����ȡ�þ��������ʹ��ͬһ������֪��Ϊʲô
                    RandomAccessFile raf = new RandomAccessFile(
                            getFilename(path), "rw");
                    // [4.4]����ÿ���߳�Ҫ���Լ���λ�ÿ�ʼд
                    raf.seek(startIndex);

                    InputStream in = conn.getInputStream();

                    byte[] buffer = new byte[1024 * 1024];
                    int len = -1;
                    // [4.5.1]currentPosition������¼��ǰ�ӷ����������ص����ļ���λ��
                    int currentPosition = startIndex;
                    while ((len = in.read(buffer)) != -1) {
                        raf.write(buffer, 0, len);

                        currentPosition += len;
                        // [4.6]��λ����Ϣ�洢���ⲿ�ļ��С�ʹ��RandomAccessFile�࣬������File�࣬��ΪFile������Ƚ���Ϣ�洢�������У�һ���ϵ磬�����������洢��Ӳ���У���RandomAccessFile���Խ�������⣬"rwd"������ֱ�ӽ����ݴ洢��Ӳ���У�����������
                        RandomAccessFile raff = new RandomAccessFile(
                                getFilename(path) + "_" + threadId + ".txt",
                                "rwd");
                        raff.write((currentPosition + "").getBytes());
                        raff.close();

                        // [4.6.1]����һ�µ�ǰ�����������ֵ �� ��ǰ����
                        pbList.get(threadCount).setMax(pbMaxSize);
                        pbList.get(threadCount).setProgress(pbLastPosition);

                    }
                    raf.close();

                    System.out.println("�߳�" + threadId + "�������");

                    // [4.7]�������߳�������ɺ󣬰Ѵ洢����λ�õ��ļ�.txtɾ����ͨ��runningThread���ж��Ƿ������̶߳��������
                    runningThread--;
                    if (runningThread == 0) {
                        // �����̶߳�ִ�������
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

    // ��ȡ�ļ����֣�"http://localhost:8080/test.exe";
    public static String getFilename(String path) {
        return Environment.getExternalStorageDirectory().getPath() + "/"
                + path.substring(path.lastIndexOf("/") + 1);
    }
}
