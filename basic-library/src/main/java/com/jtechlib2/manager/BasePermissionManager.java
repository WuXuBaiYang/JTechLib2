package com.jtechlib2.manager;

import android.app.Activity;

import com.jtechlib2.listener.PermissionListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

/**
 * 权限检查管理
 * Created by wuxubaiyang on 2017/8/17.
 */
public class BasePermissionManager {
    /**
     * 检查单个权限
     *
     * @param activity   activity
     * @param permission 权限
     * @param listener   回调
     */
    public static void checkSingle(Activity activity, String permission, final PermissionListener listener) {
        Dexter.withActivity(activity)
                .withPermission(permission)
                .withListener(new com.karumi.dexter.listener.single.PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (null != listener) {
                            listener.result(true);
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (null != listener) {
                            listener.result(false);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    /**
     * 权限检查
     *
     * @param activity    activity
     * @param permissions 权限数组
     * @param listener    回调
     */
    public static void checkMultiple(Activity activity, String[] permissions, final PermissionListener listener) {
        Dexter.withActivity(activity)
                .withPermissions(permissions)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (null != listener) {
                            listener.result(report.areAllPermissionsGranted());
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
}