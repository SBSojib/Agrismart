package com.example.agrismart;

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

public class Extra extends AppCompatActivity implements ConnectionCallbacks,
        OnConnectionFailedListener, LocationListener {

    TextView textCity;
    TextView textLastUpdate;
    TextView textDescription;
    TextView textHumidity;
    TextView textTimeSunrise;
    TextView textTimeSunset;
    TextView textCelcius;
    ImageView imageView;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();
    int MY_PERMISSION = 0;

    int temperature = 0;
    int humidity = 0;
    int windSpeed = 0;
    int fertilizingInterval=0;
    int insecticideInterval=0;
    int cultivationDuration = 0;
    String condition = "OK";
    String cropNameInExtra="Crop";
    TextView temperatureUpdate;
    TextView humidityUpdate;
    TextView fertilizationUpdate;
    TextView insecticideUpdate;

    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMassage;

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

    ListView listView;
    List<Crop2> list;
    ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    MyAdapter2 myAdapter;


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
        setContentView(R.layout.activity_extra);

        textCity = (TextView) findViewById(R.id.textCity);
        textLastUpdate = (TextView) findViewById(R.id.textLastUpdate);
        textDescription = (TextView) findViewById(R.id.textDescription);
        textHumidity = (TextView) findViewById(R.id.textHumidity);
        textTimeSunrise = (TextView) findViewById(R.id.textTimeSunrise);
        textTimeSunset = (TextView) findViewById(R.id.textTimeSunset);
        textCelcius = (TextView) findViewById(R.id.textCelcius);
        imageView = (ImageView) findViewById(R.id.imageView);

        //txtCoordinates = (TextView) findViewById(R.id.textView);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());
        //TextView textViewDate = (TextView) findViewById(R.id.textView2);
        String month="";
        for(int i=0; ;i++) {
            if(currentDate.charAt(i) == '/') break;
            month += currentDate.charAt(i);
            mon = Integer.parseInt(month);
        }
        //textViewDate.setText(month);

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



        //dataRetrive();


        Intent i = getIntent();
        final String name= i.getStringExtra("crname");
        Button next1 = (Button) findViewById(R.id.fertilizingButton);
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Extra.this,FileView.class);
                i.putExtra("crop",name);
                i.putExtra("func",1);
                startActivity(i);
            }
        });
        Button next2 = (Button) findViewById(R.id.insecticideButton);
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Extra.this,FileView.class);
                i.putExtra("crop",name);
                i.putExtra("func",2);
                startActivity(i);
            }
        });
        Button next3 = (Button) findViewById(R.id.newtechButton);
        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Extra.this, NewTechnology.class));
            }
        });
        Button next4 = (Button) findViewById(R.id.progressButton);
        next4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Extra.this, Progress.class));
            }
        });
        Button next5 = (Button) findViewById(R.id.marketpriceButon);
        next5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Extra.this,FileView.class);
                i.putExtra("crop",name);
                i.putExtra("func",3);
                startActivity(i);
            }
        });

        /*
        TextView myTextView = (TextView)findViewById(R.id.cropDescrpt);

        InputStream inputStream;
        if("Potato".equals(name)){
            inputStream = getResources().openRawResource(R.raw.potato);
        }
        else if("Wheat".equals(name)){
            inputStream = getResources().openRawResource(R.raw.wheat);
        }
        else if("Corn".equals(name)){
            inputStream = getResources().openRawResource(R.raw.corn);
        }
        else if("Tomato".equals(name)){
            inputStream = getResources().openRawResource(R.raw.tomato);
        }
        else{
            inputStream = getResources().openRawResource(R.raw.rice);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        String myText = "";
        int in;
        try {
            in = inputStream.read();
            while (in != -1)
            {
                byteArrayOutputStream.write(in);
                in = inputStream.read();
            }
            inputStream.close();

            myText = byteArrayOutputStream.toString();
        }catch (IOException e) {
            e.printStackTrace();
        }

        myTextView.setText(myText);
        */
        

    }


    @Override
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
            new getWeather().execute(Common.apiRequest(String.valueOf(lat), String.valueOf(lon)));
            dataRetrive();
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

    public void dataRetrive() {
        //listView = (ListView) findViewById(R.id.listOfSuggestedCrops);
        //list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Retreiving");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("SuggestedCrops");
        databaseReference.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                //list.clear();

                for(DataSnapshot snap: dataSnapshot.getChildren()) {
                    Crop2 cropName = snap.getValue(Crop2.class);

                    String s1 = String.valueOf(lat);
                    String s2 = String.valueOf(lon);
                    Log.e("Latitude: ",s1);
                    Log.e("Longitude: ",s2);

                    String slat = String.valueOf((cropName.latitude));
                    String slon = String.valueOf(cropName.longitude);
                    String smon = String.valueOf(cropName.month);

                    cropNameInExtra = MyCrops.selectedCropName;
                    //Log.e("Crop",cropNameInExtra);
                    //Log.e("CropF",cropName.name);

                    if((cropName.name).equals(cropNameInExtra)) {
                        //list.add(cropName);
                        //Log.e("Temp",String.valueOf(cropName.minTemp));
                        temperatureUpdate = (TextView) findViewById(R.id.temperatureUpdateOfCrop);
                        humidityUpdate = (TextView) findViewById(R.id.humidityUpdateOfCrop);
                        fertilizationUpdate = (TextView) findViewById(R.id.fertilizationUpdateOfCrop);
                        insecticideUpdate = (TextView) findViewById(R.id.insecticideUpdateOfCrop);

                        Log.e("TemparatureCrop",String.valueOf(cropName.maxTemp));
                        Log.e("Temparature",String.valueOf(temperature));
                        if(temperature<cropName.minTemp) {
                            temperatureUpdate.setText("Temperature has gone below the minimum," +
                                    " take necessary steps or crops can be harmed");

                        }
                        else if(temperature>cropName.maxTemp) {
                            temperatureUpdate.setText("Temperature has gone above the maximum," +
                                    " take necessary steps or crops can be harmed");
                        }
                        else{
                            temperatureUpdate.setText("Temperature is fine for the crop");
                        }

                        if(humidity<cropName.minHumidity ) {
                            humidityUpdate.setText("Humidity has gone below the minimum," +
                                    " take necessary steps or crops can be harmed");
                        }
                        else if(humidity>cropName.maxHumidity) {
                            humidityUpdate.setText("Humidity has gone above the maximum," +
                                    " take necessary steps or crops can be harmed");
                        }
                        else{
                            humidityUpdate.setText("Humidity is fine for the crop");
                        }

                        fertilizationUpdate.setText("You need to fertilize the crops in "+
                                String.valueOf(cropName.fertilizingInterval)+" days");
                        insecticideUpdate.setText("You need to give necessary Insecticide in "+
                                String.valueOf(cropName.insecticideInterval)+" days");

                    }


                    //myAdapter = new MyAdapter2(Extra.this, R.layout.items,list);
                    //listView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }));

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Extra.this,RegistrationInput.class);
                Crop2 c=list.get(position);
                System.out.println(c.getName());
                intent.putExtra("pos",c.getName());
                startActivity(intent);
            }
        });*/
    }


    public class getWeather extends AsyncTask<String,Void,String> {

        ProgressDialog pd = new ProgressDialog(Extra.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];

            Helper http = new Helper();
            stream = http.getHTTPData(urlString);

            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.contains("Error: Not found city")) {
                Log.e("Massage", "Damn You");
                pd.dismiss();
                return;
            }
            Log.e("Debuging", "Finding BUG");
            Gson gson = new Gson();
            Type mType = new TypeToken<OpenWeatherMap>(){}.getType();

            openWeatherMap = gson.fromJson(s,mType);
            pd.dismiss();

            //textCity.setText(String.format("%s,%s",openWeatherMap.getName(), openWeatherMap.getSys().getCountry()));
            //textLastUpdate.setText(String.format("Last Updated: %s", Common.getDateNow()));
            textDescription.setText((String.format("%s",openWeatherMap.getWeather().get(0).getDescription())));
            textHumidity.setText((String.format("Humidity: %d%%",openWeatherMap.getMain().getHumidity())));
            textTimeSunrise.setText((String.format("Sunrise: %s am",Common.unixTimeStamptoDateTime(openWeatherMap.getSys().getSunrise()))));
            textTimeSunset.setText((String.format("Sunset: %s pm", Common.unixTimeStamptoDateTime(openWeatherMap.getSys().getSunset()))));

            double kelvinTemp = (openWeatherMap.getMain().getTemp());
            kelvinTemp-=273;
            textCelcius.setText(String.format("Temperature: %.2f °C", kelvinTemp));
            Picasso.with(Extra.this)
                    .load(Common.getImage(openWeatherMap.getWeather().get(0).getIcon())).into(imageView);

            double temp = openWeatherMap.getMain().getTemp();
            temperature = (int)temp;
            temperature-=273;
            humidity = openWeatherMap.getMain().getHumidity();
            condition = openWeatherMap.getWeather().get(0).getDescription();
            double Speed = openWeatherMap.getWind().getSpeed();
            windSpeed = (int)Speed;
            windSpeed = 10;
            Log.e("Speed",String.valueOf(windSpeed));

        }
    }
}



