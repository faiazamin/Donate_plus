package com.sbrotee63.donate;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ShowPostLocation extends FragmentActivity {

    private GoogleMap mMap;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private boolean mLocationPermission = false;
    private static final int LOCATION_PERMISSION = 1234;
    private static final float ZOOM = 15f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post_location);
        getLocationPermission();
    }

    private void getLocationPermission(){
        String[] permissions ={
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION        } ;
        Log.d("newTag","getLocationPermission invoked");
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[0]) == PackageManager.PERMISSION_GRANTED){
            Log.d("newTag",permissions[0]);
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[1]) == PackageManager.PERMISSION_GRANTED){
                Log.d("newTag",permissions[1]);
                mLocationPermission = true;
                initMap();
            }
            else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION);
                Log.d("newTag",permissions[1]+"revoked");
            }
        }
        else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION);
            Log.d("newTag",permissions[0]+"revoked");
        }
    }

    private void getDeviceLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Log.d("newTag", "getDeviceLocation found");
        try {
            if (mLocationPermission) {
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d("newTag", "location found");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), ZOOM);
                        } else {
                            Log.d("newTag", "current location not found");
                        }
                    }
                });
            }
        } catch (SecurityException s) {
            Log.d("newTag", "SecurityException found");
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d("newTag", "moveCamera found");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
                                    @RequiresApi(api = Build.VERSION_CODES.M)
                                    @Override
                                    public void onMapReady(GoogleMap googleMap) {
                                        mMap = googleMap;
                                        Log.d("newTag", "initMap invoked");

                                        if (mLocationPermission) {
                                            getDeviceLocation();

                                            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                                return;
                                            }
                                            mMap.setMyLocationEnabled(true);
                                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                }


            }
        }
        );



    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermission = false;

        if(grantResults.length > 0 ){
            for(int i = 0; i < grantResults.length; ++i) {
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    mLocationPermission = false;
                    return;
                }
            }

            mLocationPermission = true;
            Log.d("newTag","onRequestPermissionsResult invoked");
            initMap();
        }
    }
}
