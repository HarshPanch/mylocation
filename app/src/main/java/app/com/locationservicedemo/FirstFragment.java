package app.com.locationservicedemo;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.com.customlocationservice.LocationCallback;
import app.com.customlocationservice.MICustomLocation;

/**
 * Created by mind on 3/1/17.
 */
public class FirstFragment extends Fragment {

    TextView tv_latlng;
    LinearLayout ll_type;
    Button btn_start, btn_stop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.common_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_latlng = (TextView) view.findViewById(R.id.tv_latlng);
        ll_type = (LinearLayout) view.findViewById(R.id.ll_type);
        ll_type.setBackgroundColor(getActivity().getResources().getColor(R.color.frag_one));

        btn_start = (Button) view.findViewById(R.id.btn_start);
        btn_stop = (Button) view.findViewById(R.id.btn_stop);

        MICustomLocation.getLocationManager().addLocationCallback(locationCallback);

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MICustomLocation.getLocationManager().setLocationUpdatesEnabled(false);
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MICustomLocation.getLocationManager().setLocationUpdatesEnabled(true);
            }
        });

    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationChanged(Location location) {
            System.out.println("####" + location + " @@@@" + location.getLongitude());
            tv_latlng.setText("Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude());
        }
    };

}
