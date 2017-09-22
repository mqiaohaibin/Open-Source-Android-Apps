package cn.xiaomi.todo;

import android.app.Application;

/**
 * Created by qiaohaibin on 20/09/2017.
 */

public class App extends Application {

    private static App sInstance;

    public static App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
