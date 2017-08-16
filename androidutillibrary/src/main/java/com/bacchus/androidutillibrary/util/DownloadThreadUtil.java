package com.bacchus.androidutillibrary.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Laiyin on 2017/8/3.
 * <p>实现自动下载和安装，并且有开始、下载和结束的回调，下载过程的回调带有进度参数
 *    使用时只需要开启线程实现回调即可
 */

public class DownloadThreadUtil extends Thread {

    private String urlpath; //请求apk下载地址

    private String filePath;  //存放apk文件夹的路径

    private String filePathApk; //存放apk文件的路径

    private String filePathApkName; //apk文件名

    private ThreadCallback mThreadCallback; //线程回调的地方

    private Context context;

    private int fileSize;

    private int downloadSize = 0;

    /**
     * 下载安装线程
     * @param context 上下文
     * @param threadCallback 线程回调的地方
     * @param url 请求下载路径
     * @param filePath 文件下载到客户端存放apk文件夹的路径
     * @param filePathApk 存放apk文件的路径
     * @param filePathApkName apk文件名
     */
    public DownloadThreadUtil(Context context, ThreadCallback threadCallback, String url, String filePath, String filePathApk, String filePathApkName) {
        this.urlpath = url;
        this.context = context;
        this.filePath = filePath;
        this.filePathApk = filePathApk;
        this.filePathApkName = filePathApkName;
        this.mThreadCallback = threadCallback;
    }

    @Override
    public void run() {
        try {
            mThreadCallback.threadStartListener();
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            fileSize = conn.getContentLength();
            if (fileSize < 1 || is == null) {
                throw new Exception();
            } else {
                //检测sdcard是否挂载
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    File file = new File(filePath, "apk");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File file1 = new File(filePathApk, filePathApkName);
                    if (file1.exists()) file1.delete();
                    file1.createNewFile();

                    FileOutputStream os = new FileOutputStream(file1);
                    byte[] array = new byte[1024];

                    int index = is.read(array);
                    while (index != -1) {
                        os.write(array, 0, index);
                        index = is.read(array);
                        downloadSize += index;
                        mThreadCallback.threadDownloadListener(downloadSize, fileSize);
                    }

                    if (os != null) {
                        os.flush();
                        os.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                    installApk(context, filePathApk, filePathApkName);
                    mThreadCallback.threadEndListener();
                }
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 回调接口 用来监听线程的开始/结束
     */
    public interface ThreadCallback {

        void threadStartListener();

        void threadDownloadListener(int downloadSize, int fileSize);

        void threadEndListener();

    }

    /**
     * 安装apk
     *
     * @param filePathApk     apk存放路径
     * @param filePathApkName apk文件名
     */
    public void installApk(Context context, String filePathApk, String filePathApkName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(filePathApk, filePathApkName)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}