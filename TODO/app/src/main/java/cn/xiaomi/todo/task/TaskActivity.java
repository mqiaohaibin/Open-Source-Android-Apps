package cn.xiaomi.todo.task;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import cn.xiaomi.todo.model.task.Task;

import cn.xiaomi.todo.Constants;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Task task = getIntent().getParcelableExtra(Constants.Intent.EXTRA_TASK);
        int taskType = getIntent().getIntExtra(Constants.Intent.EXTRA_TYPE, Constants.Intent.EXTRA_TYPE_NULL);

        Fragment fragment = null;
        if (taskType == Constants.Intent.EXTRA_TYPE_TASK_DETAIL) {
            fragment = TaskDetailFragment.newInstance(task);
        }

        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(android.R.id.content, fragment).commit();
        }
    }
}
