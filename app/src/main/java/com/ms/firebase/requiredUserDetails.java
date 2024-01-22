package com.ms.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class requiredUserDetails extends AppCompatActivity {

    private TextInputEditText dateOfBirth, firstName, LastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_required_user_details);

        // Assign Var to Actual Inputs.

        dateOfBirth = findViewById(R.id.dateOfBirth);

        dateOfBirth.setFocusable(false);

        dateOfBirth.setOnClickListener(v -> openDateChooser());

    }

    private void openDateChooser(){

        Calendar calendar = Calendar.getInstance();
        int yearINIT = calendar.get(Calendar.YEAR);
        int monthINIT = calendar.get(Calendar.MONTH);
        int dayINIT = calendar.get(Calendar.DATE);


        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, day) -> dateOfBirth.setText(year + "/" + month + "/" + day), yearINIT, monthINIT, dayINIT);

        dialog.show();

    }

}