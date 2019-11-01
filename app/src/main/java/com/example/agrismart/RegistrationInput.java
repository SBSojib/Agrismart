package com.example.agrismart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistrationInput extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    String selectedDate = null;
    String month = null;
    String day = null;
    String yearD = null;

    EditText edittext;

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_input);

        edittext = (EditText) findViewById(R.id.Birthday);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                edittext.setText(sdf.format(myCalendar.getTime()));
                selectedDate = String.valueOf(sdf.format(myCalendar.getTime()));

                String[] words=selectedDate.split("/");
                month = words[0];
                day = words[1];
                yearD = words[2];

                Log.e("Day",day);
                Log.e("Month",month);
                Log.e("Year",yearD);

                Log.e("Date",selectedDate);
                //updateLabel();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegistrationInput.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);

        final String[] item1 = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        final String[] item2 = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};
        final String[] item3 = new String[]{"2019", "2020", "2021", "2022"};
        final String[] item4 = new String[]{"Acre", "Shatak", "Bigha", "Kata"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, item1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, item2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, item3);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, item4);

        spinner4.setAdapter(adapter4);

        Intent intent = getIntent();
        final String nm = intent.getStringExtra("crop");
//        button.setText(name);
        //Log.e("Crop",nm);
        Intent i = new Intent(this,MyCrops.class);

        button = (Button) findViewById(R.id.buttonOfConfirmingRegistration);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                int k4=spinner4.getSelectedItemPosition();
                EditText edit = (EditText)findViewById(R.id.qtyText);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                String qty = edit.getText().toString();
                DatabaseReference mDatabase;
// ...
                mDatabase = FirebaseDatabase.getInstance().getReference();
                String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                if(nm ==null || day == null || month == null || yearD == null) {
                    Toast.makeText(getApplicationContext(), "Please select Date", Toast.LENGTH_LONG).show();
                    return;
                }
                if(qty.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Field Size", Toast.LENGTH_LONG).show();
                    return;
                }

                Crop crop = new Crop(nm,day,month,yearD,qty,item4[k4]);
                String node = nm +" " + day+" " +month + " " + yearD;
                mDatabase.child("crops").child(currentuser).child(node).setValue(crop);
                startActivity(new Intent(RegistrationInput.this, Menu.class));
                finish();


            }
        });
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
        String s = String.valueOf(sdf.format(myCalendar.getTime()));
    }
}
