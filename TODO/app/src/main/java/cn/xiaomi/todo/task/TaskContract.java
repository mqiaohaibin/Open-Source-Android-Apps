package cn.xiaomi.todo.task;

import java.util.List;

import cn.xiaomi.todo.BaseDataView;
import cn.xiaomi.todo.BasePresenter;
import cn.xiaomi.todo.Datasource;
import cn.xiaomi.todo.model.task.Task;

/**
 * Created by qiaohaibin on 20/09/2017.
 */

public interface TaskContract {

    interface View extends BaseDataView {

        void onShow(List<Task> tasks);

    }

    interface Presenter extends BasePresenter {

        void refresh();

    }

}
