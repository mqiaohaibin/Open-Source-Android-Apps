package cn.xiaomi.todo.model.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.xiaomi.todo.Datasource;

/**
 * Created by qiaohaibin on 21/09/2017.
 */

public class TaskRepository implements Datasource<Task> {

    private TaskDbHelper mDbHelper;

    public TaskRepository(Context context) {
        mDbHelper = new TaskDbHelper(context);
    }

    private void dealCallback1(Callback1 callback, boolean success, String error) {
        if (callback != null && success) {
            callback.success();
        } else if (callback != null) {
            callback.fail(-1, error);
        }
    }


    @Override
    public void insert(Task data, Callback1 callback) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskDbContract.TaskEntry.COLUMN_NAME_ENTRY_ID, data.getId());
        values.put(TaskDbContract.TaskEntry.COLUMN_NAME_TITLE, data.getTitle());
        values.put(TaskDbContract.TaskEntry.COLUMN_NAME_DESCRIPTION, data.getDescription());
        values.put(TaskDbContract.TaskEntry.COLUMN_NAME_COMPLETED, data.isCompleted());

        long result = db.insert(TaskDbContract.TaskEntry.TABLE_NAME, null, values);
        dealCallback1(callback, result != -1, "插入失败");

        db.close();
    }

    @Override
    public void delete(Task data, Callback1 callback) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String whereClause = TaskDbContract.TaskEntry.COLUMN_NAME_ENTRY_ID + "=?";
        String[] whereArgs = {data.getId()};

        int result = db.delete(TaskDbContract.TaskEntry.TABLE_NAME, whereClause, whereArgs);
        dealCallback1(callback, result != 0, "删除失败");

        db.close();
    }

    @Override
    public void update(Task data, Callback1 callback) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskDbContract.TaskEntry.COLUMN_NAME_COMPLETED, data.isCompleted());

        String whereClause = TaskDbContract.TaskEntry.COLUMN_NAME_ENTRY_ID + "=?";
        String[] whereArgs = {data.getId()};

        int result = db.update(TaskDbContract.TaskEntry.TABLE_NAME, values, whereClause, whereArgs);
        dealCallback1(callback, result != 0, "更新失败");

        db.close();
    }

    @Override
    public void load(int offset, int limit, Callback<Task> callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String sql = "select " +
                TaskDbContract.TaskEntry.COLUMN_NAME_ENTRY_ID + "," +
                TaskDbContract.TaskEntry.COLUMN_NAME_TITLE + "," +
                TaskDbContract.TaskEntry.COLUMN_NAME_DESCRIPTION + "," +
                TaskDbContract.TaskEntry.COLUMN_NAME_COMPLETED + " " +
                "from " + TaskDbContract.TaskEntry.TABLE_NAME;

        if (offset > 0) {
            sql += " offset " + offset;
        }

        if (limit > 0) {
            sql += " limit " + limit;
        }

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {
            List<Task> datas = null;
            if (cursor.getCount() > 0) {
                datas = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    String entryId = cursor.getString(cursor.getColumnIndex(TaskDbContract.TaskEntry.COLUMN_NAME_ENTRY_ID));
                    String title = cursor.getString(cursor.getColumnIndex(TaskDbContract.TaskEntry.COLUMN_NAME_TITLE));
                    String description = cursor.getString(cursor.getColumnIndex(TaskDbContract.TaskEntry.COLUMN_NAME_DESCRIPTION));
                    boolean completed = cursor.getInt(cursor.getColumnIndex(TaskDbContract.TaskEntry.COLUMN_NAME_COMPLETED)) == 1;
                    datas.add(new Task(entryId, title, description, completed));
                }
            } else {
                datas = new ArrayList<>(10);
                for (int i = 0;i < 10; i++) {
                    datas.add(new Task(i + "", "title" + i, "description" + i, i % 2 == 1));
                }
            }

            if (callback != null) {
                callback.success(datas);
            }
        } else if(callback != null){
            callback.fail(-1, "读取失败");
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();
    }
}
