package cn.xiaomi.todo.model.config;

import android.content.Context;

import cn.xiaomi.todo.main.MainActionType;

/**
 * Created by qiaohaibin on 20/09/2017.
 */

public class ConfigManager implements ConfigModel {

    private static ConfigManager sInstance = null;

    public static ConfigManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (ConfigManager.class) {
                if (sInstance == null) {
                    sInstance = new ConfigManager(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private ConfigModelImpl mConfigImpl;

    private ConfigManager(Context context) {
        mConfigImpl = new ConfigModelImpl(context);
    }

    @Override
    public void setLastMainActionType(MainActionType actionType) {
        mConfigImpl.setLastMainActionType(actionType);
    }

    @Override
    public MainActionType getLastMainActionType(MainActionType defaultActionType) {
        return mConfigImpl.getLastMainActionType(defaultActionType);
    }
}
