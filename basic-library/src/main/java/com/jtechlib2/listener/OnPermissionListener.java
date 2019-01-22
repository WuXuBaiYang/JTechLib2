package com.jtechlib2.listener;

/**
 * 权限检查监听
 */
public interface OnPermissionListener {
    void result(boolean allGranted);
}