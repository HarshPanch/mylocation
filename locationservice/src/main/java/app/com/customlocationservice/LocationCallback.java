package app.com.customlocationservice;

import android.location.Location;

/**
 * Created by mind on 2/1/17.
 */

public interface LocationCallback {
    void onLocationChanged(Location location);
}
