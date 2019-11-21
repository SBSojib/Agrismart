package com.example.agrismart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Fertilizing extends Activity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_description);

        final TextView myTextView = (TextView)findViewById(R.id.mytextview);
        final TextView view = (TextView)findViewById(R.id.plantDescrpText);
        Intent i =getIntent();
        final String name = i.getStringExtra("crop");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Fertilizer");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    CropPdf c = snap.getValue(CropPdf.class);
                    if(c.getName().equals(name)){
                        String text = c.getData();
                        String build="";
                        for(int i=0;i<text.length();i++){
                            char et=text.charAt(i);
                            if(et=='#'){
                                build=build+System.lineSeparator()+System.lineSeparator();
                                i++;
                            }
                            else{
                                build=build+et;
                            }
                        }
                        view.setText(name + " " +"Ferilizer");
                        myTextView.setText(build);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


    }

}