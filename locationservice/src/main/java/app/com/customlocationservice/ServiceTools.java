package app.com.customlocationservice;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by mind on 4/1/17.
 */

public class ServiceTools {

    private static String LOG_TAG = ServiceTools.class.getName();

    public static boolean isServiceRunning(String serviceClassName){
        final ActivityManager activityManager = (ActivityManager) ActivityTracker.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(serviceClassName)){
                return true;
            }
        }
        return false;
    }
}
