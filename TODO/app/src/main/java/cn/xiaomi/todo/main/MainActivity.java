package cn.xiaomi.todo.main;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import cn.xiaomi.todo.R;
import cn.xiaomi.todo.statistics.StatisticsFragment;
import cn.xiaomi.todo.task.TaskFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {

    private MainPresenter mMainPresenter;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        mMainPresenter = new MainPresenter(this);
        mMainPresenter.start();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        MainActionType actionType;

        switch (item.getItemId()) {
            case R.id.action_statistics:
                actionType = MainActionType.MAIN_ACTION_TYPE_STATISTICS;
                break;

            default:
            case R.id.action_list:
                actionType = MainActionType.MAIN_ACTION_TYPE_LIST;
                break;
        }

        mMainPresenter.changeMainAction(actionType);

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean isActive() {
        return isFinishing();
    }

    @Override
    public void onShow(MainActionType actionType) {
        FloatingActionButton actionButton = (FloatingActionButton) findViewById(R.id.actionButton);
        actionButton.setVisibility(View.GONE);

        Fragment fragment;
        switch (actionType) {
            case MAIN_ACTION_TYPE_STATISTICS:
                fragment = StatisticsFragment.newInstance();
                break;
            case MAIN_ACTION_TYPE_LIST:
            default:
                fragment = new TaskFragment();
                break;
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.mContent, fragment).commit();
    }
}
