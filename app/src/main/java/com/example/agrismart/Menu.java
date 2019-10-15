package com.example.agrismart;

 import androidx.appcompat.app.AppCompatActivity;

         import android.content.Intent;
         import android.os.Bundle;
 import android.view.View;
         import android.widget.Button;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


    }
}