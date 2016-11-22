package com.shellever.contacts;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Author: Shellever
 * Date:   11/14/2016
 * Email:  shellever@163.com
 */

public class DisplayUtils {
    public static float dp2px(Context context, int dip) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm);
    }
}
