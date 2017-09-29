package cn.xiaomi.todo.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;

import cn.xiaomi.todo.Constants;
import cn.xiaomi.todo.R;
import cn.xiaomi.todo.model.task.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskEditFragment extends Fragment implements TaskEditPresenter.View{

    private static final String TAG = "TaskEditFragment";

    public static TaskEditFragment newInstance(boolean returnResult) {
        TaskEditFragment fragment = new TaskEditFragment();
        return fragment;
    }

    private TaskEditPresenter mPresenter;

    private EditText etTitle;
    private EditText etDescription;
    private CheckBox cbCompleted;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_edit, container, false);

        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etDescription = (EditText) view.findViewById(R.id.etDescription);
        cbCompleted = (CheckBox) view.findViewById(R.id.cbCompleted);

        //force show soft input
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mPresenter = new TaskEditPresenter(this, true);
        mPresenter.start();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.add(Menu.NONE, R.id.action_done, Menu.NONE, R.string.action_done);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            String title = etTitle.getText().toString();
            String description = etDescription.getText().toString();
            boolean completed = cbCompleted.isChecked();
            mPresenter.addTask(title, description, completed);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onShow(String toast) {
        Snackbar.make(getView(), toast, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBack(Task task) {
        Intent data = new Intent();
        data.putExtra(Constants.Intent.EXTRA_TASK, task);
        getActivity().setResult(Activity.RESULT_OK, data);
        getActivity().finish();
    }
}
