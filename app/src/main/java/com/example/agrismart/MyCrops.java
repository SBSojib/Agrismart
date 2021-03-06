package com.example.agrismart;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyCrops extends AppCompatActivity {
    ListView listView;
    List<Crop> list;
    ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    MyAdapter myAdapter;

    public static String selectedCropName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_crops_list);

        listView = (ListView) findViewById(R.id.list2);

        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Retreiving...");
        progressDialog.show();
        Button btn1=(Button)findViewById(R.id.updateCrop);
        Button btn2=(Button)findViewById(R.id.deleteCrop);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i= new Intent(MyCrops.this,DeleteCrop.class);
                startActivity(i);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i= new Intent(MyCrops.this,UpdateCrop.class);
                startActivity(i);
            }
        });
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("crops").child(currentuser);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Crop c = snap.getValue(Crop.class);
                    list.add(c);

                    myAdapter = new MyAdapter(MyCrops.this,R.layout.items,list);
                    listView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyCrops.this,Extra.class);
                Crop c=list.get(position);
                selectedCropName = c.getName();
                Log.e("Name",selectedCropName);
                System.out.println(listView.getItemAtPosition(position).toString());
                intent.putExtra("crname",c.getName());
                intent.putExtra("plantingDay",c.getDateDay());
                intent.putExtra("plantingMonth",c.getDateMonth());
                intent.putExtra("plantingYear",c.getDateYear());
                startActivity(intent);
            }
        });
    }
}
