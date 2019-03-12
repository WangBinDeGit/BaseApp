package com.wangbin.mydome.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.wangbin.mydome.interfaces.IViewSpecification
import com.wangbin.mydome.interfaces.PermissionListener
import com.wangbin.mydome.tools.AppActivityStack
import com.wangbin.mydome.tools.LoadingDialog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by WangBin.
 * on 2018/8/2
 * 继承的BaseActivity
 */
abstract class BaseActivity : AppCompatActivity(), IViewSpecification, View.OnClickListener {
    /***是否显示标题栏 */
    private var isshowtitle = true
    /***是否显示标题栏 */
    private var isshowstate = true
    /***显示转圈提示框 */
    private var progressDialog: Dialog? = null
    /***显示统一提示框 */
    private var alertDialog: AlertDialog.Builder? = null
    /***获取TAG的activity名称 */
    protected val TAG = this.javaClass.simpleName
    /***权限监听 */
    private lateinit var mPermissionListener: PermissionListener

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isshowtitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }

        if (isshowstate) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        if (savedInstanceState != null) {
            initParams(savedInstanceState)
        } else if (intent != null && intent.extras != null) {
            initParams(intent.extras)
        }
        AppActivityStack.create().addActivity(this)
        //设置布局
        setContentView(intiLayout())
        //初始化控件
        initView()
        setListener()
    }

    open fun initParams(arguments: Bundle?) {
    }

    /**
     * 是否设置标题栏
     *
     * @return
     */
    fun setTitle(ishow: Boolean) {
        isshowtitle = ishow
    }

    /**
     * 设置是否显示状态栏
     *
     * @param ishow
     */
    fun setState(ishow: Boolean) {
        isshowstate = ishow
    }

    /**
     * 显示长toast
     *
     * @param msg
     */
    fun toastLong(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    /**
     * 显示短toast
     *
     * @param msg
     */
    fun toastShort(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 给View赋点击事件
     *
     * @param view
     */
    fun onClickView(view: View) {
        view.setOnClickListener(this)
    }

    fun notFastClick(view: View) {
        RxView.clicks(view)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(object : Observer<Any> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onNext(o: Any) {
                        onClick(view)
                    }
                })

    }

    override fun onClick(view: View) {
        widgetClick(view)
    }

    /**
     * 普通跳转Activity(不传值)
     *
     * @param cls
     */
    fun startActivity(cls: Class<*>) {
        startActivity(Intent(this, cls))
    }

    /**
     * 跳转Activity(传Bundle)
     *
     * @param cls
     * @param bundle
     */
    fun startActivity(cls: Class<*>, bundle: Bundle) {
        startActivity(Intent(this, cls).putExtras(bundle))
    }

    /**
     * 跳转startActivityForResult(传Bundle)
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    fun startActivityForResult(cls: Class<*>, bundle: Bundle, requestCode: Int) {
        val intent = Intent()
        intent.setClass(this, cls)
        intent.putExtras(bundle)
        startActivityForResult(intent, requestCode)
    }

    fun finishThis(context: Activity, view: View) {
        view.visibility = View.VISIBLE
        view.setOnClickListener {
            context.finish()
        }
    }

    /**
     * 获取EditText控件中的数据
     *
     * @param edit
     */
    fun getText(edit: EditText): String {
        return edit.text.toString().trim { it <= ' ' }
    }

    /**
     * 获取TextView控件中的数据
     * @param text
     */
    fun getText(text: TextView): String {
        return text.text.toString().trim { it <= ' ' }
    }

    /**
     * @param msg        展示内容
     * @param cancelable 设置返回键无效
     */
    fun showWaitingDialog(msg: String, cancelable: Boolean) {
        progressDialog = LoadingDialog.innitProgressDiag(this, msg, cancelable)
        progressDialog!!.show()
    }

    fun canelWaitingDialog() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
        }
    }

    /**
     * 统一弹窗
     *
     * @param message 展示信息
     * @param title   标题
     */
    fun alertText(title: String, message: String, clickListener: DialogInterface.OnClickListener) {
        this.runOnUiThread {
            alertDialog = AlertDialog.Builder(this@BaseActivity)
            alertDialog!!.setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("确定", clickListener)
                    .setNegativeButton("取消", clickListener)
                    .show()
        }
    }

    //获取焦点并自动弹出键盘
    fun getFocus(actv: AutoCompleteTextView) {
        actv.isFocusable = true
        actv.isFocusableInTouchMode = true
        actv.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(actv, 0)
    }

    /**
     * 申请运行时权限
     */
    fun requestRuntimePermission(permissions: Array<String>, permissionListener: PermissionListener) {
        mPermissionListener = permissionListener
        val permissionList = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission)
            }
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), 1)
        } else {
            permissionListener.onGranted()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> if (grantResults.isNotEmpty()) {
                val deniedPermissions = ArrayList<String>()
                for (i in grantResults.indices) {
                    val grantResult = grantResults[i]
                    val permission = permissions[i]
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        deniedPermissions.add(permission)
                    }
                }
                if (deniedPermissions.isEmpty()) {
                    mPermissionListener.onGranted()
                } else {
                    mPermissionListener.onDenied(deniedPermissions)
                }
            }
        }
    }

    /**
     * 生命周期销毁
     */
    public override fun onDestroy() {
        super.onDestroy()
        //在onDestroy添加，防止空指针或者内存泄漏
        AppActivityStack.create().removeActivity(this)
    }

}
