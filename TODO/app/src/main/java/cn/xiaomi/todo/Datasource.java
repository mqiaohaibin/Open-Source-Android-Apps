package cn.xiaomi.todo;

import java.util.List;

/**
 * Created by qiaohaibin on 21/09/2017.
 */

public interface Datasource<T> {

    interface Callback<M> {
        void success(List<M> datas);
        void fail(int code, String error);
    }

    interface Callback1 {
        void success();
        void fail(int code, String error);
    }

    void insert(T data, Callback1 callback);

    void delete(T data, Callback1 callback);

    void update(T data, Callback1 callback);

    void load(int from, int size, Callback<T> callback);

}
