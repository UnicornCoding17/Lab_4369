package com.s23010383.agralab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private EditText addressEditText;
    private Button searchButton, sensorbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        addressEditText = findViewById(R.id.addressEditText);
        searchButton = findViewById(R.id.searchButton);
        sensorbtn = findViewById(R.id.sensorbtn);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.id_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLocation();
            }
        });
        sensorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, SensorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;

        LatLng colombo = new LatLng(6.883338143074057, 79.88659109838935);
        myMap.addMarker(new MarkerOptions().position(colombo).title("Colombo"));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(colombo, 12.0f));
    }

    private void searchLocation() {
        String location = addressEditText.getText().toString().trim();
        if (location.isEmpty()) {
            Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show();
            return;
        }

        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocationName(location, 1);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: Check your network connection", Toast.LENGTH_SHORT).show();
        }

        if (addressList != null && !addressList.isEmpty()) {
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

            myMap.clear();
            myMap.addMarker(new MarkerOptions().position(latLng).title(location));
            myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
        } else {
            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
        }
    }


}