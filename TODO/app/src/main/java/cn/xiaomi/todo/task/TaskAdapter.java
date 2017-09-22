package cn.xiaomi.todo.task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.xiaomi.library.BaseAdapter;
import cn.xiaomi.todo.R;
import cn.xiaomi.todo.model.task.Task;

/**
 * Created by qiaohaibin on 21/09/2017.
 */

public class TaskAdapter extends BaseAdapter<Task, TaskAdapter.ViewHolder> {

    public TaskAdapter(List<Task> items) {
        super(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = getItem(position);

        TextView textView = (TextView) holder.itemView;
        textView.setText(task.getTitle());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
