package com.meitu.netlib.constraintdemo;

import android.content.Context;

/**
 * Basic api common config
 */
public class BasicConfig {

    private static Context mContext;

    /**
     * save application context for use
     *
     * @param context application context
     */
    public static void init(Context context) {
        if (context == null) {
            throw new NullPointerException("context can't be null");
        }
        mContext = context.getApplicationContext();
    }

    /**
     * @return application context
     */
    public static Context getContext() {
        return mContext;
    }

}
