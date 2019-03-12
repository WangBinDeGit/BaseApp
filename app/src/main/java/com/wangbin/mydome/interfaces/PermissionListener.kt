package com.wangbin.mydome.interfaces

/**
 * @author chaychan
 * @description: 权限申请回调的接口
 */
interface PermissionListener {

    fun onGranted()

    fun onDenied(deniedPermissions: List<String>)
}
