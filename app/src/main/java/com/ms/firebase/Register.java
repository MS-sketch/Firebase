package com.ms.firebase;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class Register extends AppCompatActivity {


    // Variables Declared
    TextInputEditText editTextEmail, editTextPassword, editTextFirstName, editTextLastName,
            editTextDOB, editTextConfirmPassword;
    Button buttonReg, buttonNext_1, buttonNext_2, buttonBack_2, buttonBack_3;
    TextView textViewLogin;
    ProgressBar progressBar;

    CardView cvStep_1, cvStep_2, cvStep_3;

    // User Details
    String uid;
    String firstName, lastName, DOB, email, password, chkPassword;


    // Firebase Declaration
    FirebaseAuth mAuth;

    FirebaseDatabase db;
    DatabaseReference reference;


    // MAIN ACTIVITY
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
        setContentView(R.layout.activity_register);


        // Firebase

        mAuth = FirebaseAuth.getInstance();


        // Init Of Variables

        // Text Fields

        // 1st Card
        editTextFirstName = findViewById(R.id.firstName);
        editTextLastName = findViewById(R.id.lastName);

        // 2nd Card
        editTextDOB = findViewById(R.id.DOB);

        // Disable Focus
        editTextDOB.setFocusable(false);

        // 3rd Card
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirmPassword);


        // Progress Bar
        // 3rd Card
        progressBar = findViewById(R.id.progressBar);

        // Text View
        // 3rd Card
        textViewLogin = findViewById(R.id.loginNow);

        // Button
        // 1st Card
        buttonNext_1 = findViewById(R.id.next_step1);

        // 2nd Card
        buttonNext_2 = findViewById(R.id.next_step2);
        buttonBack_2 = findViewById(R.id.back_step2);

        // 3rd Card
        buttonReg = findViewById(R.id.btn_register);
        buttonBack_3 = findViewById(R.id.back_step3);


        // Card View
        // 1st Card
        cvStep_1 = findViewById(R.id.card_name);

        // Second Card
        cvStep_2 = findViewById(R.id.card_other);

        // 3rd Card
        cvStep_3 = findViewById(R.id.card_view);


        // 1st Card Functions.

        // Make the 1st card Visible.
        cvStep_1.setVisibility(View.VISIBLE);

        // Actions to be executed when clicked on "Login Now" Text View.
        textViewLogin.setOnClickListener(view -> openLoginWindow());


        // Function when clicked on "Next"
        buttonNext_1.setOnClickListener(v -> {

            // Get the values of the items.


            firstName = String.valueOf(editTextFirstName.getText());
            lastName = String.valueOf(editTextLastName.getText());

            if (TextUtils.isEmpty(firstName)){
                makeToastSmall("Please Enter First Name");
                return;
            }

            if (TextUtils.isEmpty(lastName)){
                buttonReg.setVisibility(View.VISIBLE);
                makeToastSmall("Please Enter Last");
                return;
            }

            int delay = 0;

            new Handler().postDelayed(() -> {
                AutoTransition autoTransition = new AutoTransition();
                TransitionManager.beginDelayedTransition(findViewById(R.id.cardViewContainer), autoTransition);

                if (cvStep_1.getVisibility() == View.VISIBLE) {
                    cvStep_1.setVisibility(View.INVISIBLE);
                    cvStep_2.setVisibility(View.VISIBLE);
                } else {
                    cvStep_1.setVisibility(View.VISIBLE);
                    cvStep_2.setVisibility(View.INVISIBLE);
                }

            }, delay);


        });



        // 2nd Card

        // Function when clicked DOB editText

        editTextDOB.setOnClickListener(v -> openDateChooser());

        // Function when clicked on "Back"

        buttonBack_2.setOnClickListener(v -> {
            AutoTransition autoTransition = new AutoTransition();
            TransitionManager.beginDelayedTransition(findViewById(R.id.cardViewContainer), autoTransition);

            if (cvStep_2.getVisibility() == View.VISIBLE) {
                cvStep_2.setVisibility(View.INVISIBLE);
                cvStep_3.setVisibility(View.INVISIBLE);
                cvStep_1.setVisibility(View.VISIBLE);
            } else {
                cvStep_2.setVisibility(View.VISIBLE);
                cvStep_1.setVisibility(View.INVISIBLE);
                cvStep_3.setVisibility(View.INVISIBLE);
            }
        });


        // Function when clicked on "Next"

        buttonNext_2.setOnClickListener(v -> {

            DOB = String.valueOf(editTextDOB.getText());

            if (TextUtils.isEmpty(DOB)){
                makeToastSmall("Please Select DOB");
                return;
            }

            AutoTransition autoTransition = new AutoTransition();
            TransitionManager.beginDelayedTransition(findViewById(R.id.cardViewContainer), autoTransition);

            if (cvStep_2.getVisibility() == View.VISIBLE) {
                cvStep_2.setVisibility(View.INVISIBLE);
                cvStep_3.setVisibility(View.VISIBLE);
            } else {
                cvStep_2.setVisibility(View.VISIBLE);
                cvStep_3.setVisibility(View.INVISIBLE);
            }

        });



        // 3rd Card

        // Actions to be executed when clicked on "Back".

        buttonBack_3.setOnClickListener(v -> {
            AutoTransition autoTransition = new AutoTransition();
            TransitionManager.beginDelayedTransition(findViewById(R.id.cardViewContainer), autoTransition);

            if (cvStep_3.getVisibility() == View.VISIBLE) {
                cvStep_3.setVisibility(View.INVISIBLE);
                cvStep_2.setVisibility(View.VISIBLE);
            } else {
                cvStep_3.setVisibility(View.VISIBLE);
                cvStep_2.setVisibility(View.INVISIBLE);
            }
        });



        // Actions to be executed when clicked on "Register" Button.

        buttonReg.setOnClickListener(view -> {
            // Change Visibility
            toggleButtons(1, 1, 0);

            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());
            chkPassword = String.valueOf(editTextConfirmPassword.getText());


            if (TextUtils.isEmpty(email)){
                makeToastSmall("Please Enter Email");
                toggleButtons(0, 0, 1);
                return;
            }

            if (TextUtils.isEmpty(chkPassword)){
                makeToastSmall("Please Confirm Password");
                toggleButtons(0, 0, 1);
                return;
            }

            if (TextUtils.isEmpty(password)){
                makeToastSmall("Please Enter Password");
                toggleButtons(0, 0, 1);
                return;
            }

            if (!(password).equals(chkPassword)){
                makeToastSmall("Passwords Don't Match!");
                toggleButtons(0, 0, 1);
                return;
            }

            // Get the Password Strength
            int passStrength = passwordStrength(password);

            if (passStrength == -1){
                makeToastSmall("Password Is Too Weak!");
                toggleButtons(0, 0, 1);

                // Empty the edit text.
                emptyEditText(1);
                return;
            }



            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {

                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {

                            // Save User Data
                            // Find UID
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            if (currentUser != null) {
                                uid = currentUser.getUid();
                            }

                            // Save Data
                            // saveUserData(firstName, lastName, DOB, email);


                            Users users = new Users(firstName, lastName, DOB, email);
                            db = FirebaseDatabase.getInstance();
                            reference = db.getReference("Users");
                            reference.child(uid).setValue(users).addOnCompleteListener(task1 -> {

                                makeToastSmall("Account Created!");
                                // Opening the Login Page
                                openLoginWindow();

                            });


                        }

                        else {
                            // If sign in fails, display a message to the user.
                            makeToastSmall("An Error Occurred.");
                            toggleButtons(0, 0, 1);

                            // Empty the edit text.
                            emptyEditText(1);

                        }
                    });

        });

    }

    // Other Functions

    // Hide & UnHide Buttons
    private void toggleButtons(int btn_Reg, int btn_Back3, int progBar){
        // 1 = toggle OFF && 0 = toggle ON

        // Btn Reg
        if (btn_Reg == 1){
            buttonReg.setVisibility(View.GONE);
        }
        if (btn_Reg == 0){
            buttonReg.setVisibility(View.VISIBLE);
        }

        // Btn Back 3
        if (btn_Back3 == 1){
            buttonBack_3.setVisibility(View.GONE);
        }
        if (btn_Back3 == 0){
            buttonBack_3.setVisibility(View.VISIBLE);
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
        // 1 = Password & Confirm Password
        if (argument == 1){
            editTextConfirmPassword.setText("");
            editTextPassword.setText("");
        }
    }

    // Make Toast Message (Short)
    private void makeToastSmall(String message){
        Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
    }

    // Open Date Picker

    private void openDateChooser(){

        Calendar calendar = Calendar.getInstance();
        int yearINIT = calendar.get(Calendar.YEAR);
        int monthINIT = calendar.get(Calendar.MONTH);
        int dayINIT = calendar.get(Calendar.DATE);


        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, day) -> editTextDOB.setText(day + "/" + month + "/" + year), yearINIT, monthINIT, dayINIT);

        dialog.show();

    }

    // Password Strength Check
    private int passwordStrength(String password){
        String inputPassword = password;

        int n = inputPassword.length();

        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean specialChar = false;
        String normalChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 ";

        for (int i = 0; i < n; i++) {
            if (Character.isLowerCase(inputPassword.charAt(i))) {
                hasLower = true;
            }
            if (Character.isUpperCase(inputPassword.charAt(i))) {
                hasUpper = true;
            }
            if (Character.isDigit(inputPassword.charAt(i))) {
                hasDigit = true;
            }
            if (normalChars.indexOf(inputPassword.charAt(i)) == -1) {
                specialChar = true;
            }
        }

        // Strength of password
        if (hasLower && hasUpper && hasDigit && specialChar && n >= 12) {
            return 1;
        } else if ((hasLower && hasUpper && n >= 9) || (specialChar && (hasUpper || hasLower || hasDigit) && n >= 9) ||
                ((hasLower || hasDigit || hasUpper || specialChar) && n >= 14)) {
            return 0;
        } else {
            return -1;
        }
    }


    // Opening Activities

    private void openLoginWindow(){
        Intent loginView = new Intent(getApplicationContext(), Login.class);
        startActivity(loginView);
        finish();
    }

    private void openMainActivity(){
        Intent MainView = new Intent(getApplicationContext(), bottomNav.class);
        startActivity(MainView);
        finish();
    }

}