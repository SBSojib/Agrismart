package com.example.agrismart;

 import androidx.appcompat.app.AlertDialog;
 import androidx.appcompat.app.AppCompatActivity;

 import android.content.DialogInterface;
 import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.app.NotificationCompat;
 import androidx.core.app.NotificationManagerCompat;

 import android.app.Notification;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.EditText;

 import androidx.annotation.NonNull;
 import androidx.annotation.Nullable;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.app.ActivityCompat;

 import android.Manifest;
 import android.app.ProgressDialog;
 import android.content.Intent;
 import android.content.pm.PackageManager;
 import android.location.Location;
 import android.os.AsyncTask;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.View;
 import android.widget.AdapterView;
 import android.widget.Button;
 import android.widget.ImageView;
 import android.widget.ListView;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.example.agrismart.Common.Common;
 import com.example.agrismart.Helper.Helper;
 import com.example.agrismart.Model.OpenWeatherMap;
 import com.google.android.gms.common.ConnectionResult;
 import com.google.android.gms.common.GooglePlayServicesUtil;
 import com.google.android.gms.common.api.GoogleApiClient;
 import com.google.android.gms.location.LocationRequest;
 import com.google.android.gms.location.LocationServices;
 import com.google.android.gms.location.LocationListener;
 import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.database.DataSnapshot;
 import com.google.firebase.database.DatabaseError;
 import com.google.firebase.database.DatabaseReference;
 import com.google.firebase.database.FirebaseDatabase;
 import com.google.firebase.database.ValueEventListener;
 import com.google.gson.Gson;
 import com.google.gson.reflect.TypeToken;
 import com.squareup.picasso.Picasso;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.app.NotificationCompat;
 import androidx.core.app.NotificationManagerCompat;

 import android.app.Notification;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.EditText;

 import static com.example.agrismart.Notification.CHANNEL_1_ID;
 import static com.example.agrismart.Notification.CHANNEL_2_ID;


 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.lang.reflect.Type;
 import java.text.DateFormat;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.List;

 import static com.google.android.gms.common.api.GoogleApiClient.*;


 import static com.example.agrismart.Notification.CHANNEL_1_ID;
 import static com.example.agrismart.Notification.CHANNEL_2_ID;



public class Menu extends AppCompatActivity implements ConnectionCallbacks,
        OnConnectionFailedListener, LocationListener{

    private NotificationManagerCompat notificationManager;

    OpenWeatherMap openWeatherMap = new OpenWeatherMap();
    int MY_PERMISSION = 0;
    int windSpeed = 0;
    String condition = "OK";

    private static final int MY_PERMISSION_REQUEST_CODE = 7171;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 7172;
    private TextView txtCoordinates;
    private Button btnGetCoordinates, btnLocationUpdates;
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private static int UPDATE_INTERVAL = 5000; // SEC
    private static int FATEST_INTERVAL = 3000; // SEC
    private static int DISPLACEMENT = 10; // METERS

    double lat = 0;
    double lon = 0;
    int mon = 0;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkPlayServices()) {
                        buildGoogleApiClient();
                        createLocationRequest();
                    }
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        notificationManager = NotificationManagerCompat.from(this);

        Button next = (Button) findViewById(R.id.registerCrops);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, SuggestPage.class));
            }
        });
        Button next1 = (Button) findViewById(R.id.myCrops);
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, MyCrops.class));
            }
        });
        Button btn = (Button) findViewById(R.id.logOut);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(Menu.this, SignIn.class));
            }
        });
        Button next2 = (Button) findViewById(R.id.browseCrops);
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, BrowseCrop.class));
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Run-time request permission
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, MY_PERMISSION_REQUEST_CODE);
        } else {
            if (checkPlayServices()) {
                buildGoogleApiClient();
                createLocationRequest();
            }
        }


    }
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
        if(mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void tooglePeriodicLoctionUpdates() {
        if(!mRequestingLocationUpdates)
        {
            //btnLocationUpdates.setText("Stop location update");
            mRequestingLocationUpdates = true;
            startLocationUpdates();
        }
        else
        {
            //btnLocationUpdates.setText("Start location update");
            mRequestingLocationUpdates = false;
            stopLocationUpdates();
        }
    }


    private void displayLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            lat = latitude;
            lon = longitude;
            Log.e("ErrorLat",String.valueOf(lat));
            Log.e("ErrorLon",String.valueOf(lon));

            new getWeather().execute(Common.apiRequest(String.valueOf(lat), String.valueOf(lon)));

            Log.e("Debuging: ", "Reached");
            //txtCoordinates.setText(lat + " / " + lon);
        }
        else {
            lat = 0;
            lon = 0;
            Log.e("Debuging: ", "Not Reached");
            //txtCoordinates.setText(lat+" / "+lon);
        }

    }
/*
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
        builder.setTitle("Exit ?");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("You will be Logged Out")
                .setCancelable(false)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }*/

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);

    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        //Fix first time run app if permission doesn't grant yet so can't get anything
        mGoogleApiClient.connect();


    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), "This device is not supported", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    private void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        tooglePeriodicLoctionUpdates();
        displayLocation();
        if(mRequestingLocationUpdates) {
            //startLocationUpdates();
        }

    }


    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        //displayLocation();
    }

    public void sendOnChannel1(View v) {
        String title = "Warning";
        String massage = "It's raining Heavily";
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_1)
                .setContentTitle(title)
                .setContentText(massage)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1,notification);
    }
    public void sendOnChannel2(View v) {
        String title = "Warning";
        String massage = "Save your Crops, it's Cyclone";
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_1)
                .setContentTitle(title)
                .setContentText(massage)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(2,notification);
    }

    public class getWeather extends AsyncTask<String,Void,String> {

        ProgressDialog pd = new ProgressDialog(Menu.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
            //pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];

            Helper http = new Helper();
           //Problem is here, stream is recieving null value

            stream = http.getHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

           /* if(s.contains("Error: Not found city")) {
                Log.e("Massage", "Damn You");
                pd.dismiss();
                return;
            }*/
            Log.e("Debuging", "Finding BUG");
            Gson gson = new Gson();
            Type mType = new TypeToken<OpenWeatherMap>(){}.getType();

            openWeatherMap = gson.fromJson(s,mType);
            pd.dismiss();


            //textCity.setText(String.format("%s,%s",openWeatherMap.getName(), openWeatherMap.getSys().getCountry()));
            //textLastUpdate.setText(String.format("Last Updated: %s", Common.getDateNow()));

            condition = openWeatherMap.getWeather().get(0).getDescription();
            double Speed = openWeatherMap.getWind().getSpeed();
            windSpeed = (int)Speed;
            windSpeed = 10;
            Log.e("Speed",String.valueOf(windSpeed));

            int indicator = MainActivity.flag;
            Log.e("Value",String.valueOf(indicator));
            if(indicator == 0) {
                MainActivity.flag = 1;
                if (condition.equals("heavy rain")) {
                    View v = null;
                    sendOnChannel1(v);
                }
                if (condition.equals("heavy snow")) {
                    View v = null;
                    sendOnChannel1(v);

                }
                if (windSpeed >= 5) {
                    View v = null;
                    sendOnChannel2(v);
                }
            }

        }
    }
}
