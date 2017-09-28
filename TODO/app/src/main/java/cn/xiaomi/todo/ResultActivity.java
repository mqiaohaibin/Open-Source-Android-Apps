package cn.xiaomi.todo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.xiaomi.todo.model.task.Task;
import cn.xiaomi.todo.task.TaskDetailFragment;
import cn.xiaomi.todo.task.TaskEditFragment;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Task task = getIntent().getParcelableExtra(Constants.Intent.EXTRA_TASK);
        int taskType = getIntent().getIntExtra(Constants.Intent.EXTRA_TYPE, Constants.Intent.EXTRA_TYPE_NULL);

        Fragment fragment = null;
        if (taskType == Constants.Intent.EXTRA_TYPE_TASK_ADD) {
            fragment = TaskEditFragment.newInstance(true);
        }

        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(android.R.id.content, fragment).commit();
        }
    }
}
