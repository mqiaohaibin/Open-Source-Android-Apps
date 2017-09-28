package cn.xiaomi.todo.task;

import cn.xiaomi.todo.BasePresenter;
import cn.xiaomi.todo.BaseView;
import cn.xiaomi.todo.Datasource;
import cn.xiaomi.todo.model.task.Task;
import cn.xiaomi.todo.model.task.TaskDatabase;
import cn.xiaomi.todo.model.task.TaskRepository;

/**
 * Created by qiaohaibin on 28/09/2017.
 */

public class TaskEditPresenter implements BasePresenter{

    private boolean mReturnResult;

    private View mView;
    private TaskDatabase mTaskDatabase;

    public TaskEditPresenter(View view, boolean returnResult) {
        mView = view;
        mReturnResult = returnResult;
        mTaskDatabase = new TaskRepository(mView.getContext());
    }

    @Override
    public void start() {

    }

    public void addTask(String title, String description, boolean completed) {
        final Task task = new Task(title, description, completed);
        mTaskDatabase.insert(task, new Datasource.Callback1() {
            @Override
            public void success() {
//                mView.onShow("添加成功");
                mView.onBack(task);
            }

            @Override
            public void fail(int code, String error) {
                mView.onShow("添加失败");
            }
        });
    }


    public interface View extends BaseView{

        void onShow(String toast);

        void onBack(Task task);

    }
}
