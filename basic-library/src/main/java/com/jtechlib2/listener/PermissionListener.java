package com.jtechlib2.listener;

/**
 * 权限检查监听
 */
public interface PermissionListener {
    void result(boolean allGranted);
}