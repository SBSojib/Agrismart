package com.example.agrismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

public class RegisterCrops extends AppCompatActivity {
    CheckBox potato, tomato, rice, wheat, corn;
    Button confirmOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_crops);
        addListenerOnButtonClick();
    }


    public void addListenerOnButtonClick(){
        potato = (CheckBox)findViewById(R.id.potatoCheckBox);
        tomato = (CheckBox)findViewById(R.id.tomatoCheckBox);
        rice = (CheckBox)findViewById(R.id.riceCheckBox);
        wheat = (CheckBox)findViewById(R.id.wheatCheckBox);
        corn= (CheckBox)findViewById(R.id.cornCheckBox);

        confirmOrder=(Button)findViewById(R.id.confirmCrops);
        confirmOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startActivity(new Intent(RegisterCrops.this, RegistrationInput.class));
                ArrayList selected = new ArrayList();


                if (potato.isChecked()) {
                    selected.add("Potato");
                }
                if (tomato.isChecked()) {
                    selected.add("Tomato");
                }
                if (rice.isChecked()) {
                    selected.add("Rice");
                }
                if (wheat.isChecked()) {
                    selected.add("Wheat");
                }
                if (corn.isChecked()) {
                    selected.add("Corn");
                }/*
                Intent intent = new Intent(RegisterCrops.this, MyCrops.class);
                intent.putStringArrayListExtra("stock_list", selected);
                startActivity(intent);
                startActivity(intent);
    */
            }

        });



    }
}




