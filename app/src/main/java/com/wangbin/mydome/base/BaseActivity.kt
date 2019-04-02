package com.wangbin.mydome.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.wangbin.mydome.interfaces.IViewSpecification
import com.wangbin.mydome.interfaces.PermissionListener
import com.wangbin.mydome.tools.AppActivityStack
import com.wangbin.mydome.tools.LoadingDialog
import com.wangbin.mydome.tools.ToastUtils
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * @ClassName BaseActivity
 * @Description 继承的BaseActivity
 * @Author WangBin
 * @Date 2019/3/22 15:42
 */
abstract class BaseActivity : AppCompatActivity(), IViewSpecification, View.OnClickListener {
    /***是否显示标题栏 */
    private var isshowtitle = true
    /***是否显示标题栏 */
    private var isshowstate = true
    /***获取TAG的activity名称 */
    protected val TAG = this.javaClass.simpleName
    /***权限监听 */
    private lateinit var mPermissionListener: PermissionListener
    /***显示转圈提示框 */
    private var progressDialog: Dialog? = null
    /*** 显示提示toast */
    protected var mToastUtils: ToastUtils? = null

    companion object {
        private const val REQUEST_CODE = 0 // 请求码
        /*** 所需的全部权限*/
        private val PERMISSIONS = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS)
    }

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
        mToastUtils = ToastUtils().create()
        AppActivityStack.create().addActivity(this)
        //设置布局
        setContentView(intiLayout())
        //初始化控件
        initView()
        setListener()
    }

    open fun initParams(arguments: Bundle?) {}

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
     * 显示等待框
     *
     * @param msg        展示内容
     * @param cancelable 设置返回键无效
     */
    fun showWaitingDialog(context: Context, msg: String, cancelable: Boolean) {
        if (progressDialog == null) {
            progressDialog = LoadingDialog.initProgressDialog(context, msg, cancelable)
        }
        if (progressDialog!!.isShowing) {
            return
        }
        progressDialog!!.show()
    }

    /**
     * 隐藏等待框
     */
    fun canelWaitingDialog() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
        }
    }

    /**
     * 给View赋点击事件
     *
     * @param view
     */
    fun onClickView(view: View) {
        view.setOnClickListener(this)
    }

    /**
     * 给View赋点击事件并防止重复点击
     *
     * @param view 传入的控件
     */
    @SuppressLint("CheckResult")
    fun notFastClick(view: View) {
        RxView.clicks(view)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe { onClick(view) }

    }

    override fun onClick(view: View) {
        widgetClick(view)
    }

    /**
     * 普通跳转Activity(不传参数值)
     *
     * @param cls 跳转的Activity
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

    fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        startActivityForResult(Intent(this, cls), requestCode)
    }

    /**
     * 获取EditText控件中的数据
     *
     * @param edit 传入为EditText
     */
    fun getText(edit: EditText): String {
        return edit.text.toString().trim { it <= ' ' }
    }

    /**
     * 获取TextView控件中的数据
     *
     * @param text 传入为TextView
     */
    fun getText(text: TextView): String {
        return text.text.toString().trim { it <= ' ' }
    }

    /**
     *  获取焦点并自动弹出键盘
     *
     *  @param actv 传入的控件
     */
    fun getFocus(actv: AutoCompleteTextView) {
        actv.isFocusable = true
        actv.isFocusableInTouchMode = true
        actv.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(actv, 0)
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
     * 设置view显示并销毁activity
     *
     * @param activity 传入要返回的Activity
     * @param view 给view设置显示状态，并添加点击事件销毁activity
     */
    fun finishThis(activity: Activity, view: View) {
        view.visibility = View.VISIBLE
        view.setOnClickListener {
            activity.finish()
        }
    }

    /**
     * 生命周期销毁 移除栈内资源
     */
    public override fun onDestroy() {
        super.onDestroy()
        //在onDestroy添加，防止空指针或者内存泄漏
        AppActivityStack.create().removeActivity(this)
        if (mToastUtils != null) {
            mToastUtils!!.destory()
        }
    }

}
