package cn.xiaomi.todo;

import java.util.List;

/**
 * Created by qiaohaibin on 21/09/2017.
 */

public interface BaseDataView<T> extends BaseView {

    void onShow(boolean loading);

    void onShow(List<T> datas);

    // datas empty(contains no data and fail to get datas)
    void onShow(int statusIcon, int statusMessage);

}
