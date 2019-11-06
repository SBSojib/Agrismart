package com.example.agrismart;



import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private EditText emailTV, passwordTV;
    private Button loginBtn,frgtpass;


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });
        frgtpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo();
            }
        });
    }
    private void goTo(){
        Intent i = new Intent(SignIn.this,ResetPass.class);
        startActivity(i);
    }

    private void loginUserAccount() {


        final String email, password;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();

                            if (password.equals("agri300")) {

                                Intent intent = new Intent(SignIn.this, AdminPage.class);

                                startActivity(intent);
                                finish();
                            }
                            else{
                                Intent intent = new Intent(SignIn.this, Menu.class);

                                startActivity(intent);
                                finish();
                            }

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();

                        }
                    }
                });


    }

    private void initializeUI() {
        emailTV = findViewById(R.id.usernameText);
        passwordTV = findViewById(R.id.passText);

        loginBtn = findViewById(R.id.signInConfirmButton);
        frgtpass=findViewById(R.id.forgetpass);

    }

}