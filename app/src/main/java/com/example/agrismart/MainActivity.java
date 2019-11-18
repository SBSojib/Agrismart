package com.example.agrismart;

import android.content.Intent;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public static int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button next = (Button) findViewById(R.id.goToSignIn);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.opening_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));


        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL, 0, 0);

        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentuser="";
                try {
                    currentuser = currentuser + FirebaseAuth.getInstance().getCurrentUser().getUid();
                }
                catch (Exception e){

                }
                if(!currentuser.equals("")){
                    startActivity(new Intent(MainActivity.this, Menu.class));

                    }
                else{
                    startActivity(new Intent(MainActivity.this, SignIn.class));

                }


            }
        });
        Button next1 = (Button) findViewById(R.id.goToSignUp);
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
                finish();

            }
        });


    }

}