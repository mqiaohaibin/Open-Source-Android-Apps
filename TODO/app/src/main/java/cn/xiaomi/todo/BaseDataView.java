package cn.xiaomi.todo;

import android.net.wifi.WifiConfiguration;

/**
 * Created by qiaohaibin on 21/09/2017.
 */

public interface BaseDataView extends BaseView {

    public static class Status {

        public static final int STATUS_TYPE_LOADING = 1;
        public static final int STATUS_TYPE_SUCCESS = 2;
        public static final int STATUS_TYPE_EMPTY = 0;
        public static final int STATUS_TYPE_ERROR = -1;

        public int mType;
        public int mResourceId;
        public String mMessage;

        public static Status obtain(int type) {
            Status status = new Status();
            status.mType = type;
            return status;
        }

        public static Status obtain(int type, int resourceId, String message) {
            Status status = new Status();
            status.mType = type;
            status.mResourceId = resourceId;
            status.mMessage = message;
            return status;
        }

    }

    void onShow(Status status);

}
