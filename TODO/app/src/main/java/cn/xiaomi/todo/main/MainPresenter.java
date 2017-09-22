package cn.xiaomi.todo.main;


import android.content.Context;
import android.os.Handler;
import android.os.Message;

import cn.xiaomi.todo.App;
import cn.xiaomi.todo.model.config.ConfigManager;

/**
 * Created by qiaohaibin on 20/09/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    private final int WHAT_SHOW = 0;

    private MainContract.View mView;
    private Context mContext;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_SHOW) {
                mView.onShow((MainActionType) msg.obj);
            }
        }
    };

    public MainPresenter(MainContract.View view) {
        mView = view;
        mContext = mView.getContext();
    }

    @Override
    public void start() {
        MainActionType actionType = ConfigManager.getInstance(mContext)
                .getLastMainActionType(MainActionType.MAIN_ACTION_TYPE_LIST);
        mHandler.obtainMessage(WHAT_SHOW, actionType).sendToTarget();
    }

    @Override
    public void changeMainAction(MainActionType actionType) {
        MainActionType lastActionType = ConfigManager.getInstance(mContext)
                .getLastMainActionType(MainActionType.MAIN_ACTION_TYPE_LIST);
        if (lastActionType != actionType) {
            ConfigManager.getInstance(App.getInstance()).setLastMainActionType(actionType);
            mHandler.obtainMessage(WHAT_SHOW, actionType).sendToTarget();
        }
    }
}
