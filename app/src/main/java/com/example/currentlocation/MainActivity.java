package com.example.currentlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    boolean flag = true;
    Button btLocation;
    TextView textView1, textView2, textView3, textView4, textView5;
    FusedLocationProviderClient fusedLocationProviderClient;
    SupportMapFragment supportMapFragment;
    double lat, lng;
    String countryName, locality, addressLine;


    Button btView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLocation = (Button) findViewById(R.id.bt_location);
        textView1 = (TextView) findViewById(R.id.text_view1);
        textView2 = (TextView) findViewById(R.id.text_view2);
        textView3 = (TextView) findViewById(R.id.text_view3);
        textView4 = (TextView) findViewById(R.id.text_view4);
        textView5 = (TextView) findViewById(R.id.text_view5);

        //Init fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        if(ActivityCompat.checkSelfPermission(MainActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getLocation();
        }
        else{
            ActivityCompat.requestPermissions(MainActivity.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }



        btView = findViewById(R.id.bt_activity2);
        btView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        location_click(btLocation);
    }


    public void location_click(View view) {
        textView1.setText(Html.fromHtml("<font color='#6200EE'><b>Latitude: </b><br></font>"
                + lat
        ));

        textView2.setText(Html.fromHtml("<font color='#6200EE'><b>Longtitude: </b><br></font>"
                + lng
        ));

        textView3.setText(Html.fromHtml("<font color='#6200EE'><b>Country Name: </b><br></font>"
                + countryName
        ));

        textView4.setText(Html.fromHtml("<font color='#6200EE'><b>Locality: </b><br></font>"
                +  locality
        ));

        textView5.setText(Html.fromHtml("<font color='#6200EE'><b>Address Line: </b><br></font>"
                + addressLine
        ));
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            try {
                                Geocoder geocoder = new Geocoder(MainActivity.this
                                        , Locale.getDefault());

                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(), location.getLongitude(), 1
                                );

                                lat = addresses.get(0).getLatitude();
                                lng = addresses.get(0).getLongitude();
                                countryName = addresses.get(0).getCountryName();
                                locality = addresses.get(0).getLocality();
                                addressLine = addresses.get(0).getAddressLine(0);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            LatLng latLng = new LatLng(lat, lng);

                            MarkerOptions options = new MarkerOptions().position(latLng).title("I am there");

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                            googleMap.addMarker(options);
                        }
                    });



                }
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        if(requestCode == 44){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation();
            }
        }
    }
}