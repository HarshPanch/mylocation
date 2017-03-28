package app.com.locationservicedemo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import app.com.customlocationservice.ActivityTracker;
import app.com.customlocationservice.MICustomLocation;
import app.com.customlocationservice.ServiceTools;

public class MainActivity extends MIActivity implements View.OnClickListener {

    Button btn1, btn2, btn3;
    FirstFragment firstFragment;
    SecondFragment secoundFragment;
    ThirdFragment thirdFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityTracker.setContext(MainActivity.this);

        LocationServiceApplication locationServiceApplication = (LocationServiceApplication) this.getApplicationContext();
        locationServiceApplication.mainActivity = this;

        if (firstFragment == null) {
            firstFragment = new FirstFragment();
        }

        if (secoundFragment == null) {
            secoundFragment = new SecondFragment();
        }

        if (thirdFragment == null) {
            thirdFragment = new ThirdFragment();
        }

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        MICustomLocation.getLocationManager().setLocationUpdatesEnabled(true);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000:
                if (permissions.length > 0) {
                    if (permissions[1].equalsIgnoreCase(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            MICustomLocation.getLocationManager().setLocationUpdatesEnabled(true);
                        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                            } else {
                                Toast.makeText(MainActivity.this, "Your location permission was deny. Please Give location permission from app settings.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        LocationServiceApplication locationServiceApplication = (LocationServiceApplication) this.getApplicationContext();
        if (ServiceTools.isServiceRunning("CustomLocationService")) {
            Toast.makeText(MainActivity.this, "Service start", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Service end", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn1:
                if (!firstFragment.isAdded()) {
                    addFragment(firstFragment, false, false);
                } else {
                    if (firstFragment.isHidden()) {
                        if (!secoundFragment.isHidden() || !thirdFragment.isHidden()) {
                            getSupportFragmentManager().beginTransaction().hide(secoundFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(thirdFragment).commit();
                        }
                        getSupportFragmentManager().beginTransaction().show(firstFragment).commit();
                    }

                }

                break;

            case R.id.btn2:
                if (!secoundFragment.isAdded()) {
                    addFragment(secoundFragment, false, false);
                } else {
                    if (secoundFragment.isHidden()) {
                        if (!firstFragment.isHidden() || !thirdFragment.isHidden()) {
                            getSupportFragmentManager().beginTransaction().hide(firstFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(thirdFragment).commit();
                        }
                        getSupportFragmentManager().beginTransaction().show(secoundFragment).commit();
                    }

                }
                break;

            case R.id.btn3:
                if (!thirdFragment.isAdded()) {
                    addFragment(thirdFragment, false, false);
                } else {
                    if (thirdFragment.isHidden()) {
                        if (!firstFragment.isHidden() || !secoundFragment.isHidden()) {
                            getSupportFragmentManager().beginTransaction().hide(firstFragment).commit();
                            getSupportFragmentManager().beginTransaction().hide(secoundFragment).commit();
                        }
                        getSupportFragmentManager().beginTransaction().show(thirdFragment).commit();
                    }

                }
                break;
        }
    }
}
