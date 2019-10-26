package com.example.agrismart;

import androidx.appcompat.app.AppCompatActivity;

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

    EditText et1 = null;
    EditText et2 = null;
    EditText et3 = null;
    EditText et4 = null;
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
        et1= (EditText)findViewById(R.id.crpName);
        et2= (EditText)findViewById(R.id.crpLat);
        et3= (EditText)findViewById(R.id.crpLon);
        et4= (EditText)findViewById(R.id.crpMon);


        Button btn = (Button)findViewById(R.id.addcrop);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t1 = et1.getText().toString();

                final int val = Integer.valueOf(et2.getText().toString());
                final int val1 = Integer.valueOf(et2.getText().toString());
                final int val2 = Integer.valueOf(et2.getText().toString());
// ...
                mDatabase = FirebaseDatabase.getInstance().getReference();
                Crop2 c = new Crop2(t1,val,val1,val2);
                String m= c.getName();
                mDatabase.child("SuggestedCrops").child(m).setValue(c);
                Toast.makeText(getApplicationContext(), "Crop Added", Toast.LENGTH_LONG).show();
                et1.getText().clear();
                et2.getText().clear();
                et3.getText().clear();
                et4.getText().clear();
            }
        });
    }
}
