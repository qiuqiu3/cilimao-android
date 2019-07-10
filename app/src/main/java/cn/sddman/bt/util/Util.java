package cn.sddman.bt.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import org.xutils.x;

public class Util {

    public static boolean checkApkExist( String packageName){
        if (StringUtil.isEmpty(packageName))
            return false;
        try {
            ApplicationInfo info = x.app().getApplicationContext().getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void putTextIntoClip(String text){
        ClipboardManager clipboardManager = (ClipboardManager) x.app().getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(text, text);
        clipboardManager.setPrimaryClip(clipData);
    }
}
