package com.hndist.selectstoragelocation;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * 检查权限的工具类
 * Created by Airn on 16/1/26.
 */
public class PermissionsChecker {
    private final Context mContext;

    public PermissionsChecker(Context context) {
        //获取应用的上下文
        mContext = context.getApplicationContext();
    }

    /**
     * 判断是否缺少权限
     *
     * @param permissions 权限集合
     * @return 是否缺少
     */
    public boolean lacksPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission) ==
                    PackageManager.PERMISSION_DENIED) {
                return true;
            }
        }
        return false;
    }
}
