package com.fit2081.fit2081_a2_tamekalougoon_32507356;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemsMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_map);

        // down cast and set up on click listener
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // translates longitude/langitude -> street address and vice versa
        geocoder = new Geocoder(this, Locale.getDefault());


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // set to open to australia
        LatLng latLng = new LatLng(-25.274399, 133.775131);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // output the country name
        // listen for a click on the map
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {


                // list of addresses
                List<Address> addresses = new ArrayList<>();
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                }
                // catch error in case there is no internet connection to prevent runtime errors
                catch (Exception e) {
                    // error
                    Toast.makeText(ItemsMapActivity.this, "No connection", Toast.LENGTH_LONG).show();
                }

                String msg;
                if (addresses.size() > 0) {
                    msg = addresses.get(0).getCountryName();
                } else {
                    msg = "No Country Clicked"; }
                Toast.makeText(ItemsMapActivity.this, msg, Toast.LENGTH_SHORT).show();

            }
        });
    }
}