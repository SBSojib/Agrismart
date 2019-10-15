package com.example.agrismart;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisterCrops extends AppCompatActivity {
    ListView listView;
    List<Crop2> list;
    ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    MyAdapter2 myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_crops);


        listView = (ListView) findViewById(R.id.list1);

        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Retreiving...");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("SuggestedCrops");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Crop2 c = snap.getValue(Crop2.class);
                    list.add(c);

                    myAdapter = new MyAdapter2(RegisterCrops.this,R.layout.items,list);
                    listView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RegisterCrops.this,RegistrationInput.class);
                Crop2 c=list.get(position);
                System.out.println(listView.getItemAtPosition(position).toString());
                intent.putExtra("crname",c.getName());
                startActivity(intent);
            }
        });
    }
}
