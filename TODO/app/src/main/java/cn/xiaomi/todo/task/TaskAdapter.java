package cn.xiaomi.todo.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import cn.xiaomi.library.BaseAdapter;
import cn.xiaomi.todo.R;
import cn.xiaomi.todo.model.task.Task;

/**
 * Created by qiaohaibin on 21/09/2017.
 */

public class TaskAdapter extends BaseAdapter<Task, TaskAdapter.ViewHolder> {

    public static final int TAG_CHECK = 1;

    public TaskAdapter(List<Task> items, OnItemClickListener itemClickListener) {
        super(items, itemClickListener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(itemView, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = getItem(position);

        holder.cbCompleted.setChecked(task.isCompleted());
        holder.tvTitle.setText(task.getTitle());
    }

    static class ViewHolder extends BaseAdapter.ViewHolder {

        CheckBox cbCompleted;
        TextView tvTitle;

        public ViewHolder(View itemView, OnItemClickListener itemClickListener) {
            super(itemView, itemClickListener);
            cbCompleted = (CheckBox) itemView.findViewById(R.id.cbCompleted);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);

            cbCompleted.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            mItemClickListener.onItemClick(cbCompleted, getAdapterPosition(), TAG_CHECK);
        }
    }

}
