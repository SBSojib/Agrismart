package com.example.agrismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CropInfoShow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_info_show);
        final String name= getIntent().getStringExtra("crop");
        Button next1 = (Button) findViewById(R.id.FertilzerShow);
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CropInfoShow.this,Fertilizing.class);
                i.putExtra("crop",name);
                startActivity(i);
            }
        });
        Button next2 = (Button) findViewById(R.id.InsecticideShow);
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CropInfoShow.this,Insecticide.class);
                i.putExtra("crop",name);
                startActivity(i);
            }
        });
        Button next3 = (Button) findViewById(R.id.NewTechShow);
        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CropInfoShow.this,NewTechnology.class);
                i.putExtra("crop",name);
                startActivity(i);            }
        });
        Button next4 = (Button) findViewById(R.id.ProgressShow);
        next4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CropInfoShow.this,Progress.class);
                i.putExtra("crop",name);
                startActivity(i);            }
        });
        Button next5 = (Button) findViewById(R.id.MarketPriceShow);
        next5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CropInfoShow.this,MarketPrice.class);
                i.putExtra("crop",name);
                startActivity(i);
            }
        });
    }
}
