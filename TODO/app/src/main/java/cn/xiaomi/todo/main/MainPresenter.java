package cn.xiaomi.todo.main;

import android.content.Context;

import cn.xiaomi.todo.model.config.ConfigManager;

/**
 * Created by qiaohaibin on 20/09/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private Context mContext;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mContext = mView.getContext();
    }

    @Override
    public void start() {
        MainActionType actionType = ConfigManager.getInstance(mContext)
                .getLastMainActionType(MainActionType.MAIN_ACTION_TYPE_LIST);
        mView.onShow(actionType);
    }

    @Override
    public void changeMainAction(MainActionType actionType) {
        MainActionType lastActionType = ConfigManager.getInstance(mContext)
                .getLastMainActionType(MainActionType.MAIN_ACTION_TYPE_LIST);
        if (lastActionType != actionType) {
            ConfigManager.getInstance(mContext).setLastMainActionType(actionType);
            mView.onShow(actionType);
        }
    }
}
