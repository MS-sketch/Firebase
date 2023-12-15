package com.ms.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    // Variables Declared
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;
    TextView textViewRegister;
    ProgressBar progressBar;
    // Firebase Declaration
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent MainView = new Intent(getApplicationContext(), bottomNav.class);
            startActivity(MainView);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // Init Of Variables
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);
        textViewRegister = findViewById(R.id.registerNow);

        // Actions to be executed when clicked on "Login Now" Text View.

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerView = new Intent(getApplicationContext(), Register.class);
                startActivity(registerView);
                finish();
            }
        });

        // Actions to be executed when clicked on "Login" Button.

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                buttonLogin.setVisibility(View.GONE);

                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    buttonLogin.setVisibility(View.VISIBLE);
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    buttonLogin.setVisibility(View.VISIBLE);
                    Toast.makeText(Login.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    buttonLogin.setVisibility(View.VISIBLE);
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Signed In",
                                            Toast.LENGTH_SHORT).show();

                                    // TODO: Change to bottom_nav

                                    Intent MainView = new Intent(getApplicationContext(), requiredUserDetails.class);
                                    startActivity(MainView);
                                    finish();
                                }

                                else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "Invalid Credentials!",
                                            Toast.LENGTH_SHORT).show();
                                    buttonLogin.setVisibility(View.VISIBLE);

                                }
                            }
                        });

            }
        });

    }
}