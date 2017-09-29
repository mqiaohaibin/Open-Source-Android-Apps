package cn.xiaomi.todo.model.task;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qiaohaibin on 21/09/2017.
 */

public class TaskDbHelper extends SQLiteOpenHelper {

    public static final int DATBASE_VERSION = 1;
    public static final String DATABASE_NAME = "tasks.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TaskDbContract.TaskEntry.TABLE_NAME + " (" +
                    TaskDbContract.TaskEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + " PRIMARY KEY," +
                    TaskDbContract.TaskEntry.COLUMN_NAME_TITLE + TEXT_TYPE + "," +
                    TaskDbContract.TaskEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + "," +
                    TaskDbContract.TaskEntry.COLUMN_NAME_COMPLETED + BOOLEAN_TYPE + ")";

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATBASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}
