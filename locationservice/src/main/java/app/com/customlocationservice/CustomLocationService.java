package app.com.customlocationservice;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * Created by mind on 3/1/17.
 */

public class CustomLocationService extends Service implements com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    Context mContext = ActivityTracker.getContext();
//        LocationCallback locationCallback;

    public CustomLocationService(Context mContext) {
        this.mContext = mContext;
    }

    public CustomLocationService() {
    }

    public CustomLocationService Builder() {
        return this;
    }

    public CustomLocationService build() {
        return this;
    }

    boolean locationUpdate;
    long interVal;
    boolean singleTime;
    float displacement;
    int priority;
    LocationRequest mLocationRequest;
    LocationCallback locationCallback;

    public boolean isSingleTime() {
        return singleTime;
    }

    public void setSingleTime(boolean singleTime) {
        this.singleTime = singleTime;
        if (singleTime) {
            interVal = 0;
            onConnected(null);
        }
    }

    public long getInterVal() {
        return interVal;
    }

    public void setInterVal(long interValTime, String timeFormate) {
        this.interVal = getTimeInMinute(interValTime, timeFormate);
        setLocationUpdate(isLocationUpdate());
    }

    public boolean isLocationUpdate() {
        return locationUpdate;
    }

    public void setLocationUpdate(boolean locationUpdate) {
        this.locationUpdate = locationUpdate;
//        if (locationUpdate) {
        onConnected(null);
//        }

//        if (locationUpdate) {
//            onConnected(null);
//        } else {
//            stopLocationUpdates();
//        }

    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
        getLocationPriority(priority);
    }

/*    public CustomLocationService(Context mContext, LocationCallback locationCallback) {
        this.mContext = mContext;
        showSettingsAlert();
    }*/

    /**
     * set time in minutes.
     * @param time: time
     * @param timeFormate: time format.
     * @return: return time in minutes.
     */
    public long getTimeInMinute(long time, String timeFormate) {
        switch (timeFormate) {
            case TimeUnit.HOURS:
                return time * 60;
            case TimeUnit.MINUTES:
                return time;
            case TimeUnit.SECONDS:
                return time/60;
            default:
                return time;
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onDestroy() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onDestroy();
    }

    /**
     * Stop location update.
     */
    public void stopLocationUpdates() {

        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
        System.out.println(" GoogleApiClient connection status for Stop After Connect >>> " + mGoogleApiClient.isConnected());

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Toast.makeText(mContext, "Stop", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * It will show google location service dialog when location of device is off.
     */
    public void showSettingsAlert() {

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            mGoogleApiClient.connect();
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(getLocationPriority(1));
        locationRequest.setInterval(1 * 30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);

        // it will request for the permission dialog.
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
//                        try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        try {
                            status.startResolutionForResult(
                                    (Activity) mContext, 1001);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
//                        } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
//                        }   break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });

//        startLocationUpdates();

    }

    /**
     * Set priority for the location request.
     * @param priority: priority.
     * @return: priority which need to use for getting location.
     */
    private int getLocationPriority(int priority) {
        switch (priority) {
            case 1:
                return Priority.PRIORITY_HIGH_ACCURACY;
            case 2:
                return Priority.PRIORITY_BALANCED_POWER_ACCURACY;
            case 3:
                return Priority.PRIORITY_NO_POWER;
            default:
                return Priority.PRIORITY_HIGH_ACCURACY;
        }
    }

    /**
     * It will update our location using FusedLocationApi.
     */
    public void startLocationUpdates() {

        mLocationRequest = LocationRequest.create();
        if (CustomLocationService.Builder.P.getPriority() != 0) {
            mLocationRequest.setPriority(CustomLocationService.Builder.P.getLocationPriority(CustomLocationService.Builder.P.getPriority()));
        }

        if (CustomLocationService.Builder.P.getMinDistance() != 0) {
            mLocationRequest.setSmallestDisplacement(CustomLocationService.Builder.P.getMinDistance());
        }
        if (CustomLocationService.Builder.P.getInterval() != 0) {
            mLocationRequest.setInterval(CustomLocationService.Builder.P.getInterval()); // Update location every second
        }

        if (CustomLocationService.Builder.P.getNumberOfUpdates() != 0) {
            mLocationRequest.setNumUpdates(CustomLocationService.Builder.P.getNumberOfUpdates());
        }

        if (CustomLocationService.Builder.P.getFastestInterval() != 0) {
            mLocationRequest.setFastestInterval(CustomLocationService.Builder.P.getFastestInterval());
        }

        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(getLocationPriority(priority));
        mLocationRequest.setInterval(100); // Update location every second
        mLocationRequest.setFastestInterval(100); // Update location every second
//        mLocationRequest.setSmallestDisplacement(displacement); // Update location every second
//        mLocationRequest.setNumUpdates(5);

        if (ActivityCompat.checkSelfPermission(ActivityTracker.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityTracker.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (getLocationCallback() != null) {
                System.out.println("Got Location Callback >>> ");
                getLocationCallback().onLocationChanged(location);
            }
        }
    }

    public LocationCallback getLocationCallback() {
        return locationCallback;
    }

    public void setLocationCallback(LocationCallback locationCallback) {
        this.locationCallback = locationCallback;
    }


    public void singleRequest(boolean isSingle) {

        this.singleTime = isSingle;
        startLocationUpdates();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        mGoogleApiClient.connect();
        if (locationUpdate) {
            startLocationUpdates();
        } else {
            stopLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            if (locationUpdate) {
                Toast.makeText(mContext, " Location Updated ", Toast.LENGTH_SHORT).show();
                MICustomLocation.getLocationManager().updateLocationCallback(location);
                System.out.println("####" + location.getLongitude());
                if (singleTime) {
                    stopLocationUpdates();
                }
            }
        }
    }

    public static class Builder {

        private static MILocationController.LocationParams P;

        public Builder(@NonNull Context context) {
            P = new MILocationController.LocationParams(context);
        }

        public Builder setMinDistance(float minDistance) {
            P.minDistance = minDistance;
            return this;
        }

        public Builder setTimeInterval(long timeInterval) {
            P.interval = timeInterval;
            return this;
        }

        public Builder setPriority(int priority) {
            P.priority = priority;
            return this;
        }

        public Builder setNumberOfUpdates(int numberOfUpdates) {
            P.numberOfUpdates = numberOfUpdates;
            return this;
        }

        public Builder setFastestTimeInterval(long fastesttimeInterval) {
            P.fastestInterval = fastesttimeInterval;
            return this;
        }

        public Builder setLocationCallback(LocationCallback locationCallback) {
            P.locationCallback = locationCallback;
            return this;
        }

        public Builder setLocationListner(LocationListener locationListner) {
            P.locationListener = locationListner;
            return this;
        }

        public CustomLocationService create() {
            CustomLocationService miLocationService = new CustomLocationService(ActivityTracker.getContext());
            P.apply(P);
            return miLocationService;
        }
    }

}
