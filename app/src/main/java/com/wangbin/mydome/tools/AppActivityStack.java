package com.wangbin.mydome.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wangbin.mydome.MyApplication;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * 栈管理
 */
final public class AppActivityStack {
    private static AppActivityStack instance = null;
    private static Stack<Activity> activityStack;
//    private static Map<String, Activity> destroyMap = new HashMap<String, Activity>();

    private AppActivityStack() {
    }

    public static synchronized AppActivityStack create() {
        if (null == instance) {
            synchronized (AppActivityStack.class) {
                if (null == instance) {
                    instance = new AppActivityStack();
                }
            }
        }
        return instance;
    }


    public Stack<Activity> getStack() {
        return activityStack;
    }

    /**
     * 获取当前Activity栈中元素个数
     */
    public int getCount() {
        return activityStack.size();
    }

    /**
     * Activity栈中元素是否为空
     *
     * @return
     */
    private boolean isEmpty() {
        return activityStack == null || activityStack.isEmpty();
    }


    /**
     * Activity栈中元素是否只包含指定元素(集合为空也返回true)
     *
     * @return
     */
    public boolean onlyContains(Class<? extends Activity> claz) {
        boolean contains = false;
        if (isEmpty()) {
            contains = true;
        } else {
            if (activityStack.size() == 1) {
                contains = activityStack.get(0).getClass().getSimpleName().equals(claz.getSimpleName());
            }
        }
        return contains;
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        if (activity != null) {
            WeakReference<Activity> activityWeak = new WeakReference<Activity>(activity);
            activityStack.add(activityWeak.get());
//            addDestroyActivity(activity, activity.getClass().getSimpleName());
        }
    }

    /**
     * 添加Activity到栈
     */
    public void removeActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        if (activity != null) {
            WeakReference<Activity> activityWeak = new WeakReference<Activity>(activity);
            activityStack.remove(activityWeak.get());
        }
    }

    /**
     * 获取当前Activity（栈顶Activity）
     */
    public Activity topActivity() {
        if (activityStack == null) {
            throw new NullPointerException(
                    "Activity stack is Null,your Activity must extend KJActivity");
        }
        if (activityStack.isEmpty()) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 销毁指定Activity
     *
     * @param simpleActiviyName 要销毁的activity的简单类名
     */
    public void finishActivity(String simpleActiviyName) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().getSimpleName().equals(simpleActiviyName)) {
                activityStack.get(i).finish();
            }
        }
    }

    /**
     * 按从栈顶到栈底的顺序，销毁指定范围内的Activity，直到栈顶的那个元素为当前指定Activity时，（如果栈中不含该activity，则销毁所有的）
     *
     * @param claz         要销毁的activity的字节码对象
     * @param isFinishThis 是否移除当前传递的这个activity
     */
    public void finishActivityUntil(Class<?> claz, boolean isFinishThis) {
        if (activityStack == null || activityStack.isEmpty()) {
            return;
        }
        //当前指定的activity不为栈顶元素 则
        while (claz != activityStack.lastElement().getClass()) {
            //销毁最顶端的元素
            Activity topActivity = activityStack.pop();
            topActivity.finish();
            //为栈空则跳出
            if (activityStack.isEmpty()) {
                break;
            }
        }
        //经过前面的操作 只要不为空，最栈顶肯定是当前参数传入的这个activity
        if (isFinishThis && !activityStack.isEmpty()) {
            activityStack.pop().finish();
        }
    }

    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     */
    public Activity findActivity(Class<?> cls) {
        Activity activity = null;
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
                activity = activityStack.get(i);
                break;
            }
        }
        return activity;
    }

    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishTopActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            WeakReference<Activity> activityWeak = new WeakReference<Activity>(activity);
            activityStack.remove(activityWeak.get());
            if (!activity.isFinishing()) {
                activity.finish();// 此处不用finish
            }
            activity = null;
        }
    }

    /**
     * 结束指定的Activity(重载)
     */
    public synchronized void finishActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
                finishActivity(activityStack.get(i));
            }
        }
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     *
     * @param cls 当前activity的字节码对象
     */
    public void finishOthersActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (!(activityStack.get(i).getClass().equals(cls))) {
                finishActivity(activityStack.get(i));
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    // @Deprecated
    // public void AppExit(Context cxt) {
    // appExit(cxt);
    // }

    public void resetApp(Class<?> cls, Class<?> clsMain) {
        finishOthersActivity(cls);
        if (activityStack != null) {
            if (activityStack.size() > 0) {
                Activity activity = activityStack.lastElement();
                if (activity != null) {
                    Intent it = new Intent();
                    it.setClass(activity, clsMain);
                    activity.startActivity(it);
                }
            } else {
                Intent it = new Intent();
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                it.setClass(MyApplication.Companion.instance(), clsMain);
                MyApplication.Companion.instance().startActivity(it);
            }
        }

    }

    public void resetApp(Class<?> cls) {
        finishAllActivity();
        Intent it = new Intent();
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        it.setClass(MyApplication.Companion.instance(), cls);
        MyApplication.Companion.instance().startActivity(it);
    }

    public void restartApp(Class<?> cls) {
        Intent intent = new Intent(MyApplication.Companion.instance(), cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.Companion.instance().startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
    }

    /**
     * 应用程序退出
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            Runtime.getRuntime().exit(0);
        } catch (Exception e) {
            Runtime.getRuntime().exit(-1);
        }
    }
}
