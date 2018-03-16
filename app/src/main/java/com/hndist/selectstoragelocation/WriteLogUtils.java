package com.hndist.selectstoragelocation;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 写日志
 * Created by Airn on 2017/3/22.
 */

public class WriteLogUtils {

    public static void writeLog(String content) {
        SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("CHN"));
        SimpleDateFormat TimeFormat2 = new SimpleDateFormat("yyyyMMdd", new Locale("CHN"));
        String timeDirName = TimeFormat2.format(System.currentTimeMillis());
        String logTime = TimeFormat.format(System.currentTimeMillis());
        String basePath = Environment.getExternalStorageDirectory() + "/Airn";
        basePath = basePath + "/log";
        File dir3 = new File(basePath);
        if (!dir3.exists())
            dir3.mkdirs();
        basePath = basePath + "/";
        String filePath = basePath + timeDirName;
        String fileName = "log.txt";
        writeTxtToFile(content, filePath, fileName, logTime);
    }

    // 将字符串写入到文本文件中
    private static void writeTxtToFile(String content, String filePath, String fileName, String logTime) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);
        String strFilePath = filePath + File.separator + fileName;
        // 每次写入时，都换行写
        String strContent = "\r\n" + logTime + ":" + "\r\n" + content + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.e("TAG", "Create the file:" + strFilePath);
                makeFilePath(filePath, fileName);
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TAG", "Error on write File" + e);
        }
    }

    /**
     * 生成文件
     *
     * @param filePath 文件夹名称
     * @param fileName 文件名称
     * @return 返回文件
     */
    private static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + File.separator + fileName);
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (newFile) {
                    Log.e("TAG", "创建文件成功");
                } else {
                    Log.e("TAG", "创建文件失败");
                }
            }
        } catch (Exception e) {
            Log.e("TAG", "创建文件异常");
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 生成文件夹
     *
     * @param filePath 文件夹名称
     */
    private static void makeRootDirectory(String filePath) {
        File file = null;

        try {
            String basePath = Environment.getExternalStorageDirectory() + "/Airn/log";
            //            String basePath = "/storage/sdcard1" + "/DIST_File";
            File baseFilePath = new File(basePath);
            if (!baseFilePath.exists()) {
                boolean mkdir = baseFilePath.mkdir();
                if (mkdir) {
                    Log.e("TAG", "创建文件夹成功");
                } else {
                    Log.e("TAG", "创建文件夹失败");
                }
            }
            file = new File(filePath);
            if (!file.exists()) {
                boolean mkdir = file.mkdir();
                if (mkdir) {
                    Log.e("TAG", "创建文件夹成功");
                } else {
                    Log.e("TAG", "创建文件夹失败");
                }
            }
        } catch (Exception e) {
            Log.e("TAG", "生成文件异常--" + e);
        }
    }
}
