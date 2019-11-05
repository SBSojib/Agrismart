package com.example.agrismart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
    InputStream ins=null;


    Button btn1;
    Button btn2;
    Button btn3;


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
        Button btn1= (Button)findViewById(R.id.addFileFert);
        Button btn2= (Button)findViewById(R.id.addFilePest);
        Button btn3= (Button)findViewById(R.id.addFilePrc);
        Button btn4 = (Button)findViewById(R.id.addNewTechnology);
        Button btn5 = (Button)findViewById(R.id.addFileProgress);

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
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPdf(1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPdf(2);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPdf(3);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPdf(4);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPdf(5);
            }
        });
    }
    private void selectPdf(int id) {
        Intent intent = new Intent();
        intent.setType("text/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if(id==1){startActivityForResult(Intent.createChooser(intent,"select txt file"),1);}
        if(id==2){startActivityForResult(Intent.createChooser(intent,"select txt file"),2);}
        if(id==3){startActivityForResult(Intent.createChooser(intent,"select txt file"),3);}
        if(id==4){startActivityForResult(Intent.createChooser(intent,"select txt file"),4);}
        if(id==5){startActivityForResult(Intent.createChooser(intent,"select txt file"),5);}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            covertFile(data.getData(),1);
        }
        if(requestCode==2 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            covertFile(data.getData(),2);
        }
        if(requestCode==3 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            covertFile(data.getData(),3);
        }
        if(requestCode==4 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            covertFile(data.getData(),4);
        }
        if(requestCode==5 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            covertFile(data.getData(),5);
        }
    }




    private void covertFile(Uri data,int id) {


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        String myText = "";
        int in;
        try {
            ins = getContentResolver().openInputStream(data);
            in = ins.read();
            while (in != -1)
            {
                byteArrayOutputStream.write(in);
                in = ins.read();
            }
            ins.close();

            myText = byteArrayOutputStream.toString();
        }catch (IOException e) {
            e.printStackTrace();
        }

        String nameCrop=name.getText().toString();
        CropPdf c= new CropPdf(nameCrop,myText);
        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(id==1){
            mDatabase.child("Fertilizer").child(nameCrop).setValue(c);}
        if(id==2){
            mDatabase.child("Insecticide").child(nameCrop).setValue(c);}
        if(id==3){
            mDatabase.child("MarketPrice").child(nameCrop).setValue(c);}
        if(id==4){
            mDatabase.child("NewTechnology").child(nameCrop).setValue(c);}
        if(id==5){
            mDatabase.child("Progress").child(nameCrop).setValue(c);}

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
