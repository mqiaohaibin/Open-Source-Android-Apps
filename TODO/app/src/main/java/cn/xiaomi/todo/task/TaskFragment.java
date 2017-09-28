package cn.xiaomi.todo.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.xiaomi.library.BaseAdapter;
import cn.xiaomi.library.LineItemDecoration;
import cn.xiaomi.todo.Constants;
import cn.xiaomi.todo.R;
import cn.xiaomi.todo.ResultActivity;
import cn.xiaomi.todo.main.MainActivity;
import cn.xiaomi.todo.model.task.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment implements TaskContract.View, View.OnClickListener
        , SwipeRefreshLayout.OnRefreshListener, BaseAdapter.OnItemClickListener {

    private static final String TAG = "TaskFragment";

    private TaskContract.Presenter mPresenter;

    private SwipeRefreshLayout mRefreshLayout;

    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;

    private View mStatusView;
    private ImageView mStatusIconView;
    private TextView mStatusTitleView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(R.string.task_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRefreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_task, container, false);
        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) mRefreshLayout.findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        int spacing = getResources().getDimensionPixelSize(R.dimen.app_line);

        mRecyclerView.addItemDecoration(new LineItemDecoration(spacing, 1));
        mTaskAdapter = new TaskAdapter(null, this);
        mRecyclerView.setAdapter(mTaskAdapter);

        registerForContextMenu(mRecyclerView);

        mStatusView = mRefreshLayout.findViewById(R.id.mStatusView);
        mStatusIconView = (ImageView) mStatusView.findViewById(R.id.mStatusIconView);
        mStatusTitleView = (TextView) mStatusView.findViewById(R.id.mStatusTitleView);

        if (getActivity() instanceof MainActivity) {
            FloatingActionButton actionButton = (FloatingActionButton) getActivity()
                    .findViewById(R.id.actionButton);
            actionButton.setImageResource(R.drawable.ic_add);
            actionButton.setVisibility(View.VISIBLE);
            actionButton.setOnClickListener(this);
        }

        mPresenter = new TaskPresenter(this);
        mPresenter.start();

        return mRefreshLayout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actions_task, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (getActivity() instanceof MainActivity && menu.findItem(R.id.action_add) != null) {
            menu.removeItem(R.id.action_add);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                mPresenter.addTask();
                return true;
            case R.id.action_filter:
                PopupMenu popupMenu = new PopupMenu(getContext(),
                        getActivity().findViewById(R.id.action_filter));
                popupMenu.getMenuInflater().inflate(R.menu.actions_filter_task, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_filter_active:
                                mPresenter.filter(TaskContract.Presenter.FILTER_TYPE_ACTIVATE);
                                return true;
                            case R.id.action_filter_completed:
                                mPresenter.filter(TaskContract.Presenter.FILTER_TYPE_COMPLETED);
                                return true;
                        }
                        mPresenter.filter(TaskContract.Presenter.FILTER_TYPE_ALL);
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            case R.id.action_clear:
                mPresenter.clearCompletedTask();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, R.id.action_delete, Menu.NONE, R.string.action_delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            mPresenter.deleteTask(mTaskAdapter.getItem(menuInfo.position), menuInfo.position);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionButton:
                mPresenter.addTask();
                break;
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onShow(boolean loading) {
        mRefreshLayout.setRefreshing(loading);
    }

    @Override
    public void onShow(List<Task> tasks) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mStatusView.setVisibility(View.GONE);
        mTaskAdapter.setItems(tasks);
        mTaskAdapter.notifyDataSetChanged();
        registerForContextMenu(mRecyclerView);
    }

    @Override
    public void onShow(int statusIcon, int statusMessage) {
        mRecyclerView.setVisibility(View.GONE);
        mStatusView.setVisibility(View.VISIBLE);
        mStatusIconView.setImageResource(statusIcon);
        mStatusTitleView.setText(statusMessage);
    }

    @Override
    public void onShowToast(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onUpdateTask(Task task, int position) {
        mTaskAdapter.getItems().set(position, task);
        //调用这个方法，会出现一条线(目前没有解决办法)
        //mTaskAdapter.notifyItemChanged(position);
        mTaskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteTask(Task task, int position) {
        mTaskAdapter.getItems().remove(task);
        mTaskAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void onItemClick(View view, int position, int tag) {
        Task task = mTaskAdapter.getItem(position);
        if (tag == TaskAdapter.TAG_CHECK) {
            if (((CheckBox) view).isChecked()) {
                mPresenter.completeTask(task, position);
            } else {
                mPresenter.activateTask(task, position);
            }
        } else {
            mPresenter.detailTask(task, position);
        }
    }

    @Override
    public void onDetailTask(Task task, int position) {
        Intent intent = new Intent(getContext(), TaskActivity.class);
        intent.putExtra(Constants.Intent.EXTRA_TASK, task);
        intent.putExtra(Constants.Intent.EXTRA_TYPE, Constants.Intent.EXTRA_TYPE_TASK_DETAIL);
        startActivity(intent);
    }

    @Override
    public void onShowAddTaskForResult(int requestCode) {
        Intent intent = new Intent(getContext(), ResultActivity.class);
        intent.putExtra(Constants.Intent.EXTRA_TYPE, Constants.Intent.EXTRA_TYPE_TASK_ADD);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onAddTask(Task task, int position) {
        mTaskAdapter.getItems().add(position, task);
        mTaskAdapter.notifyItemInserted(position);
        mRecyclerView.scrollToPosition(position);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.result(requestCode, resultCode, data);
    }

    public static class TaskAdapter extends BaseAdapter<Task, TaskAdapter.ViewHolder> {

        public static final int TAG_CHECK = 1;

        public TaskAdapter(List<Task> items, OnItemClickListener itemClickListener) {
            super(items, itemClickListener);
        }

        @Override
        public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
            return new TaskAdapter.ViewHolder(itemView, mItemClickListener);
        }

        @Override
        public void onBindViewHolder(TaskAdapter.ViewHolder holder, int position) {
            Task task = getItem(position);

            holder.cbCompleted.setChecked(task.isCompleted());
            holder.tvTitle.setText(task.getTitle());
        }

        static class ViewHolder extends BaseAdapter.ViewHolder {

            CheckBox cbCompleted;
            TextView tvTitle;

            public ViewHolder(final View itemView, OnItemClickListener itemClickListener) {
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
}
