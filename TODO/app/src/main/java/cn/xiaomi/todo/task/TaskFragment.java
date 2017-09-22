package cn.xiaomi.todo.task;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.xiaomi.library.LineItemDecoration;
import cn.xiaomi.library.SpacingItemDecoration;
import cn.xiaomi.todo.R;
import cn.xiaomi.todo.main.MainActivity;
import cn.xiaomi.todo.model.task.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment implements TaskContract.View, View.OnClickListener
        , SwipeRefreshLayout.OnRefreshListener {

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

        mTaskAdapter = new TaskAdapter(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRefreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_task, container, false);
        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) mRefreshLayout.findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mTaskAdapter);
        mRecyclerView.addItemDecoration(new LineItemDecoration(getResources().getDimensionPixelSize(R.dimen.app_line), 1));
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionButton:

                break;
        }
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onShow(List<Task> tasks) {
        mTaskAdapter.setItems(tasks);
        mTaskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShow(Status status) {
        int statusType = status.mType;
        switch (statusType) {
            case Status.STATUS_TYPE_LOADING:
                mRefreshLayout.setRefreshing(true);
                break;
            case Status.STATUS_TYPE_SUCCESS:
                mRefreshLayout.setRefreshing(false);
                mRecyclerView.setVisibility(View.VISIBLE);
                mStatusView.setVisibility(View.GONE);
                break;
            case Status.STATUS_TYPE_EMPTY:
                mRefreshLayout.setRefreshing(false);
                mRecyclerView.setVisibility(View.GONE);
                mStatusView.setVisibility(View.VISIBLE);
                mStatusIconView.setImageResource(status.mResourceId);
                mStatusTitleView.setText(status.mMessage);
                break;
            case Status.STATUS_TYPE_ERROR:
                mRefreshLayout.setRefreshing(false);
                mRecyclerView.setVisibility(View.GONE);
                mStatusView.setVisibility(View.VISIBLE);
                mStatusIconView.setImageResource(status.mResourceId);
                mStatusTitleView.setText(status.mMessage);
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }
}
