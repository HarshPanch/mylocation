package app.com.customlocationservice;

/**
 * Created by mind on 1/3/17.
 */

public class MICustomLocation {


    private static CustomLocationService.Builder builder;
    private static CustomLocationService customLocationService;
    private static LocationManager locationManager;


    public static void setBuilder(CustomLocationService.Builder builder) {
        MICustomLocation.builder = builder;
    }

    public static CustomLocationService.Builder getBuilder() {
        return builder;
    }


    public static CustomLocationService getCustomLocationService() {
        return customLocationService;
    }

    public static void setCustomLocationService(CustomLocationService customLocationService) {
        MICustomLocation.customLocationService = customLocationService;
    }


    public static LocationManager getLocationManager() {
        return locationManager;
    }

    public static void setLocationManager(LocationManager locationManager) {
        MICustomLocation.locationManager = locationManager;
    }

}
