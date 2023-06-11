package com.example.instagram.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;


import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.instagram.R;
import com.example.instagram.databinding.ActivityMapsBinding;

import com.example.instagram.viewmodel.MapsViewModel;
import com.example.instagram.viewmodel.UploadPostViewModel;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private ActivityMapsBinding binding;
    private List<Address> list;
    private Geocoder geocoder;
    private String location;
    private Marker marker;
    private String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.ACCESS_FINE_LOCATION", "Manifest.permission.ACCESS_COARSE_LOCATION"
    };
    private int PERMISSIONS_REQUEST_CODE = 100;
    private MapsViewModel mapsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapsViewModel = new ViewModelProvider(this).get(MapsViewModel.class);
        geocoder = new Geocoder(MapsActivity.this);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        getSupportActionBar().hide();
        Bundle extras = getIntent().getExtras();
        setContentView(binding.getRoot());
        if (!checkIfPermissionsGranted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        } else {

        }
        Places.initialize(getApplicationContext(), "AIzaSyAwu6FO-Vb-ITp39cSydpdr7e6yYjdHP5k");
        PlacesClient placesClient = Places.createClient(this);
        AutocompleteSupportFragment autocompleteFragment =
                (AutocompleteSupportFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.autocomplete_fragment);
        assert autocompleteFragment != null;
        autocompleteFragment.setActivityMode(AutocompleteActivityMode.FULLSCREEN);
//        autocompleteFragment.requireView().setBackgroundColor(0xff0000);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i("xxx", "Place: " + place.getName() + ", " + place.getId());
                geocode(place.getName());
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i("xxx", "error: " + status);
            }
        });
        binding.saveBtn.setOnClickListener(v->{
            int id = extras.getInt("id");
            mapsViewModel.addLocation(id,location);
        });
        mapsViewModel.getObservedPost().observe(this, s -> {
            if (mapsViewModel.getObservedPost().getValue() != null) {


                finish();
            }

        });
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);


    }

    private boolean checkIfPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void geocode(String locationName) {

        try {
            list = geocoder.getFromLocationName(locationName, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double latitude = list.get(0).getLatitude();
        double longitude = list.get(0).getLongitude();
        if(marker!= null) {
            marker.remove();
        }
        LatLng latLng = new LatLng(latitude, longitude);
        location = locationName;
        marker = map.addMarker(new MarkerOptions().position(latLng).title(locationName));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);

        map.animateCamera(cameraUpdate);
        binding.saveBtn.setEnabled(true);

    }
}