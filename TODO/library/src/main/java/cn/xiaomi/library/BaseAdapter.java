package cn.xiaomi.library;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by qiaohaibin on 21/09/2017.
 */

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    List<T> mItems;

    public BaseAdapter(List<T> items) {
        mItems = items;
    }

    public void setItems(List<T> items) {
        mItems = items;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public final T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

}
