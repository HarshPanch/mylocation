package app.com.customlocationservice;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mind on 2/1/17.
 */

public class LocationManager {

    private List<LocationCallback> locationCallbacks;
    public final Context mContext;
    LocationCallback locationCallback;
    CustomLocationService customLocationService;

    public void initList() {
        if (locationCallbacks == null) {
            locationCallbacks = new ArrayList<>();
        }
    }

    public LocationManager() {
        mContext = null;
    }

    public LocationManager(Context mContext, LocationCallback locationCallback) {
        this.mContext = mContext;
        this.locationCallback = locationCallback;
//        showSettingsAlert();
    }

    public void addLocationCallback(LocationCallback locationListener) {
        initList();
        locationCallbacks.add(locationListener);
        MICustomLocation.getCustomLocationService().startLocationUpdates();
    }

    public void removeLocationCallback(LocationCallback locationListener) {
        initList();
        if (!locationCallbacks.isEmpty()) {
            locationCallbacks.remove(locationListener);
        }
    }

    public void updateLocationCallback(Location location) {
//        startLocationUpdates();
        initList();
        for (int i = 0; i < locationCallbacks.size(); i++) {
            locationCallbacks.get(i).onLocationChanged(location);

//            locationCallbacks.get(i).onLocationChanged(location);
            System.out.println("LocationCallbacks Size >> ####" + locationCallbacks.size());
        }
    }

    public void setLocationUpdatesEnabled(boolean isUpdate) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(ActivityTracker.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(ActivityTracker.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityTracker.getContext().requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                        1000);

            } else {
                serviceSpeedUp(isUpdate);
            }
        } else {
            serviceSpeedUp(isUpdate);
        }

    }

    public void serviceSpeedUp(boolean isUpdate) {
        if (customLocationService == null) {
            System.out.println("customService is null..");
            customLocationService = MICustomLocation.getBuilder().create();
            MICustomLocation.setCustomLocationService(customLocationService);
        } else {
            System.out.println("customService is not null..");
        }

        if (isUpdate) {
            customLocationService.showSettingsAlert();
            customLocationService.setLocationUpdate(true);
        } else {
            MICustomLocation.getCustomLocationService().setLocationUpdate(false);
        }
    }

    public void requestSingleLocation(boolean isSingle) {
        MICustomLocation.getCustomLocationService().singleRequest(isSingle);
    }

}
