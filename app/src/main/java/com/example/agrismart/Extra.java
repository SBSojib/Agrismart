package com.example.agrismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    }
}
