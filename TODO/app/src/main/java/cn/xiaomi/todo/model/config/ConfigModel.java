package cn.xiaomi.todo.model.config;

import cn.xiaomi.todo.main.MainActionType;

/**
 * Created by qiaohaibin on 20/09/2017.
 */

public interface ConfigModel {

    void setLastMainActionType(MainActionType actionType);

    MainActionType getLastMainActionType(MainActionType defaultActionType);

}
