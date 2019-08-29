package com.example.agrismart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class NewTechnology extends AppCompatActivity {

    PDFView newTechnologyPdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_technology);

        newTechnologyPdf = (PDFView) findViewById(R.id.NewTechnologyPdf);
        newTechnologyPdf.fromAsset("NewTechnology.pdf").load();
    }
}
