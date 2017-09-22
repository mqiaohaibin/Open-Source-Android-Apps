package cn.xiaomi.todo.main;

import cn.xiaomi.todo.BasePresenter;
import cn.xiaomi.todo.BaseView;

/**
 * Created by qiaohaibin on 20/09/2017.
 */

public interface MainContract {

    interface View extends BaseView {
        void onShow(MainActionType actionType);
    }

    interface Presenter extends BasePresenter {
        void changeMainAction(MainActionType actionType);
    }

}
