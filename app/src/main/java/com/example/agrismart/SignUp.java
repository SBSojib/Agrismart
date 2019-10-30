package com.example.agrismart;



import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private EditText emailTV, passwordTV;
    private Button loginBtn;


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final Spinner spinner1 = (Spinner) findViewById(R.id.adminDecideSpinner);
        final String[] item1 = new String[]{"Admin","User"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, item1);
        spinner1.setAdapter(adapter1);
        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k1=spinner1.getSelectedItemPosition();
                final String stat=item1[k1];
                loginUserAccount(stat);
            }
        });
    }

    private void loginUserAccount(String stat) {


        String email, password;
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
        if(stat.equals("User") && password.equals("agri300")){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            return;
        }
        if((stat.equals("Admin") && password.equals("agri300")) || (stat.equals("User") && !password.equals("agri300"))) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignUp.this, SignIn.class);

                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }
    }

    private void initializeUI() {
        emailTV = findViewById(R.id.regUsernameText);
        passwordTV = findViewById(R.id.regPassText);

        loginBtn = findViewById(R.id.regConfirmButton);

    }
}