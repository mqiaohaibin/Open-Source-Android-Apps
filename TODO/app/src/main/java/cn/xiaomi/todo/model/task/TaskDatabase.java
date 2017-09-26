package cn.xiaomi.todo.model.task;

import cn.xiaomi.todo.Datasource;

/**
 * Created by qiaohaibin on 25/09/2017.
 */

public interface TaskDatabase extends Datasource<Task>{

    void clearCompletedTask(Callback<Task> callback);

    void activeTask(Task task, Callback<Task> callback);

}
