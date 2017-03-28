package app.com.customlocationservice;

import android.content.Context;
import android.location.LocationListener;

import com.google.android.gms.location.LocationRequest;

/**
 * Created by mind on 1/2/17.
 */

class MILocationController {

    /**
     * Created by mind on 1/2/17.
     */

    public static class LocationParams {

        public LocationParams(Context mContext) {
            context = mContext;
        }

//        MILocationService locationManager;
        CustomLocationService locationManager;
        LocationCallback locationCallback;
        LocationListener locationListener;
        int priority;
        float minDistance;
        long interval;
        long fastestInterval;
        int numberOfUpdates;
        boolean locationUpdate;
        Context context;


        public void apply(LocationParams locationParams) {
            if (context != null) {
                locationParams.setContext(context);
            } else {
                throw new IllegalArgumentException("Context Cannot be Null.");
            }

//            if (minDistance1.isNaN()) {
                locationParams.setMinDistance(minDistance);
//            } else {
//                throw new IllegalArgumentException("Minimum Distance cannot be 0, Please set higher than zero Or Default Params.");
//            }

//            if (interval != 0) {
                locationParams.setInterval(interval);
//            } else {
//                throw new IllegalArgumentException("Time Interval cannot be 0, Please set higher than zero Or Default Params.");
//            }

//            if (fastestInterval != 0) {
                locationParams.setFastestInterval(fastestInterval);
//            } else {
//                throw new IllegalArgumentException("Fastest Time Interval cannot be 0, Please set higher than zero Or Default Params.");
//            }

//            if (priority != 0) {
                locationParams.setPriority(priority);
//            } else {
//                throw new IllegalArgumentException("Priority can only be either " +
//                        "PRIORITY_HIGH_ACCURACY, PRIORITY_BALANCED_POWER_ACCURACY, " +
//                        " or  PRIORITY_LOW_POWER");
//            }

//            if (locationCallback != null) {
//                locationParams.setLocationCallback(locationCallback);
//            } else {
//                throw new IllegalArgumentException("Please check GPSInterface might be not attach.");
//            }

            if (locationListener != null) {
                locationParams.setLocationListener(locationListener);
            }

//            if (numberOfUpdates != 0) {
                locationParams.setNumberOfUpdates(numberOfUpdates);
//            } else {
//                throw new IllegalArgumentException("Number Of Updates cannot be 0, Please set higher than zero Or Default Params.");
//            }

        }


        public LocationCallback getLocationCallback() {
            return locationCallback;
        }

        public void setLocationCallback(LocationCallback locationCallback) {
            this.locationCallback = locationCallback;
        }


        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
            getLocationPriority(priority);
        }


        public float getMinDistance() {
            return minDistance;
        }

        public void setMinDistance(float minDistance) {
            this.minDistance = minDistance;
        }


        public boolean isLocationUpdate() {
            return locationUpdate;
        }


        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public long getInterval() {
            return interval;
        }

        public void setInterval(long interval) {
            this.interval = interval;
        }

        public int getNumberOfUpdates() {
            return numberOfUpdates;
        }

        public void setNumberOfUpdates(int numberOfUpdates) {
            this.numberOfUpdates = numberOfUpdates;
        }


        public long getFastestInterval() {
            return fastestInterval;
        }

        public void setFastestInterval(long fastestInterval) {
            this.fastestInterval = fastestInterval;
        }


        public LocationListener getLocationListener() {
            return locationListener;
        }

        public void setLocationListener(LocationListener locationListener) {
            this.locationListener = locationListener;
        }


        public CustomLocationService getLocationManager() {
            return locationManager;
        }

        public void setLocationManager(CustomLocationService locationManager) {
            this.locationManager = locationManager;
        }


        public int getLocationPriority(int priority) {

            switch (priority) {
                case 1:
                    return LocationRequest.PRIORITY_HIGH_ACCURACY;
                case 2:
                    return LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
                case 3:
                    return LocationRequest.PRIORITY_LOW_POWER;
                default:
                    return LocationRequest.PRIORITY_HIGH_ACCURACY;
//                    throw new IllegalArgumentException("Priority can only be either " +
//                            "PRIORITY_HIGH_ACCURACY, PRIORITY_BALANCED_POWER_ACCURACY, " +
//                            " or  PRIORITY_LOW_POWER");
            }
        }

    }
}
