package com.shellever.contacts;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

/**
 * Author: Shellever
 * Date:   11/13/2016
 * Email:  shellever@163.com
 */

public class SearchEditText extends EditText {

    private static boolean DEBUG = false;

    private Drawable mSearchDrawable;
    private Drawable mDeleteDrawable;

    public SearchEditText(Context context) {
        this(context, null);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mSearchDrawable = getCompoundDrawables()[0];    // left top right bottom
        if (mSearchDrawable == null) {
            mSearchDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_action_search);
        }
        int mIntrinsicWidth = mSearchDrawable.getIntrinsicWidth();
        int mIntrinsicHeight = mSearchDrawable.getIntrinsicHeight();
        int width = (int) (mIntrinsicWidth * 0.8f);     // scale
        int height = (int) (mIntrinsicHeight * 0.8f);   // scale
        mSearchDrawable.setBounds(0, 0, width, height);

        if(DEBUG) {
            Locale locale = Locale.getDefault();
            String info = String.format(locale, "[(%d, %d), (%d, %d)]", mIntrinsicWidth, mIntrinsicHeight, width, height);
            Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();  // (96, 96), (76, 76)
        }

        mDeleteDrawable = getCompoundDrawables()[2];    // left top right bottom
        if (mDeleteDrawable == null) {
            mDeleteDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_action_delete);
        }
        mIntrinsicWidth = mDeleteDrawable.getIntrinsicWidth();
        mIntrinsicHeight = mDeleteDrawable.getIntrinsicHeight();
        width = (int) (mIntrinsicWidth * 0.8f);
        height = (int) (mIntrinsicHeight * 0.8f);
        mDeleteDrawable.setBounds(0, 0, width, height);

        setDeleteDrawable(false);
        addTextChangedListener(new MiddleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setDeleteDrawable(s.length() > 0);
            }
        });
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                boolean visible = hasFocus && (getText().length() > 0);
                setDeleteDrawable(visible);
            }
        });
    }

    private void setDeleteDrawable(boolean visible) {
        Drawable right = visible ? mDeleteDrawable : null;
        setCompoundDrawables(mSearchDrawable, null, right, null);   // firstly, setBounds()
//        setCompoundDrawablesWithIntrinsicBounds(mSearchDrawable, null, right, null); // IntrinsicBounds
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDeleteDrawable != null && event.getAction() == MotionEvent.ACTION_UP) {
            int left = getWidth() - getPaddingRight() - mDeleteDrawable.getIntrinsicWidth();
            int right = getWidth() - getPaddingRight();
            if(DEBUG) {
                Toast.makeText(getContext(), "left, right = " + left + ", " + right, Toast.LENGTH_SHORT).show();
            }
            if (event.getX() >= left && event.getX() <= right) {
                this.setText("");
            }
//            int eventX = (int) event.getRawX();
//            int eventY = (int) event.getRawY();
//            Toast.makeText(getContext(), "eventX, eventY = " + eventX + ", " + eventY, Toast.LENGTH_SHORT).show();
//            Rect rect = new Rect();
//            getGlobalVisibleRect(rect);         //
//            rect.left = rect.right - 50;
//            if(rect.contains(eventX, eventY)){
//                setText("");
//            }
        }

        return super.onTouchEvent(event);
    }

    public static class MiddleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
