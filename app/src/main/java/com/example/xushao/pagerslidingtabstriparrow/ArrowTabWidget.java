package com.example.xushao.pagerslidingtabstriparrow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TabWidget;

import com.example.xushao.pagerslidingtabstriparrow.utils.Utils;

/**
 * 带箭头指示选中位置，底部箭头跟随滑动
 * <p>
 * 说明：
 * 支持任意个数，但由于无法左右滑动，太多了会导致宽度不够.
 *
 * @author xushao
 */
public class ArrowTabWidget extends TabWidget {

    private final int SCREEN;
    private final int BG_WIDTH;

    private int mCount;
    private float mItemWidth;
    private float mOffset;
    private float mInitPos;

    private Bitmap mBitmap;

    public ArrowTabWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        SCREEN = Utils.getWidth(context);
        BG_WIDTH = SCREEN * 2;
        init(context);
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mCount > 0) {
            float leftPos = mInitPos;
            leftPos += mOffset;
            canvas.drawBitmap(mBitmap, leftPos, 0, null);
        }
    }

    /**
     * 9patch图在nexus 5x(Android 6.0)上没有对齐，解决办法如下.
     * <p>
     * 原先放在在drawable-xxhdpi目录，对比发现
     * 1、放在drawable目录，对齐正常，但图片模糊；
     * 2、放在drawable-nodpi目录，对齐正常，显示正常.
     */
    private void init(Context context) {
        int itemHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
        mBitmap = Utils.getNinePatchBitmap(R.drawable.ic_tab_widget_bg, BG_WIDTH, itemHeight, context);
    }

    /**
     * pager数目
     *
     * @param count tab count
     */
    public void setTabCount(int count) {
        if (count > 0) {
            mCount = count;
            mItemWidth = (float) SCREEN / mCount;
            mInitPos = -SCREEN + mItemWidth / 2;
        } else {
            System.out.println("ArrowTabWidget: Tab count can't be 0");
        }
    }

    public void updateArrow(int position, int offsetPx) {
        if (mCount > 0) {
            float o = offsetPx / (float) mCount;
            mOffset = position * mItemWidth + o;
            invalidate();
        }
    }

}
