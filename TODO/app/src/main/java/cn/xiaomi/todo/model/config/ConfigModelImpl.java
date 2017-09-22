package cn.xiaomi.todo.model.config;

import android.content.Context;
import android.content.SharedPreferences;

import cn.xiaomi.todo.main.MainActionType;

/**
 * Created by qiaohaibin on 20/09/2017.
 */

public class ConfigModelImpl implements ConfigModel {

    private final String PREFS_NAME = "pref_properties";

    private final String PREFS_KEY_lastMainActionType = "lastMainActionType";

    private Context mContext;

    ConfigModelImpl(Context context) {
        mContext = context;
    }

    @Override
    public void setLastMainActionType(MainActionType actionType) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(PREFS_KEY_lastMainActionType, actionType.ordinal());
        editor.commit();
    }

    @Override
    public MainActionType getLastMainActionType(MainActionType defaultActionType) {
        SharedPreferences preferences = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int ordinal = preferences.getInt(PREFS_KEY_lastMainActionType, defaultActionType.ordinal());
        return MainActionType.valueOf(ordinal);
    }
}
