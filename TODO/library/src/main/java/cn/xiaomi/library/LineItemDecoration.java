package cn.xiaomi.library;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by qiaohaibin on 21/09/2017.
 */

public class LineItemDecoration extends SpacingItemDecoration {

    private Paint mPaint;
    private final Rect mBounds = new Rect();
    private final Rect mOffsets = new Rect();

    public LineItemDecoration(int spacing, int spanCount) {
        this(spacing, spanCount, Color.LTGRAY);
    }

    public LineItemDecoration(int spacing, int spanCount, int color) {
        super(spacing, spanCount);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public LineItemDecoration(int spacing, int spanCount, boolean includeEdge) {
        super(spacing, spanCount, includeEdge);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public LineItemDecoration(int spacing, int spanCount, int color, int orientation, boolean includeEdge) {
        super(spacing, spanCount, orientation, includeEdge);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);

        if (mOrientation == HORIZONTAL) {
            drawHorizontal(canvas, parent, state);
        } else {
            drawVertical(canvas, parent, state);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        canvas.save();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && parent.getClipToPadding()) {
            canvas.clipRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getWidth() - parent.getPaddingRight(),
                    parent.getHeight() - parent.getPaddingBottom());
        }


        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {

            final View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);

            final float right = mBounds.right + Math.round(ViewCompat.getTranslationX(child));
            final float left = mBounds.left + Math.round(ViewCompat.getTranslationX(child));

            final float bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child));
            final float top = mBounds.top + Math.round(ViewCompat.getTranslationY(child));

            int column = i % mSpanCount;
            super.getItemOffsets(mOffsets, child, parent, state);

            if (mIncludeEdge && column == 0) {
                //left
                canvas.drawRect(left + mOffsets.left - mSpacing, top + mOffsets.top - mSpacing, left + mOffsets.left, bottom - mOffsets.bottom + mSpacing, mPaint);
            }

            if (mIncludeEdge && i < mSpanCount) {
                //top
                canvas.drawRect(left + mOffsets.left - mSpacing, top + mOffsets.top - mSpacing, right - mOffsets.right + mSpacing, top + mOffsets.top, mPaint);
            }

            if (column != mSpanCount - 1 || mIncludeEdge) {
                //right
                canvas.drawRect(right - mOffsets.right, top + mOffsets.top - mSpacing, right - mOffsets.right + mSpacing, bottom - mOffsets.bottom + mSpacing, mPaint);
            }

            if (i / mSpanCount != (childCount - 1) / mSpanCount || mIncludeEdge) {
                //bottom
                canvas.drawRect(left + mOffsets.left - mSpacing, bottom - mOffsets.bottom, right - mOffsets.right + mSpacing, bottom - mOffsets.bottom + mSpacing, mPaint);
            }

        }
        canvas.restore();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        canvas.save();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && parent.getClipToPadding()) {
            canvas.clipRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getWidth() - parent.getPaddingRight(),
                    parent.getHeight() - parent.getPaddingBottom());
        }


        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);

            final float left = mBounds.left + Math.round(ViewCompat.getTranslationX(child));
            final float top = mBounds.top + Math.round(ViewCompat.getTranslationY(child));
            final float right = mBounds.right + Math.round(ViewCompat.getTranslationX(child));
            final float bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child));

            int row = i % mSpanCount;
            super.getItemOffsets(mOffsets, child, parent, state);

            if (mIncludeEdge && i < mSpanCount) {
                //left
                canvas.drawRect(left + mOffsets.left - mSpacing, top + mOffsets.top - mSpacing, left + mOffsets.left, bottom - mOffsets.bottom + mSpacing, mPaint);
            }

            if (mIncludeEdge && row == 0) {
                //top
                canvas.drawRect(left + mOffsets.left - mSpacing, top + mOffsets.top - mSpacing, right - mOffsets.right + mSpacing, top + mOffsets.top, mPaint);
            }

            if (i / mSpanCount != (childCount - 1) / mSpanCount || mIncludeEdge) {
                //right
                canvas.drawRect(right - mOffsets.right, top + mOffsets.top - mSpacing, right - mOffsets.right + mSpacing, bottom - mOffsets.bottom + mSpacing, mPaint);
            }

            if (row != mSpanCount - 1 || mIncludeEdge) {
                //bottom
                canvas.drawRect(left + mOffsets.left - mSpacing, bottom - mOffsets.bottom, right - mOffsets.right + mSpacing, bottom - mOffsets.bottom + mSpacing, mPaint);
            }

        }
        canvas.restore();
    }

}
