package cn.xiaomi.todo.task;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import cn.xiaomi.todo.R;
import cn.xiaomi.todo.model.task.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment implements View.OnClickListener, TaskDetailPresenter.View {

    public static TaskDetailFragment newInstance(Task task) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.mPresenter = new TaskDetailPresenter(fragment, task);
        return fragment;
    }

    private TaskDetailPresenter mPresenter;

    private TextView tvTitle;
    private TextView tvDescription;
    private CheckBox cbCompleted;

    @Override
    public void onStart() {
        super.onStart();

        getActivity().setTitle(R.string.taskDetail_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        cbCompleted = (CheckBox) view.findViewById(R.id.cbCompleted);
        cbCompleted.setOnClickListener(this);

        mPresenter.start();

        return view;
    }

    @Override
    public void onShow(Task task) {
        tvTitle.setText(task.getTitle());
        tvDescription.setText(task.getDescription());
        cbCompleted.setChecked(task.isCompleted());
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
