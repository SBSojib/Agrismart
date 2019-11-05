package com.example.agrismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Progress extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_description);

        final TextView myTextView = (TextView)findViewById(R.id.mytextview);
        final TextView view = (TextView)findViewById(R.id.plantDescrpText);
        Intent i =getIntent();
        final String name = i.getStringExtra("crop");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Progress");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    CropPdf c = snap.getValue(CropPdf.class);
                    if(c.getName().equals(name)){
                        String text = c.getData();
                        view.setText(name + "" +"Progress");
                        myTextView.setText(text);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


    }

}