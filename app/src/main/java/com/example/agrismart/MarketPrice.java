package com.example.agrismart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class MarketPrice extends AppCompatActivity {

    PDFView marketPricePdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_price);

        marketPricePdf = (PDFView) findViewById(R.id.MarketPricePdf);
        marketPricePdf.fromAsset("MarketPrice.pdf").load();
    }
}