package com.example.agrismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationInput extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_input);


        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        final Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        final Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);

        final String[] item1 = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        final String[] item2 = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};
        final String[] item3 = new String[]{"2019", "2020", "2021", "2022"};
        final String[] item4 = new String[]{"Acre", "Shatak", "Bigha", "Kata"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, item1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, item2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, item3);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, item4);

        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);
        spinner4.setAdapter(adapter4);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("crname");
        Intent i = new Intent(this,MyCrops.class);

        button = (Button) findViewById(R.id.buttonOfConfirmingRegistration);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int k1=spinner1.getSelectedItemPosition();
                int k2=spinner1.getSelectedItemPosition();
                int k3=spinner1.getSelectedItemPosition();
                int k4=spinner1.getSelectedItemPosition();
                EditText edit = (EditText)findViewById(R.id.qtyText);

                String qty = edit.getText().toString();
                DatabaseReference mDatabase;
// ...
                mDatabase = FirebaseDatabase.getInstance().getReference();
                String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Crop crop = new Crop(name,item1[k1],item2[k2],item3[k3],qty,item4[k4]);
                mDatabase.child("crops").child(currentuser).child(name).setValue(crop);
                ///startActivity(new Intent(RegistrationInput.this, Extra.class));

            }
        });
    }
}
