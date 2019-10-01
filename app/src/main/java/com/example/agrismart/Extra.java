package com.example.agrismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Extra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
        Button next1 = (Button) findViewById(R.id.fertilizingButton);
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Extra.this, Fertilizing.class));
            }
        });
        Button next2 = (Button) findViewById(R.id.insecticideButton);
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Extra.this, Insecticide.class));
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
                startActivity(new Intent(Extra.this, MarketPrice.class));
            }
        });
        TextView myTextView = (TextView)findViewById(R.id.cropDescrpt);
        Intent i = getIntent();
        String name= i.getStringExtra("crname");
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
    }
}
