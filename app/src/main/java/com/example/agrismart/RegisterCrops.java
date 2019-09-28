package com.example.agrismart;



import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterCrops extends AppCompatActivity {
    ListView listview;
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_crops);
        intent = new Intent(this,RegistrationInput.class);
        listview = (ListView) findViewById(R.id.list1);
        String[] names = getResources().getStringArray(R.array.crops);
        listview = (ListView) findViewById(R.id.list1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(listClick);
    }
    private AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView Parent, View v,int position,long id){
            String itm=(String)listview.getItemAtPosition(position);
            int k = position;
            intent.putExtra("pos",itm);


            startActivity(intent);
        }
    };
}