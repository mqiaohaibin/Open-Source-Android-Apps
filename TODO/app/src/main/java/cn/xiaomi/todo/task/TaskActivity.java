package cn.xiaomi.todo.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import cn.xiaomi.todo.model.task.Task;

public class TaskActivity extends AppCompatActivity {

    public static final String EXTRA_TASK = "cn.xiaomi.todo.EXTRA_TASK";
    public static final String EXTRA_TYPE = "cn.xiaomi.todo.EXTRA_TYPE";
    public static final int EXTRA_TYPE_DETAIL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Task task = getIntent().getParcelableExtra(EXTRA_TASK);
        int taskType = getIntent().getIntExtra(EXTRA_TYPE, EXTRA_TYPE_DETAIL);

        Fragment fragment = null;

        FragmentManager manager = getSupportFragmentManager();
        if (taskType == EXTRA_TYPE_DETAIL) {
            fragment = TaskDetailFragment.newInstance(task);
        }
        manager.beginTransaction().replace(android.R.id.content, fragment).commit();
    }
}
