package com.meitu.netlib.constraintdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meitu.netlib.constraintdemo.BasicConfig;


public class DimenHelper {

    public static final int DENSITY_LOW = 120;
    public static final int DENSITY_MEDIUM = 160;
    public static final int DENSITY_HIGH = 240;
    public static final int DENSITY_XHIGH = 320;
    public static final int DENSITY_XXHIGH = 480;
    public static final int NOT_CHANGE = -100;

    private static final int DP_TO_PX = TypedValue.COMPLEX_UNIT_DIP;
    private static final int SP_TO_PX = TypedValue.COMPLEX_UNIT_SP;
    private static final int PX_TO_DP = TypedValue.COMPLEX_UNIT_MM + 1;
    private static final int PX_TO_SP = TypedValue.COMPLEX_UNIT_MM + 2;

    private static Context mContext;
    private static DisplayMetrics mMetrics;

    static {
        mContext = BasicConfig.getContext().getApplicationContext();
        mMetrics = mContext.getResources().getDisplayMetrics();
    }

    // -- dimens convert

    private static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case DP_TO_PX:
            case SP_TO_PX:
                return TypedValue.applyDimension(unit, value, metrics);
            case PX_TO_DP:
                return value / metrics.density;
            case PX_TO_SP:
                return value / metrics.scaledDensity;
        }
        return 0;
    }

    public static int dp2px(float value) {
        return (int) applyDimension(DP_TO_PX, value, mMetrics);
    }

    public static int sp2px(float value) {
        return (int) applyDimension(SP_TO_PX, value, mMetrics);
    }

    public static int px2dp(float value) {
        return (int) applyDimension(PX_TO_DP, value, mMetrics);
    }

    public static int px2sp(float value) {
        return (int) applyDimension(PX_TO_SP, value, mMetrics);
    }

    public static int dp2bitmapSize(float value) {
        if (mMetrics.density >= 3) {
            return (int) (value * 2.75f);
        }
        return (int) applyDimension(DP_TO_PX, value, mMetrics);
    }

    public static int screenWidth() {
        return mMetrics.widthPixels;
    }

    public static int screenHeight() {
        return mMetrics.heightPixels;
    }

    public static float density() {
        return mMetrics.density;
    }

    // -- update layout

    public static void updateLayout(View view, int w, int h) {
        if (view == null) {
            return;
        }

        LayoutParams params = view.getLayoutParams();
        if (params == null) {
            return;
        }

        boolean changed = false;
        if (w != NOT_CHANGE && params.width != w) {
            changed = true;
            params.width = w;
        }
        if (h != NOT_CHANGE && params.height != h) {
            changed = true;
            params.height = h;
        }

        if (changed) {
            view.setLayoutParams(params);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                view.setMinimumWidth(params.width);
                view.setMinimumHeight(params.height);
            }
        }
    }

    public static void updateLayoutMargin(View view, int left, int top, int right, int bottom) {
        if (view == null) {
            return;
        }
        LayoutParams params = view.getLayoutParams();
        if (params == null) {
            return;
        }
        if (params instanceof RelativeLayout.LayoutParams) {
            updateMargin(view, (RelativeLayout.LayoutParams) params, left, top, right, bottom);
        } else if (params instanceof LinearLayout.LayoutParams) {
            updateMargin(view, (LinearLayout.LayoutParams) params, left, top, right, bottom);
        } else if (params instanceof FrameLayout.LayoutParams) {
            updateMargin(view, (FrameLayout.LayoutParams) params, left, top, right, bottom);
        } else if (params instanceof ViewGroup.MarginLayoutParams) {
            updateMargin(view, (ViewGroup.MarginLayoutParams) params, left, top, right, bottom);
        }
    }

    private static void updateMargin(View view, ViewGroup.MarginLayoutParams params, int l, int t, int r, int b) {
        boolean changed = false;
        if (l != NOT_CHANGE && params.leftMargin != l) {
            changed = true;
            params.leftMargin = l;
        }
        if (t != NOT_CHANGE && params.topMargin != t) {
            changed = true;
            params.topMargin = t;
        }
        if (r != NOT_CHANGE && params.rightMargin != r) {
            changed = true;
            params.rightMargin = r;
        }
        if (b != NOT_CHANGE && params.bottomMargin != b) {
            changed = true;
            params.bottomMargin = b;
        }
        if (changed) {
            view.setLayoutParams(params);
        }
    }

    public static void updatePadding(View view, int l, int t, int r, int b) {
        if (view == null) {
            return;
        }
        int pl = view.getPaddingLeft();
        int pt = view.getPaddingTop();
        int pr = view.getPaddingRight();
        int pb = view.getPaddingBottom();

        boolean changed = false;
        if (l != NOT_CHANGE && pl != l) {
            changed = true;
            pl = l;
        }
        if (t != NOT_CHANGE && pt != t) {
            changed = true;
            pt = t;
        }
        if (r != NOT_CHANGE && pr != r) {
            changed = true;
            pr = r;
        }
        if (b != NOT_CHANGE && pb != b) {
            changed = true;
            pb = b;
        }
        if (changed) {
            view.setPadding(pl, pt, pr, pb);
        }
    }

    // -- window dimens

    public static int statusBarHeight(Activity activity) {
        Rect rect = new Rect();
        if (activity != null && activity.getWindow() != null) {
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        }
        return rect.top;
    }

    // -- text dimens

    /**
     * @param textView make sure your textView has invoke {@link TextView#setTextSize(float)}.
     * @return text's width.
     */
    public static float textWidth(TextView textView, String text) {
        return textView.getPaint().measureText(text);
    }

    /**
     * @param size in pixel
     * @return text's width.
     */
    public static float textWidth(float size, String text) {
        Paint paint = new Paint();
        paint.setTextSize(size);
        return paint.measureText(text);
    }

    public static boolean isTextWidthOver(float limitWidth, float textSize, String text) {
        float textWidth = textWidth(textSize, text);
        return limitWidth < textWidth;
    }

    /**
     * @param textView make sure your textView has invoke {@link TextView#setTextSize(float)}.
     * @return text's height
     */
    public static float textHeight(TextView textView) {
        Paint.FontMetrics fontMetrics = textView.getPaint().getFontMetrics();
        return fontMetrics.bottom - fontMetrics.top;
    }

    /**
     * @param blankLength blank length.
     * @param textView    make sure your textView has invoke {@link TextView#setTextSize(float)}.
     * @return blank string with measured length
     */
    public static String blankString(float blankLength, TextView textView) {
        int count = (int) Math.ceil(blankLength / textWidth(textView, " "));
        StringBuilder sb = new StringBuilder();
        while (count-- > 0) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public static String getOmittedString(float limitWidth, float textSize, String str) {
        if (limitWidth == 0) {
            return "";
        }

        if (TextUtils.isEmpty(str) || !isTextWidthOver(limitWidth, textSize, str)) {
            return str;
        }

        String lastString = "...";
        if (isTextWidthOver(limitWidth, textSize, lastString)) {
            return "";
        }

        if (textWidth(textSize, lastString) >= textWidth(textSize, str)) {
            return "";
        }

        int index = 0;
        for (int i = 0; i <= str.length(); ++i) {
            String targetStr = str.substring(0, i);
            if (isTextWidthOver(limitWidth, textSize, targetStr)) {
                index = i;
                break;
            }
        }

        if (index < 2) {
            return "";
        } else if (index == 2) {
            String targetStr = str.substring(0, 1) + lastString;
            if (isTextWidthOver(limitWidth, textSize, targetStr)) {
                return lastString;
            } else {
                return targetStr;
            }
        } else if (index > 2) {
            String targetStr = str.substring(0, index) + lastString;
            if (isTextWidthOver(limitWidth, textSize, targetStr)) {
                targetStr = str.substring(0, index - 1) + lastString;
                if (isTextWidthOver(limitWidth, textSize, targetStr)) {
                    return str.substring(0, index - 2) + lastString;
                }
                return targetStr;
            } else {
                return targetStr;
            }
        } else {
            return lastString;
        }
    }

    /**
     * 适配带虚拟导航栏机型
     * @param activity
     * @param listener
     */
    public static void getRealScreenHeight(Activity activity, final OnRealHeightListener listener) {
        if (activity == null || activity.isFinishing()) {
            if (listener != null) {
                listener.onRealHeightGet(DimenHelper.screenHeight());
            }
            return;
        }

        final View decorView = activity.getWindow().getDecorView();
        if (decorView instanceof ViewGroup && ((ViewGroup)decorView).getChildCount() > 0) {
            ((ViewGroup)decorView).getChildAt(0).getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            int realHeight = ((ViewGroup) decorView).getChildAt(0).getHeight();
                            if (listener != null) {
                                listener.onRealHeightGet(realHeight);
                            }
                        }
                    }
            );
        }
    }

    public interface OnRealHeightListener {
        void onRealHeightGet(int realHeight);
    }
}