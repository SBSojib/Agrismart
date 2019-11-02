package com.example.agrismart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminPage extends AppCompatActivity {
    DatabaseReference mDatabase;

    EditText name = null;
    EditText month = null;
    EditText latitude = null;
    EditText longitude = null;
    EditText minTemp = null;
    EditText maxTemp = null;
    EditText minHumidity = null;
    EditText maxHimidity = null;
    EditText fertilizingInterval = null;
    EditText totalFertilizingTime = null;
    EditText insecticideInterval = null;
    EditText totalInsecticideTime = null;
    EditText cultivationDuration = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        name= (EditText)findViewById(R.id.crpName);
        latitude= (EditText)findViewById(R.id.crpLat);
        longitude= (EditText)findViewById(R.id.crpLon);
        month= (EditText)findViewById(R.id.crpMon);


        minTemp = (EditText) findViewById(R.id.minTempOfCrop);
        maxTemp = (EditText) findViewById(R.id.maxTempOfCrop);
        minHumidity = (EditText) findViewById(R.id.minHumidityOfCrop);
        maxHimidity = (EditText) findViewById(R.id.maxHumidityOfCrop);
        fertilizingInterval = (EditText) findViewById(R.id.fertilizingIntervalOfCrop);
        totalFertilizingTime = (EditText) findViewById((R.id.totalFertiliaztionTimeOfCrop));
        insecticideInterval = (EditText) findViewById(R.id.insecticideIntervalOfCrop);
        totalInsecticideTime = (EditText) findViewById(R.id.totalInsecticideTimeOfCrop);
        cultivationDuration = (EditText) findViewById(R.id.cultivationDurationOfCrop);


        Button btn = (Button)findViewById(R.id.addcrop);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameValue = name.getText().toString();

                int latitudeValue = Integer.valueOf(latitude.getText().toString());
                int longitudeValue = Integer.valueOf(longitude.getText().toString());
                int monthValue = Integer.valueOf(month.getText().toString());

                int minTempValue = Integer.valueOf(minTemp.getText().toString());
                int maxTempValue = Integer.valueOf(maxTemp.getText().toString());
                int minHumidityValue = Integer.valueOf(minHumidity.getText().toString());
                int maxHumidityValue = Integer.valueOf(maxHimidity.getText().toString());
                int fertilizerIntervalValue = Integer.valueOf(fertilizingInterval.getText().toString());
                int totalFertilizingTimeValue = Integer.valueOf(totalFertilizingTime.getText().toString());
                int insecticideIntervalValue = Integer.valueOf(insecticideInterval.getText().toString());
                int totalInsecticideTimeValue = Integer.valueOf((totalInsecticideTime.getText().toString()));
                int cultivationDurationValue = Integer.valueOf(cultivationDuration.getText().toString());
// ...
                mDatabase = FirebaseDatabase.getInstance().getReference();
                Crop2 c = new Crop2(nameValue,monthValue,latitudeValue,longitudeValue, minTempValue, maxTempValue, minHumidityValue, maxHumidityValue,
                        fertilizerIntervalValue, totalFertilizingTimeValue, insecticideIntervalValue,
                            totalInsecticideTimeValue, cultivationDurationValue);
                String m= c.getName();
                mDatabase.child("SuggestedCrops").child(m).setValue(c);
                Toast.makeText(getApplicationContext(), "Crop Added", Toast.LENGTH_LONG).show();
                name.getText().clear();
                latitude.getText().clear();
                longitude.getText().clear();
                month.getText().clear();
                minTemp.getText().clear();
                maxTemp.getText().clear();
                minHumidity.getText().clear();
                maxHimidity.getText().clear();
                fertilizingInterval.getText().clear();
                totalFertilizingTime.getText().clear();
                insecticideInterval.getText().clear();
                totalInsecticideTime.getText().clear();
                cultivationDuration.getText().clear();
            }
        });
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AdminPage.this);
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

    }
}
