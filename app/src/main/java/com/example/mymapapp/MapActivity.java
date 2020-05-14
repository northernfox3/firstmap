package com.example.mymapapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    GoogleMap mMap;
    private Marker marker;
    private GoogleApiClient mLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
       if(mMap == null) {
           SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                   .findFragmentById(R.id.map);
           mapFragment.getMapAsync(this);
           Toast.makeText(this, "Ready to map!", Toast.LENGTH_SHORT).show();


       }else {
           Toast.makeText(this, "Map not connected!", Toast.LENGTH_SHORT).show();
       }
    }


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng Melbourne = new LatLng(-37.720683, 145.048364);
        mMap.addMarker(new MarkerOptions().position(Melbourne).title("La Trobe University, Melbourne Campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Melbourne,18));


    }
    private void hideSoftKeyboard(View v) {
        InputMethodManager hide =
                (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        hide.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void Locate(View view) throws IOException {

        hideSoftKeyboard(view);
        TextView tv = (TextView) findViewById(R.id.editText1);
        String searchString = tv.getText().toString();
        Toast.makeText(this, "Searching: " + searchString, Toast.LENGTH_SHORT).show();

        Geocoder geo = new Geocoder(this);
        List<Address> list = geo.getFromLocationName(searchString,1);

        if(list.size()>0){
            Address a = list.get(0);
            String L = a.getLocality();

            double lat = a.getLatitude();
            double lng = a.getLongitude();
            LatLng newplace = new LatLng(lat,lng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newplace,18));

            if (marker != null) {
                marker.remove();
            }

            MarkerOptions options = new MarkerOptions()
                    .position(new LatLng(lat, lng));
            marker = mMap.addMarker(options);
        }
    }

    public void type(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }

    public void hybrid(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    public void normal(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void current(View view) {


    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}

