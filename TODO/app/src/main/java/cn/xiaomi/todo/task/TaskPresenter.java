package cn.xiaomi.todo.task;


import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;

import java.util.List;

import cn.xiaomi.todo.BaseDataView;
import cn.xiaomi.todo.Datasource;
import cn.xiaomi.todo.R;
import cn.xiaomi.todo.model.task.Task;
import cn.xiaomi.todo.model.task.TaskRepository;

/**
 * Created by qiaohaibin on 20/09/2017.
 */

public class TaskPresenter implements TaskContract.Presenter {

    private final int WHAT_STATUS = 0;
    private final int WHAT_DATA = 1;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_STATUS) {
                mView.onShow((BaseDataView.Status) msg.obj);
            } else if (msg.what == WHAT_DATA) {
                mView.onShow(BaseDataView.Status.obtain(BaseDataView.Status.STATUS_TYPE_SUCCESS));
                mView.onShow((List<Task>) msg.obj);
            }
        }
    };

    private Context mContext;
    private TaskContract.View mView;
    private Datasource<Task> mDatasource;

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
        load(true, false);
    }

    private void load(boolean forceUpdate, boolean showLoadingUI) {
        if (showLoadingUI) {
            BaseDataView.Status stataus = BaseDataView.Status.obtain(BaseDataView.Status.STATUS_TYPE_LOADING);
            mHandler.obtainMessage(WHAT_STATUS, stataus).sendToTarget();
        }

        mDatasource.load(0, 0, new Datasource.Callback<Task>() {
            @Override
            public void success(List<Task> datas) {
                if (datas == null || datas.size() == 0) {
                    BaseDataView.Status stataus = BaseDataView.Status.obtain(
                            BaseDataView.Status.STATUS_TYPE_EMPTY,
                            R.mipmap.logo, mContext.getResources().getString(R.string.task_noTask));
                    mHandler.obtainMessage(WHAT_STATUS, stataus).sendToTarget();
                } else {
                    mHandler.obtainMessage(WHAT_DATA, datas).sendToTarget();
                }
            }

            @Override
            public void fail(int code, String error) {
                BaseDataView.Status stataus = BaseDataView.Status.obtain(BaseDataView.Status.STATUS_TYPE_LOADING,
                        R.mipmap.logo, error);
                mHandler.obtainMessage(WHAT_STATUS, stataus).sendToTarget();
            }
        });
    }
}
