package cn.xiaomi.todo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import cn.xiaomi.todo.task.TaskEditFragment;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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
