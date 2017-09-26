package cn.xiaomi.todo.task;

import android.graphics.Bitmap;

import java.util.List;

import cn.xiaomi.todo.BaseDataView;
import cn.xiaomi.todo.BasePresenter;
import cn.xiaomi.todo.Datasource;
import cn.xiaomi.todo.model.task.Task;

/**
 * Created by qiaohaibin on 20/09/2017.
 */

public interface TaskContract {

    interface View extends BaseDataView<Task> {

        void onShowToast(String message);

        void onUpdateTask(Task task, int position);

    }

    interface Presenter extends BasePresenter {

        int FILTER_TYPE_ALL = 0;
        int FILTER_TYPE_ACTIVATE = 1;
        int FILTER_TYPE_COMPLETED = 2;

        void refresh();

        void completeTask(Task task, int position);
        void activateTask(Task task, int position);

        void filter(int type);
        void clearCompletedTask();

    }

}
