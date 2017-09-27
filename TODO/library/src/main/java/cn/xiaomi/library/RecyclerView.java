package cn.xiaomi.library;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by qiaohaibin on 27/09/2017.
 */

public class RecyclerView extends android.support.v7.widget.RecyclerView {

    private ContextMenu.ContextMenuInfo mContextMenuInfo;

    public RecyclerView(Context context) {
        super(context);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return mContextMenuInfo;
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        mContextMenuInfo = new AdapterView.AdapterContextMenuInfo(originalView, getChildAdapterPosition(originalView), getChildItemId(originalView));
        return super.showContextMenuForChild(originalView);
    }
}
