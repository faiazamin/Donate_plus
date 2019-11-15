package com.sbrotee63.donate;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowPostLocation extends FragmentActivity {

    private GoogleMap mMap;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private boolean mLocationPermission = false;
    private static final int LOCATION_PERMISSION = 1234;
    private static final float ZOOM = 15f;

    String flag;
    String location;
    //private EditText searchbar = (EditText) findViewById(R.id.postlocation_searchbar);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post_location);
        flag = getIntent().getStringExtra("flag");
        location = getIntent().getStringExtra("location");

        getLocationPermission();

    }

    private void getLocationPermission(){
        String[] permissions ={
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION        } ;
        Log.d("map","getLocationPermission invoked");
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[0]) == PackageManager.PERMISSION_GRANTED){
            Log.d("map",permissions[0]);
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[1]) == PackageManager.PERMISSION_GRANTED){
                Log.d("map",permissions[1]);
                mLocationPermission = true;
                initMap();
            }
            else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION);
                Log.d("map",permissions[1]+" revoked");
            }
        }
        else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION);
            Log.d("map",permissions[0]+" revoked");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermission = false;
        switch (requestCode){
            case LOCATION_PERMISSION: {
                if(grantResults.length > 0 ){
                    for(int i = 0; i < grantResults.length; ++i) {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermission = false;
                            return;
                        }
                    }

                    mLocationPermission = true;
                    Log.d("map","onRequestPermissionsResult invoked");
                    initMap();
                }
            }
        }

    }

    private void init(){
        Log.d("map.","init : initializing");


        findViewById(R.id.postlocation_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("map","Something happened. ");

                geoLocat();
            }
        });

         findViewById(R.id.ic_gps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    getDeviceLocation();

                hideSoftKey();
            }
        });
    }

    private void geoLocat(){

        EditText searchbar =  findViewById(R.id.postlocation_searchbar);
        String searchstring = searchbar.getText().toString();
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchstring,1);
        }catch (IOException e){
            Log.d("map","IOexception : "+e.getMessage());
        }
        if(list.size()>0){
            Address address = list.get(0);
            Log.d("map","Found new location "+address.toString()+".");
            Toast.makeText(this,address.toString(),Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), ZOOM,address.getAddressLine(0));
        }
    }

    private void showPostLocation(){


        String searchstring = location;
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchstring,1);
        }catch (IOException e){
            Log.d("map","IOexception : "+e.getMessage());
        }
        if(list.size()>0){
            Address address = list.get(0);
            Log.d("map","Found new location "+address.toString()+".");
            Toast.makeText(this,address.toString(),Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), ZOOM,address.getAddressLine(0));
        }
    }
    private void getDeviceLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Log.d("map", "getDeviceLocation found");
        try {
            if (mLocationPermission) {
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d("map", "location found");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), ZOOM, "my location ");
                        } else {
                            Log.d("map", "current location not found");
                        }
                    }
                });
            }
        } catch (SecurityException s) {
            Log.d("map", "SecurityException found");
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d("map", "moveCamera found");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        MarkerOptions options = new MarkerOptions().position(latLng).title(title);
        if(title!="my location")
                mMap.addMarker(options);
        hideSoftKey();
    }
    private void hideSoftKey(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void initMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
                                    @RequiresApi(api = Build.VERSION_CODES.M)
                                    @Override
                                    public void onMapReady(GoogleMap googleMap) {
                                        mMap = googleMap;
                                        Log.d("map", "initMap invoked");

                                        if (mLocationPermission) {
                                            if(flag!= null){
                                                showPostLocation();
                                                Toast.makeText(ShowPostLocation.this,"location",Toast.LENGTH_SHORT) .show();
                                            }else {
                                                getDeviceLocation();
                                            }

                                            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                                return;
                                            }
                                            mMap.setMyLocationEnabled(true);
                                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                                            //init();
                                        }


                                    }
                                }
        );



    }





}


















/*searchbar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView searchbar, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        ||actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.KEYCODE_ENTER
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || actionId == EditorInfo.IME_ACTION_NEXT
                        || actionId == EditorInfo.IME_ACTION_GO
                        || actionId == EditorInfo.IME_ACTION_SEND
                ){
                    Log.d("map","Something happened. ");

                    geoLocat();
                }
                return false;
            }
        });*/