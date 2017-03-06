package com.meitu.netlib.activityswitchanimation;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by sunyuxin on 16/9/12.
 */
public class MTApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initLifeRecycle();
    }

    private void initLifeRecycle() {
       this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
           @Override
           public void onActivityCreated(Activity activity, Bundle bundle) {
           }

           @Override
           public void onActivityStarted(Activity activity) {

           }

           @Override
           public void onActivityResumed(Activity activity) {
//                if (activity instanceof Main2Activity) {
//                    final View v = ((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0);
//                    activity.findViewById(android.R.id.content).setVisibility(View.GONE);
//                    v.setVisibility(View.GONE);
//                    v.animate().scaleX(0).scaleY(0).translationX(200).translationY(200).
//                            withEndAction(new Runnable() {
//                                @Override
//                                public void run() {
//                                    v.setVisibility(View.VISIBLE);
//                                }}).setDuration(9000).setInterpolator(new DecelerateInterpolator()).start();
//                }
           }

           @Override
           public void onActivityPaused(Activity activity) {

           }

           @Override
           public void onActivityStopped(Activity activity) {

           }

           @Override
           public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

           }

           @Override
           public void onActivityDestroyed(Activity activity) {

           }
       });
    }


}
