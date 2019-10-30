package com.example.agrismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MarketPrice extends AppCompatActivity {

    PDFView marketPricePdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_description);

        TextView myTextView = (TextView)findViewById(R.id.mytextview);
        Intent i =getIntent();
        final String name = i.getStringExtra("crop");

        InputStream inputStream;
        if(name.equals("Potato")){
            inputStream=getResources().openRawResource(R.raw.potato_prc);
        }
        else if(name.equals("Tomato")){
            inputStream=getResources().openRawResource(R.raw.tomato_prc);
        }
        else if(name.equals("Rice")){
            inputStream=getResources().openRawResource(R.raw.rice_prc);
        }
        else if(name.equals("Wheat")){
            inputStream=getResources().openRawResource(R.raw.wheat_prc);
        }
        else{
            inputStream=getResources().openRawResource(R.raw.corn_prc);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        String myText = "";
        int in;
        try {
            in = inputStream.read();
            while (in != -1)
            {
                byteArrayOutputStream.write(in);
                in = inputStream.read();
            }
            inputStream.close();

            myText = byteArrayOutputStream.toString();
        }catch (IOException e) {
            e.printStackTrace();
        }

        myTextView.setText(myText);
    }

}