package com.example.agrismart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteCrop extends AppCompatActivity {

    ListView listView;
    List<Crop> list;
    ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    MyAdapter myAdapter;

    public static String selectedCropName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_crop);

        listView = (ListView) findViewById(R.id.list3);

        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Retreiving...");
        progressDialog.show();
        Button btn =(Button)findViewById(R.id.confirmDeleteCrop);

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

                    myAdapter = new MyAdapter(DeleteCrop.this,R.layout.items,list);
                    listView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Crop c=list.get(position);
                selectedCropName = c.getName() + " " + c.getDateDay()+ " " + c.getDateMonth()+ " " + c.getDateYear();
                final DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().getRoot().child("crops").child(currentuser).child(selectedCropName);
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteCrop.this);
                builder.setTitle("Are you you want to delete this?");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("This change is permanent")
                        .setCancelable(false)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dbNode.setValue(null);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();



            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeleteCrop.this,MyCrops.class);
                startActivity(intent);
                finish();
            }
        });
    }
}