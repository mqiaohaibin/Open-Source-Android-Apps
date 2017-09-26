package cn.xiaomi.library;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by qiaohaibin on 21/09/2017.
 */

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public static final int TAG_ITEM_CLICK = 0;

    protected List<T> mItems;
    protected OnItemClickListener mItemClickListener;

    public BaseAdapter(List<T> items, OnItemClickListener itemClickListener) {
        mItems = items;
        mItemClickListener = itemClickListener;
    }

    public void setItems(List<T> items) {
        mItems = items;
    }

    public List<T> getItems() {
        return mItems;
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


    public interface OnItemClickListener {
        void onItemClick(View view, int position, int tag);
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected OnItemClickListener mItemClickListener;

        CheckBox cbCompleted;
        TextView tvTitle;

        public ViewHolder(View itemView, OnItemClickListener itemClickListener) {
            super(itemView);
            mItemClickListener = itemClickListener;
            if (mItemClickListener != null) {
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            if (view == itemView) {
                mItemClickListener.onItemClick(itemView, getAdapterPosition(), TAG_ITEM_CLICK);
            }
        }
    }

}
