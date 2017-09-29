package cn.xiaomi.todo.statistics;

import java.util.List;

import cn.xiaomi.todo.BasePresenter;
import cn.xiaomi.todo.BaseView;
import cn.xiaomi.todo.Datasource;
import cn.xiaomi.todo.model.task.TaskDatabase;
import cn.xiaomi.todo.model.task.TaskRepository;

/**
 * Created by qiaohaibin on 29/09/2017.
 */

public class StatisticsPresenter implements BasePresenter {

    private View mView;
    private TaskDatabase mTaskDatabase;

    public StatisticsPresenter(View view) {
        mView = view;
        mTaskDatabase = new TaskRepository(mView.getContext());
    }

    @Override
    public void start() {
        mView.onShowStatistics(0, 0);
        mTaskDatabase.taskCounts(new Datasource.Callback<Integer>() {
            @Override
            public void success(List<Integer> datas) {
                if (datas != null && datas.size() == 2) {
                    mView.onShowStatistics(datas.get(0), datas.get(1));
                }
            }

            @Override
            public void fail(int code, String error) {

            }
        });
    }

    public interface View extends BaseView {

        void onShowStatistics(int activeTaskCount, int completedTaskCount);

    }
}
