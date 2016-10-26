/**      
 * ToastUtil.java Create on 2013-6-29     
 */

package com.benli.bltoast;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * 
* @ClassName: ToastUtil 
* @Description: TODO(...) 
* @author shibenli
* @date 2015年9月28日 下午12:58:22
 */
public class ToastUtil {

    private static Handler ToastHandler = null;
    
    /**
     * 初始换方法，越早调用越好，推荐在Application的onCreate方法中调用
     * @param context
     * @return
     */
    public static Toast initToast(Context context) {
        if (ToastHandler == null) {
            currentToast = Toast.makeText(context.getApplicationContext(), "", 1000);
            ToastHandler = new Handler(){
                public void handleMessage(android.os.Message msg) {
                    currentToast.show();
                }
            };
        }
        
        return currentToast;
    }

    public static void showToast(Context context, CharSequence  msg, int duration) {
        showToast(context, msg, duration, 0);
    }
    
    /**
     * 发送延时Toast
     * @param context
     * @param msg
     * @param duration
     * @param delay
     */
    public static void showToast(Context context, CharSequence  msg, int duration, long delay) {
        if (currentToast == null && context != null) {
            currentToast = makeText(context, msg, duration);
        } else {
            currentToast.setText(msg);
        }
        currentToast.setGravity(Gravity.CENTER, 0, 0);
        delay %= 1000;
        ToastHandler.sendEmptyMessageDelayed(0, delay);
    }

    public static void showToast(Context context, CharSequence  msg) {
        try {
            if (context != null) {
                showToast(context, msg, 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void showToast(Context context, int id) {
        try {
            if (context != null) {
                showToast(context, context.getResources().getString(id), 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Toast currentToast;

    private static View toastView;

    /**
     * 使用同1个toast,避免多toast重复问题
     * 
     * @param context
     * @param text
     * @param duration
     * @return
     */
    public static Toast makeText(Context context, CharSequence text, int duration) {
        if (currentToast == null && context != null) {
            currentToast = initToast(context);
        } 
        
        toastView = currentToast.getView();
        
        if (toastView != null) {
            currentToast.setView(toastView);
            currentToast.setText(text);
            currentToast.setGravity(Gravity.CENTER, 0, 0);
            currentToast.setDuration(duration);
        }
        return currentToast;
    }

    public static void cancelAll() {
        if (currentToast!=null) {
            try {
                currentToast.cancel();
            } catch (Exception e) {
            }
        }
    }
}

