package com.hndist.selectstoragelocation;

import android.content.Context;
import android.os.storage.StorageManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * 文件操作工具类
 * Created by Airn on 2017/9/29.
 */

public class FilesUtils {
    /**
     * 获取所有的存储路径（内部存储+外部存储）
     *
     * @param context 上下文
     * @return 存储目录数组
     */
    public static List<String> getAllSdPaths(Context context) {
        Method mMethodGetPaths = null;
        List<String> list = new ArrayList<>();
        //通过调用类的实例mStorageManager的getClass()获取StorageManager类对应的Class对象
        //getMethod("getVolumePaths")返回StorageManager类对应的Class对象的getVolumePaths方法，这里不带参数
        StorageManager mStorageManager = (StorageManager) context
                .getSystemService(Context.STORAGE_SERVICE);//storage
        try {
            String[] paths = null;
            mMethodGetPaths = mStorageManager.getClass().getMethod("getVolumePaths");
            paths = (String[]) mMethodGetPaths.invoke(mStorageManager);
            for (int i = 0; i < paths.length; i++) {
                list.add(paths[i]);
            }
            //获取是SD卡路径 ，为空说明不存在
            String outerSDCardBasePath = FilesUtils.getOuterSDCardBasePath();
            if ("".equals(outerSDCardBasePath)) {
                if (list.contains("/storage/sdcard1")) {
                    list.remove("/storage/sdcard1");
                }
            }
            if (list.contains("/storage/usbotg")) {
                list.remove("/storage/usbotg");
            }
            if (list.contains("/mydoc")) {
                list.remove("/mydoc");
            }
            if (list.contains("/mnt/udisk")) {
                list.remove("/mnt/udisk");
            }
            if (list.contains("/storage/Private")) {
                list.remove("/storage/Private");
            }
            if (list.contains("/storage/otg/sda")) {
                list.remove("/storage/otg/sda");
            }
            if (list.contains("/storage/otg/sdb")) {
                list.remove("/storage/otg/sdb");
            }
            if (list.contains("/storage/otg/sdc")) {
                list.remove("/storage/otg/sdc");
            }
            if (list.contains("/storage/otg/sdd")) {
                list.remove("/storage/otg/sdd");
            }
            if (list.contains("/storage/otg/sde")) {
                list.remove("/storage/otg/sde");
            }
            if (list.contains("/storage/otg/sdf")) {
                list.remove("/storage/otg/sdf");
            }
            if (list.contains("/storage/otg/sdg")) {
                list.remove("/storage/otg/sdg");
            }
            if (list.contains("/storage/otg/sdh")) {
                list.remove("/storage/otg/sdh");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            WriteLogUtils.writeLog("Exception-getAllSdPaths: "+e.toString());
        }

        return list;
    }

    /**
     * 第二种获取SD卡路径的方法，4.4以下的版本获取到的是 /storage/sdcard0
     * 4.4-6.0获取到的有可能是空字符串，所以需要二次判断
     *
     * @return 返回外置SD卡路径
     */
    public static String getOuterSDCardBasePath() {
        String tfPath = "";
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;
                if (line.contains("internal"))
                    continue;
                // E人E本 T7
                if (line.contains("mydoc"))
                    continue;
                if (line.contains("firmware"))
                    continue;
                // end
                if (line.contains("fat")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        tfPath = columns[1];
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            WriteLogUtils.writeLog("FileNotFoundException-getOuterSDCardBasePath: "+e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            WriteLogUtils.writeLog("IOException-getOuterSDCardBasePath: "+e.toString());
        }
        //过滤第二种方法  /mnt/udisk    /storage/usbotg    /storage/usbotg
        if ("".equals(tfPath) || "/storage/sdcard0".equals(tfPath)
                || "/storage/emulated/legacy".equals(tfPath)
                || "/storage/usbotg".equals(tfPath)
                || tfPath.contains("/storage/otg")) {
            tfPath = "";
        } else {
            tfPath = "/storage/" + tfPath.substring(tfPath.lastIndexOf("/") + 1);
        }
        return tfPath;
    }
}
