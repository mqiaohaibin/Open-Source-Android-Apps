package cn.xiaomi.todo.task;

import cn.xiaomi.todo.BasePresenter;
import cn.xiaomi.todo.BaseView;
import cn.xiaomi.todo.model.task.Task;

/**
 * Created by qiaohaibin on 26/09/2017.
 */

public class TaskDetailPresenter implements BasePresenter {

    private Task mTask;
    private View mView;

    public TaskDetailPresenter(View view, Task task) {
        mView = view;
        mTask = task;
    }

    @Override
    public void start() {
        mView.onShow(mTask);
    }

    public interface View extends BaseView {

        void onShow(Task task);

    }

}
