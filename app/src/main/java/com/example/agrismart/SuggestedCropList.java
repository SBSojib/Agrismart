package com.example.agrismart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SuggestedCropList extends AppCompatActivity {

    Button button;
    TextView textView;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_crop_list);

        button = (Button) findViewById(R.id.LocationButton);
        textView = (TextView) findViewById(R.id.CityNameTextView);
        textView2 = (TextView) findViewById(R.id.CityNameTextView2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
                }
                else {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    try {
                        String city = hereLocation(location.getLatitude(), location.getLongitude());
                        textView.setText(city);
                        textView2.setText("I am Here");

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(SuggestedCropList.this, "Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000: {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    //Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    try {
                        String city = hereLocation(location.getLatitude(), location.getLongitude());
                        textView.setText(city);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(SuggestedCropList.this, "Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String hereLocation (double lat, double lon) {
        String cityName = "Searching...";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses=null;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
            //cityName = "S";
            if(addresses.size() > 0) {
                //cityName = "We are Here";
                for(Address adr: addresses) {
                    if(adr.getLocality() != null && adr.getLocality().length() > 0) {
                        cityName = adr.getLocality();
                        break;
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return cityName;
    }
}
