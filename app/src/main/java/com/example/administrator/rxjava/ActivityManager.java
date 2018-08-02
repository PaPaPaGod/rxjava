package com.example.administrator.rxjava;

import android.app.Activity;
import android.util.Log;

import java.util.Iterator;
import java.util.Stack;

public class ActivityManager {
    private static ActivityManager instance;
    private static Stack<Activity> mStack;
    private Stack<Activity> deleteActivity = new Stack<>();
    private ActivityManager(){

    }

    public static synchronized final ActivityManager getInstance(){
        if(instance==null){
            synchronized (ActivityManager.class){
                if(mStack==null){
                    mStack = new Stack<>();
                }
            }
        }
        return instance;
    }

    public Stack<Activity> getmStack() {
        return mStack;
    }

    //返回当前的activity
    public Activity currentActivity(){
        if(!mStack.empty()){
            return mStack.lastElement();
        }
        return null;
    }

    //栈内是否包含此activity
    public boolean isContain(Class<?> cls){
        for(Activity a:mStack){
            if(a.getClass().equals(cls)){
                return true;
            }
        }
        return false;
    }

    //栈内是否包含此activity
    public boolean isContain(Activity activity){
        for(Activity a:mStack){
            if(a.equals(activity)){
                return true;
            }
        }
        return false;
    }

    //入栈
    public void pushActivity(Activity activity){
        if(mStack!=null){
            mStack.add(activity);
        }
    }

    //    移除栈顶第一个activity
    public void popTopActivity() {
        Activity activity = mStack.lastElement();
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }

    /**
     * activity出栈
     * 一般在baseActivity的onDestroy里面加入
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            mStack.remove(activity);
        }
        if (!activity.isFinishing()) {
            activity.finish();
            activity = null;
        }
    }

    /**
     * activity出栈
     * 一般在baseActivity的onDestroy里面加入
     */
    public void popActivity(Class<?> cls) {
        Activity deleteActivity = null;
        for (Activity activity : mStack) {
            if (activity.getClass().equals(cls) && !activity.isFinishing()) {
                deleteActivity = activity;
                activity.finish();
            }
        }
        mStack.remove(deleteActivity);
    }


    /**
     * 从栈顶往下移除 直到cls这个activity为止
     * 如： 现有ABCD popAllActivityUntillOne(B.class)
     * 则： 还有AB存在
     * <p>
     * 注意此方法 会把自身也finish掉
     *
     * @param cls
     */
    public void popAllActivityUntillOne(Class cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 所有的栈元素 除了 cls的留下 其他全部移除
     * 如： 现有ABCD popAllActivityUntillOne(B.class)
     * 则： 只有B存在
     * 注意此方法 会把自身也finish掉
     */
    public void popAllActivityExceptOne(Class cls) {
        //第一种  ConcurrentModificationException
//        for (Activity activity : mStack) {
//            if (!activity.getClass().equals(cls) && !activity.isFinishing()) {
//                mStack.remove(activity);
//                activity.finish();
//            }
//        }

        // 第四种 ConcurrentModificationException
//        for (int i = 0; i < ; i++) {

        // 第三种 可行
        Iterator iterator = mStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = (Activity) iterator.next();
            if (!activity.getClass().equals(cls) && !activity.isFinishing()) {
//                mStack.remove(activity);
//               注意这里必须要用iterator的remove 上面的则错误
                iterator.remove();
                activity.finish();
            }
        }

        //第四种 可行 稍显复杂
//        for (Activity activity : mStack) {
//            if (!activity.getClass().equals(cls) && !activity.isFinishing()) {
//                deleteStack.add(activity);
//                activity.finish();
//            }
//        }
//        /**
//         * 这里进行了特殊处理，如果直接在循环里面remove会报
//         * concurrentmodificationexception 错误
//         * 所以，这里用另一个栈加入进去，统一移除
//         */
//        mStack.removeAll(deleteStack);
//        deleteStack.clear();
        Log.e("ActivityManager", "dsfsaf size+:" + mStack.size());
    }

    /**
     * 移除所有的activity
     * 退出应用的时候可以调用
     * （非杀死进程）
     */
    public void popAllActivity() {
        for (int i = 0; i < mStack.size(); i++) {
            if (null != mStack.get(i) && !mStack.get(i).isFinishing()) {
                mStack.get(i).finish();
            }
        }
        mStack.clear();
    }


    /**
     * 获得现在栈内还有多少activity
     *
     * @return
     */
    public int getCount() {
        if (mStack != null) {
            return mStack.size();
        }
        return 0;
    }

}
