package com.example.agrismart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Fertilizing extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_description);

        TextView myTextView = (TextView)findViewById(R.id.mytextview);
        Intent i =getIntent();
        final String name = i.getStringExtra("crop");

        InputStream inputStream;
        if(name.equals("Potato")){
            inputStream=getResources().openRawResource(R.raw.potato_fert);
        }
        else if(name.equals("Tomato")){
            inputStream=getResources().openRawResource(R.raw.tomato_fert);
        }
        else if(name.equals("Rice")){
            inputStream=getResources().openRawResource(R.raw.rice_fert);
        }
        else if(name.equals("Wheat")){
            inputStream=getResources().openRawResource(R.raw.wheat_fert);
        }
        else{
            inputStream=getResources().openRawResource(R.raw.corn_fert);
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
