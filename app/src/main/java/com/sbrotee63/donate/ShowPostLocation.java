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
import com.google.android.gms.maps.model.Marker;
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
    private Marker mMarker;

    String flag;
    String location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post_location);
        flag = getIntent().getStringExtra("flag");
        location = getIntent().getStringExtra("location");
        Toast.makeText(this, flag, Toast.LENGTH_SHORT).show();
        Log.d("mapFaiaz", "I am here");
        getLocationPermission();
    }

    private void getLocationPermission(){
        String[] permissions ={
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION        } ;
        Log.d("faiaz","getLocationPermission invoked");
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[0]) == PackageManager.PERMISSION_GRANTED){
            Log.d("faiaz",permissions[0]);
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[1]) == PackageManager.PERMISSION_GRANTED){
                Log.d("faiaz",permissions[1]);
                mLocationPermission = true;
                Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
                initMap();
            }
            else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION);
                Log.d("faiaz",permissions[1]+" revoked");
            }
        }
        else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION);
            Log.d("faiaz",permissions[0]+" revoked");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Toast.makeText(this, "onRequestPermissionsResult", Toast.LENGTH_SHORT).show();
        mLocationPermission = false;
        switch (requestCode){
            case LOCATION_PERMISSION: {
                if(grantResults.length > 0 ){
                    for(int i = 0; i < grantResults.length; ++i) {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermission = false;
                            Toast.makeText(this, permissions[i]+"****"+i, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    mLocationPermission = true;
                    Log.d("faiaz","onRequestPermissionsResult invoked");
                    initMap();
                }
            }
        }

    }

    private void init(){
        Log.d("faiaz.","init : initializing");


        findViewById(R.id.postlocation_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("faiaz","Something happened. ");

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
        findViewById(R.id.place_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("faiaz", "onClick: clicked place info");
                try{
                    if(mMarker.isInfoWindowShown()){
                        mMarker.hideInfoWindow();
                    }else{

                        mMarker.showInfoWindow();
                    }
                }catch (NullPointerException e){
                    Log.e("faiaz", "onClick: NullPointerException: " + e.getMessage() );
                }
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
            Log.d("faiaz","IOexception : "+e.getMessage());
        }
        if(list.size()>0){
            Address address = list.get(0);
            Log.d("faiaz","Found new location "+address.toString()+".");
            Toast.makeText(this,address.toString(),Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), ZOOM,address.getAddressLine(0));
        }
    }

    private void showPostLocation(){


        String searchstring = location;
        findViewById(R.id.relative1).setVisibility(View.INVISIBLE);
        findViewById(R.id.ic_gps).setVisibility(View.INVISIBLE);
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchstring,1);
        }catch (IOException e){
            Log.d("faiaz","IOexception : "+e.getMessage());
        }
        if(list.size()>0){
            Address address = list.get(0);
            Log.d("faiaz","Found new location "+address.toString()+".");
            Toast.makeText(this,address.toString(),Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), ZOOM,address.getAddressLine(0)
                    +"\n"+address.getPostalCode()
                    +"\n"+address.getPhone()
                    +"\n"+address.getUrl());
        }
    }
    private void showHospitalLocation(){
        String searchstring = location;
        findViewById(R.id.relative1).setVisibility(View.INVISIBLE);
        findViewById(R.id.ic_gps).setVisibility(View.INVISIBLE);
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchstring,1);
        }catch (IOException e){
            Log.d("faiaz","IOexception : "+e.getMessage());
        }
        if(list.size()>0){
            Address address = list.get(0);
            Log.d("faiaz","Found new location "+address.toString()+".");
            Toast.makeText(this,address.toString(),Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), ZOOM,address.getAddressLine(0)
                    +"\n"+address.getPostalCode()
                    +"\n"+address.getPhone()
                    +"\n"+address.getUrl());
        }
    }
    private void getDeviceLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Log.d("faiaz", "getDeviceLocation found");
        try {
            if (mLocationPermission) {
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d("faiaz", "location found");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), ZOOM, "my location ");
                        } else {
                            Log.d("faiaz", "current location not found");
                        }
                    }
                });
            }
        } catch (SecurityException s) {
            Log.d("faiaz", "SecurityException found");
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d("faiaz", "moveCamera found");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.setInfoWindowAdapter(new CustomWindowAdapter(this));
        mMap.clear();
        String snippet = title;
        MarkerOptions options = new MarkerOptions().position(latLng).title(title).snippet(snippet);
        if(title!="my location")
            mMarker = mMap.addMarker(options);
       // hideSoftKey();
    }
    private void hideSoftKey(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void initMap() {
        Toast.makeText(this, "initMap", Toast.LENGTH_SHORT).show();
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
                                    @RequiresApi(api = Build.VERSION_CODES.M)
                                    @Override
                                    public void onMapReady(GoogleMap googleMap) {
                                        mMap = googleMap;
                                        Log.d("faiaz", "initMap invoked");

                                        if (mLocationPermission) {
                                            if(flag!= null){
                                                if(flag.equals("post")){
                                                showPostLocation();
                                                Toast.makeText(ShowPostLocation.this,"location",Toast.LENGTH_SHORT) .show();
                                                }
                                                else{
                                                    showHospitalLocation();
                                                    Toast.makeText(ShowPostLocation.this,"hospial location",Toast.LENGTH_SHORT) .show();
                                                }
                                            }

                                            else {
                                                getDeviceLocation();
                                            }

                                            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                                return;
                                            }
                                            mMap.setMyLocationEnabled(true);
                                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                                            init();
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