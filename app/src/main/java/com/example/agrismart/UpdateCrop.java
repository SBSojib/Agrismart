package com.example.agrismart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateCrop extends AppCompatActivity {

    ListView listView;
    List<Crop> list;
    ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    MyAdapter myAdapter;

    public static String selectedCropName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_crop);

        listView = (ListView) findViewById(R.id.list4);

        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Retreiving...");
        progressDialog.show();

        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("crops").child(currentuser);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Crop c = snap.getValue(Crop.class);
                    list.add(c);

                    myAdapter = new MyAdapter(UpdateCrop.this,R.layout.items,list);
                    listView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UpdateCrop.this,UpdateCrop2.class);
                Crop c=list.get(position);
                selectedCropName = c.getName() + " " + c.getDateDay()+ " " + c.getDateMonth()+ " " + c.getDateYear();
                Log.e("sssssssssssss",selectedCropName);
                intent.putExtra("cropdets",selectedCropName);
                intent.putExtra("name",c.getName());
                intent.putExtra("day",c.getDateDay());
                intent.putExtra("mon",c.getDateMonth());
                intent.putExtra("yr",c.getDateYear());
                intent.putExtra("qty",c.getQuantity());
                intent.putExtra("unit",c.getQuantityUnit());

                startActivity(intent);


            }
        });
    }
}