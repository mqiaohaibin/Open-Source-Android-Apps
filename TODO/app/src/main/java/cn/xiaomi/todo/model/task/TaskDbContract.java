package cn.xiaomi.todo.model.task;

import android.provider.BaseColumns;

/**
 * Created by qiaohaibin on 21/09/2017.
 */

public final class TaskDbContract {

    private TaskDbContract() {}

    public static abstract class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_COMPLETED = "completed";
    }

}
