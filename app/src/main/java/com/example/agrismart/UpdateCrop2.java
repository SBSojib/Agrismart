package com.example.agrismart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateCrop2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_crop2);
        final EditText et1=(EditText)findViewById(R.id.Day);
        final EditText et2=(EditText)findViewById(R.id.Month);
        final EditText et3=(EditText)findViewById(R.id.Year);
        final EditText et4=(EditText)findViewById(R.id.qtyTextEdit);
        final Spinner s = (Spinner)findViewById(R.id.spinnerEdit);
        Button btn =(Button)findViewById(R.id.buttonOfConfirmUpdate);
        Intent intent= getIntent();
        final String cropNode=intent.getStringExtra("cropdets");
        final String name=intent.getStringExtra("name");
        String day=intent.getStringExtra("day");
        String mon=intent.getStringExtra("mon");
        String yr=intent.getStringExtra("yr");
        String qty=intent.getStringExtra("qty");
        String unit=intent.getStringExtra("unit");
        final String[] item4 = new String[]{"Acre", "Shatak", "Bigha", "Kata"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, item4);
        s.setAdapter(adapter);
        for(int i=0;i<4;i++){
            if(unit.equals(item4[i])){
                s.setSelection(i);
                break;
            }
        }
        et1.setText(day);
        et2.setText(mon);
        et3.setText(yr);
        et4.setText(qty);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day=et1.getText().toString();
                String mon=et2.getText().toString();
                String year=et3.getText().toString();
                String qty=et4.getText().toString();

                int pos=s.getSelectedItemPosition();
                String unit=item4[pos];
                Crop c= new Crop(name,day,mon,year,qty,unit);
                final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().getRoot().child("crops").child(currentuser).child(cropNode);
                dbNode.setValue(c);
                Intent i=new Intent(UpdateCrop2.this,MyCrops.class);
                startActivity(i);
                finish();
            }
        });



    }
}
