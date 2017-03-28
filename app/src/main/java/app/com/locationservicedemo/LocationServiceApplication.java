package app.com.locationservicedemo;

import android.app.Application;

import app.com.customlocationservice.CustomLocationService;
import app.com.customlocationservice.LocationManager;
import app.com.customlocationservice.MICustomLocation;
import app.com.customlocationservice.Priority;

/**
 * Created by mind on 2/1/17.
 */

public class LocationServiceApplication extends Application {


    MainActivity mainActivity;
    @Override
    public void onCreate() {
        super.onCreate();

        CustomLocationService.Builder builder = new CustomLocationService.Builder(this.getBaseContext())
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setFastestTimeInterval(2000)
                .setMinDistance(0.5f)
                .setNumberOfUpdates(15)
                .setTimeInterval(1000);

        MICustomLocation.setBuilder(builder);
        MICustomLocation.setLocationManager(new LocationManager());
        MICustomLocation.setCustomLocationService(new CustomLocationService());

    }

}
