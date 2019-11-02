package com.example.agrismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MarketPrice extends AppCompatActivity {

    PDFView marketPricePdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_description);
        CropPdf c = new CropPdf("Potato","https://firebasestorage.googleapis.com/v0/b/agrismart-cf8ee.appspot.com/o/potato_pest.pdf?alt=media&token=5c3b5cdf-bc6c-4249-88c4-464b407dc290");
        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Pesticide").child("Potato").setValue(c);
    }

}