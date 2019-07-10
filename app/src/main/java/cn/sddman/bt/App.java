package cn.sddman.bt;

import android.app.Application;

import org.xutils.x;

public class App extends Application {
    public static App instance = null;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        instance = this;
    }
    public static App appInstance() {
        return instance;
    }

}
