package cn.xiaomi.todo.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import cn.xiaomi.todo.Constants;
import cn.xiaomi.todo.Datasource;
import cn.xiaomi.todo.R;
import cn.xiaomi.todo.model.task.Task;
import cn.xiaomi.todo.model.task.TaskDatabase;
import cn.xiaomi.todo.model.task.TaskRepository;

/**
 * Created by qiaohaibin on 20/09/2017.
 */

public class TaskPresenter implements TaskContract.Presenter {

    private final int REQUEST_TASK_ADD = 1;

    private Context mContext;
    private TaskContract.View mView;
    private TaskDatabase mDatasource;
    private List<Task> mCachedDatas;
    private int mCurrentFilterType = FILTER_TYPE_ALL;

    public TaskPresenter(TaskContract.View view) {
        mView = view;
        mContext = mView.getContext();
        mDatasource = new TaskRepository(mContext);
    }

    @Override
    public void start() {
        load(true, true);
    }

    @Override
    public void refresh() {
        load(true, true);
    }

    @Override
    public void completeTask(final Task task, final int position) {
        if (!task.isCompleted()) {
            //TODO error use
            task.setCompleted(true);
            mDatasource.update(task, new Datasource.Callback1() {
                @Override
                public void success() {

                }

                @Override
                public void fail(int code, String error) {
                    task.setCompleted(false);
                    mView.onUpdateTask(task, position);
                    mView.onShowToast("更新数据失败");
                }
            });
        }
    }

    @Override
    public void activateTask(final Task task, final int position) {
        if (task.isCompleted()) {
            mDatasource.activeTask(task, new Datasource.Callback<Task>() {
                @Override
                public void success(List<Task> datas) {
                    int cachePosition = mCachedDatas.indexOf(task);
                    mCachedDatas.set(cachePosition, datas.get(0));
                    mView.onUpdateTask(datas.get(0), position);
                }

                @Override
                public void fail(int code, String error) {
                    mView.onUpdateTask(task, position);
                    mView.onShowToast("更新数据失败");
                }
            });
        }
    }

    @Override
    public void filter(int type) {
        if (mCurrentFilterType != type) {
            mCurrentFilterType = type;
            load(false, false);
        }
    }

    @Override
    public void clearCompletedTask() {
        mView.onShow(true);

        mDatasource.clearCompletedTask(new Datasource.Callback1() {
            @Override
            public void success() {
                clearCompletedTask(mCachedDatas);
                List<Task> datas = filter(mCachedDatas, mCurrentFilterType);
                if (datas == null || datas.size() == 0) {
                    mView.onShow(R.mipmap.logo, R.string.task_noTask);
                } else {
                    mView.onShow(datas);
                }
                mView.onShow(false);
            }

            @Override
            public void fail(int code, String error) {
                mView.onShowToast(error);
                mView.onShow(false);
            }
        });
    }

    private void clearCompletedTask(List<Task> datas) {
        for (int i = datas.size() - 1; i >= 0; i--) {
            Task task = datas.get(i);
            if (task.isCompleted()) {
                datas.remove(i);
            }
        }
    }

    private void load(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mView.onShow(true);
        }

        if (!forceUpdate) {
            List<Task> datas = filter(mCachedDatas, mCurrentFilterType);
            if (datas != null && datas.size() > 0) {
                mView.onShow(datas);
            } else {
                mView.onShow(R.mipmap.logo, R.string.task_noTask);
            }

            if (showLoadingUI) {
                mView.onShow(false);
            }
            return;
        }

        mDatasource.load(0, 0, new Datasource.Callback<Task>() {
            @Override
            public void success(List<Task> datas) {
                mCachedDatas = datas;
                datas = filter(datas, mCurrentFilterType);
                if (datas == null || datas.size() == 0) {
                    mView.onShow(R.mipmap.logo, R.string.task_noTask);
                } else {
                    mView.onShow(datas);
                }

                if (showLoadingUI) {
                    mView.onShow(false);
                }
            }

            @Override
            public void fail(int code, String error) {
                mCachedDatas = null;
                mView.onShow(R.mipmap.logo, R.string.error_getData);
                if (showLoadingUI) {
                    mView.onShow(false);
                }
            }
        });
    }

    private List<Task> filter(List<Task> tasks, int filterType) {
        if (tasks == null || tasks.size() == 0) {
            return null;
        }

        List<Task> datas = new ArrayList<>(tasks.size());
        for (Task task : tasks) {
            if (task.isCompleted() && (filterType == FILTER_TYPE_COMPLETED || filterType == FILTER_TYPE_ALL)) {
                datas.add(task);
            } else if (!task.isCompleted() && (filterType == FILTER_TYPE_ACTIVATE || filterType == FILTER_TYPE_ALL)) {
                datas.add(task);
            }
        }

        return datas;
    }

    @Override
    public void detailTask(Task task, int position) {
        mView.onDetailTask(task, position);
    }

    @Override
    public void deleteTask(final Task task, final int position) {
        mView.onShow(true);

        mDatasource.delete(task, new Datasource.Callback1() {
            @Override
            public void success() {
                mCachedDatas.remove(task);
                List<Task> datas = filter(mCachedDatas, mCurrentFilterType);
                if (datas == null || datas.size() == 0) {
                    mView.onShow(R.mipmap.logo, R.string.task_noTask);
                } else {
                    mView.onDeleteTask(task, position);
                }
                mView.onShow(false);
            }

            @Override
            public void fail(int code, String error) {
                mView.onShowToast("删除数据失败");
                mView.onShow(false);
            }
        });
    }

    @Override
    public void addTask() {
        mView.onShowAddTaskForResult(REQUEST_TASK_ADD);
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TASK_ADD && resultCode == Activity.RESULT_OK) {
            Task task = data.getParcelableExtra(Constants.Intent.EXTRA_TASK);
            if (mCachedDatas == null) {
                mCachedDatas = new ArrayList<>(1);
                mCachedDatas.add(0, task);
                List<Task> datas = filter(mCachedDatas, mCurrentFilterType);
                if (datas != null && datas.size() == 1) {
                    mView.onShow(datas);
                }
            } else {
                mCachedDatas.add(0, task);
                List<Task> datas = new ArrayList<>(1);
                datas.add(task);
                datas = filter(datas, mCurrentFilterType);
                if (datas != null && datas.size() == 1) {
                    mView.onAddTask(task, 0);
                }
            }
        }
    }
}
