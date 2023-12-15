package com.ms.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.time.Year;
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

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateChooser();
            }
        });

    }

    private void openDateChooser(){

        Calendar calendar = Calendar.getInstance();
        int yearINIT = calendar.get(Calendar.YEAR);
        int monthINIT = calendar.get(Calendar.MONTH);
        int dayINIT = calendar.get(Calendar.DATE);


        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                dateOfBirth.setText(String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day));
            }
        }, yearINIT, monthINIT, dayINIT);

        dialog.show();

    }

}