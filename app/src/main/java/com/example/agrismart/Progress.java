package com.example.agrismart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Progress extends AppCompatActivity {

    PDFView progressPdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        progressPdf = (PDFView) findViewById(R.id.ProgressPdf);
        progressPdf.fromAsset("Progress.pdf").load();
    }
}
