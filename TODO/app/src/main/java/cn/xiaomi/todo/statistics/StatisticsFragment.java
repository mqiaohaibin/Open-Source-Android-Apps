package cn.xiaomi.todo.statistics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.xiaomi.todo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment implements StatisticsPresenter.View{

    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
        return fragment;
    }

    private StatisticsPresenter mPresenter;

    private TextView tvStatistics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        tvStatistics = (TextView) view.findViewById(R.id.tvStatistics);

        mPresenter = new StatisticsPresenter(this);
        mPresenter.start();

        return view;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onShowStatistics(int activeTaskCount, int completedTaskCount) {
        if (activeTaskCount + completedTaskCount > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(getString(R.string.statistics_activeTaskLabel));
            builder.append(activeTaskCount);
            builder.append("\r\n");
            builder.append(getString(R.string.statistics_completedTaskLabel));
            builder.append(completedTaskCount);
            tvStatistics.setText(builder.toString());
        } else {
            tvStatistics.setText(R.string.statistics_title);
        }
    }
}
