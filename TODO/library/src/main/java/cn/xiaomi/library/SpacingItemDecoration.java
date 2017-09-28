package cn.xiaomi.library;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by qiaohaibin on 22/09/2017.
 */

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    protected int mSpacing;
    protected int mSpanCount;
    protected int mOrientation;
    protected boolean mIncludeEdge;

    public SpacingItemDecoration(int spacing, int spanCount) {
        this(spacing, spanCount, false);
    }

    public SpacingItemDecoration(int spacing, int spanCount, boolean includeEdge) {
        this(spacing, spanCount, VERTICAL, includeEdge);
    }

    public SpacingItemDecoration(int spacing, int spanCount, int orientation, boolean includeEdge) {
        mSpacing = spacing;
        mSpanCount = spanCount;
        mOrientation = orientation;
        mIncludeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == HORIZONTAL) {
            offsetHorizontal(outRect, view, parent, state);
        } else {
            offsetVertical(outRect, view, parent, state);
        }
    }

    private void offsetVertical(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % mSpanCount;

        if (mIncludeEdge) {
            outRect.left = mSpacing - column * mSpacing / mSpanCount;
            outRect.right = (column + 1) * mSpacing / mSpanCount;
            if (position < mSpanCount) {
                outRect.top = mSpacing;
            }
            outRect.bottom = mSpacing;
        } else {
            outRect.left = column * mSpacing / mSpanCount;
            outRect.right = mSpacing - (column + 1) * mSpacing / mSpanCount;
            if (position >= mSpanCount) {
                outRect.top = mSpacing;
            }
        }
    }

    private void offsetHorizontal(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int row = position % mSpanCount;

        if (mIncludeEdge) {
            outRect.top = mSpacing - row * mSpacing / mSpanCount;
            outRect.bottom = (row + 1) * mSpacing / mSpanCount;
            if (position < mSpanCount) {
                outRect.left = mSpacing;
            }
            outRect.right = mSpacing;
        } else {
            outRect.top = row * mSpacing / mSpanCount;
            outRect.bottom = mSpacing - (row + 1) * mSpacing / mSpanCount;
            if (position >= mSpanCount) {
                outRect.right = mSpacing;
            }
        }
    }

}
