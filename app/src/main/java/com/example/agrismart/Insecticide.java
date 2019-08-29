package com.example.agrismart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Insecticide extends AppCompatActivity {

    PDFView insecticidePdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insecticide);

        insecticidePdf = (PDFView) findViewById(R.id.InsecticidePdf);
        insecticidePdf.fromAsset("Insecticide.pdf").load();
    }
}
