package app.com.customlocationservice;

import android.app.Activity;

/**
 * Created by mind on 3/1/17.
 */

public class ActivityTracker {

    static Activity context;

    public static Activity getContext() {
        return context;
    }

    public static void setContext(Activity context) {
        ActivityTracker.context = context;
    }

}
