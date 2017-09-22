package cn.xiaomi.todo.main;

/**
 * Created by qiaohaibin on 20/09/2017.
 */

public enum MainActionType {
    MAIN_ACTION_TYPE_LIST, MAIN_ACTION_TYPE_STATISTICS;

    public static MainActionType valueOf(int ordinal) {
        switch (ordinal) {
            case 1:
                return MAIN_ACTION_TYPE_STATISTICS;
            default:
            case 0:
                return MAIN_ACTION_TYPE_LIST;
        }
    }
}
