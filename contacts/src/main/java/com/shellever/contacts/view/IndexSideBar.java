package com.shellever.contacts.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.shellever.contacts.R;
import com.shellever.contacts.utils.DisplayUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Author: Shellever
 * Date:   11/13/2016
 * Email:  shellever@163.com
 */

public class IndexSideBar extends View {

    private static String[] mLetterIndexArray = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z", "#"
    };

    private List<String> mLetterIndexList;
    private int curLetterIndex = -1;    // current letter index
    private Paint mPaint;
    private Rect mTextBounds;

//    private int width;
//    private int height;


    public IndexSideBar(Context context) {
        this(context, null);
    }

    public IndexSideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mLetterIndexList = Arrays.asList(mLetterIndexArray);    // String[] -> List
        mTextBounds = new Rect();
        mPaint = new Paint();       // Paint.ANTI_ALIAS_FLAG
        mPaint.setAntiAlias(true);
        mPaint.setTypeface(Typeface.SANS_SERIF);
        mPaint.setTextSize(DisplayUtils.sp2px(getContext(), 12));  // 12sp // 12dp

        setBackgroundColor(Color.alpha(0));
    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        width = w;
//        height = h;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        int cellWidth = getWidth();
        int size = mLetterIndexList.size();
        float cellHeight = getHeight() * 1.0f / size;

        for (int index = 0; index < size; index++) {
            mPaint.setColor(Color.BLACK);       // black
            if (index == curLetterIndex) {
                mPaint.setColor(Color.WHITE);   // white
            }
            String letter = mLetterIndexList.get(index);

            float xPos = (cellWidth - mPaint.measureText(letter)) / 2;

            mPaint.getTextBounds(letter, 0, letter.length(), mTextBounds);
            int textHeight = mTextBounds.height();
            // baseline - left bottom, no left top
            float yPos = cellHeight / 2 + textHeight / 2 + cellHeight * index;

            // xPos - The x-coordinate of the origin of the text being drawn
            // yPos - The y-coordinate of the baseline of the text being drawn
            canvas.drawText(letter, xPos, yPos, mPaint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float y = event.getY();
        int height = getHeight();
        int size = mLetterIndexList.size();
        int oldLetterIndex = curLetterIndex;
        int tmpLetterIndex = (int) (y / height * size);

        if (event.getAction() == MotionEvent.ACTION_UP) {
            setBackgroundColor(Color.alpha(0));     // reset
            curLetterIndex = -1;
            invalidate();
            if (listener != null) {
                listener.onTouchedLetterListener();
            }
        } else {
            setBackgroundResource(R.drawable.bg_index_side_bar);    // set
            if (tmpLetterIndex != oldLetterIndex) {
                if (tmpLetterIndex >= 0 && tmpLetterIndex < size) {
                    if (listener != null) {
                        listener.onTouchingLetterListener(mLetterIndexList.get(tmpLetterIndex));
                    }
                    curLetterIndex = tmpLetterIndex;
                    invalidate();
                }
            }
        }

        return true;
    }

    private OnTouchLetterListener listener;

    public void setOnTouchLetterListener(OnTouchLetterListener listener) {
        this.listener = listener;
    }

    public interface OnTouchLetterListener {
        void onTouchingLetterListener(String letter);

        void onTouchedLetterListener();
    }

    public void setLetterIndexList(List<String> list) {
        setLetterIndexList(list, true);
    }

    public void setLetterIndexList(List<String> list, boolean perform) {
        mLetterIndexList = perform ? list : Arrays.asList(mLetterIndexArray);
        invalidate();
    }
}
