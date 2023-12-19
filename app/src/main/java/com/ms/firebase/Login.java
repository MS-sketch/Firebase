package com.ms.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
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
            openMainActivity();
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

        textViewRegister.setOnClickListener(view -> openRegister());

        // Actions to be executed when clicked on "Login" Button.

        buttonLogin.setOnClickListener(view -> {
            toggleButtons(1,0);

            String email, password;
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());

            if (TextUtils.isEmpty(email)){
                makeToastSmall("Please Enter Email");
                toggleButtons(0,1);
                return;
            }

            if (TextUtils.isEmpty(password)){
                makeToastSmall("Please Enter Password");
                toggleButtons(0,1);
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {

                        toggleButtons(1,1);

                        if (task.isSuccessful()) {
                            makeToastSmall("Signed In!");
                            openMainActivity();
                        }

                        else {
                            // If sign in fails, display a message to the user.
                            makeToastSmall("Invalid Credentials!");

                            // Empty Text
                            toggleButtons(0,1);

                            // Empty the edit text.
                            emptyEditText(1);

                        }
                    });

        });

    }

    // Other Functions

    // Make Toast Message (Short)
    private void makeToastSmall(String message){
        Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
    }

    // Toggle Button
    private void toggleButtons(int btn_Login, int progBar){
        // 1 = toggle OFF && 0 = toggle ON

        // Btn Reg
        if (btn_Login == 1){
            buttonLogin.setVisibility(View.GONE);
        }
        if (btn_Login == 0){
            buttonLogin.setVisibility(View.VISIBLE);
        }


        // Progress Bar
        if (progBar == 1){
            progressBar.setVisibility(View.GONE);
        }
        if (progBar == 0){
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    // Empty Edit Text
    private void emptyEditText(int argument){
        // 1 = Password & Email Password
        if (argument == 1){
            editTextEmail.setText("");
            editTextPassword.setText("");
        }
    }


    // Opening Activities.
    private void openMainActivity(){
        Intent MainView = new Intent(getApplicationContext(), bottomNav.class);
        startActivity(MainView);
        finish();
    }

    private void openRegister(){
        Intent registerView = new Intent(getApplicationContext(), Register.class);
        startActivity(registerView);
        finish();
    }
}