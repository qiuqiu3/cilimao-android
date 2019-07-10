package cn.sddman.bt.util;

import android.app.Activity;

import com.irozon.sneaker.Sneaker;

import cn.sddman.bt.R;
import cn.sddman.bt.common.AppManager;
import cn.sddman.bt.common.Const;

public class AlertUtil {
   
    public static void successToast(String msg){
        Toast(AppManager.getAppManager().currentActivity(), msg,Const.SUCCESS_ALERT);
    }
    public static void errorToast(String msg){
        Toast(AppManager.getAppManager().currentActivity(), msg,Const.ERROR_ALERT);
    }
    public static void warningToast(String msg){
        Toast(AppManager.getAppManager().currentActivity(), msg,Const.WARNING_ALERT);
    }
    public static void Toast(Activity activity, String msg, int msgType){
        if(Const.ERROR_ALERT==msgType) {
            Sneaker.with(activity)
                    .setTitle(activity.getResources().getString(R.string.title_dialog), R.color.white)
                    .setMessage(msg, R.color.white)
                    .setDuration(2000)
                    .autoHide(true)
                    .setIcon(R.drawable.ic_error, R.color.white, false)
                    .sneak(R.color.colorAccent);
        }else if(Const.SUCCESS_ALERT==msgType) {
            Sneaker.with(activity)
                    .setTitle(activity.getResources().getString(R.string.title_dialog), R.color.white)
                    .setMessage(msg, R.color.white)
                    .setDuration(2000)
                    .autoHide(true)
                    .setIcon(R.drawable.ic_success, R.color.white, false)
                    .sneak(R.color.success);
        }else if(Const.WARNING_ALERT==msgType) {
            Sneaker.with(activity)
                    .setTitle(activity.getResources().getString(R.string.title_dialog), R.color.white)
                    .setMessage(msg, R.color.white)
                    .setDuration(2000)
                    .autoHide(true)
                    .setIcon(R.drawable.ic_warning, R.color.white, false)
                    .sneak(R.color.warning);
        }
    }
}
