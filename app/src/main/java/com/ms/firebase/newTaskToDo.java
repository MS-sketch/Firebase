package com.ms.firebase;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class newTaskToDo extends AppCompatActivity {



    // DECLARE VARIABLES
    ImageButton imageButtonBack;
    Button saveBTN, discardBTN;
    EditText titleET, dueDateET, dueTimeET, descriptionET;
    RadioGroup radioGroup;

    RadioButton radioButtonStudyRed, radioButtonOtherGreen, radioButtonAssignmentBlue,
            radioButtonWritingYellow;

    ImageView imageViewColorType;

    ProgressBar progressBar;

    // FIREBASE
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference reference;
    String userID, workType, dateCreated, timeCreated;

    FirebaseFirestore dbFireStore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_to_do);

        FirebaseApp.initializeApp(this);

        db = FirebaseDatabase.getInstance();


        // INIT VARIABLES

        // Image Btn
        imageButtonBack = findViewById(R.id.backToListView);

        // Button
        saveBTN = findViewById(R.id.btn_save);
        discardBTN = findViewById(R.id.btn_discard);

        // EDIT TEXT
        titleET = findViewById(R.id.taskName);
        dueDateET = findViewById(R.id.dueDate);
        dueTimeET = findViewById(R.id.dueTime);
        descriptionET = findViewById(R.id.descriptionBox);

        // RADIO GROUP
        radioGroup = findViewById(R.id.workRadioGroup);

        // RADIO BTN
        radioButtonStudyRed = findViewById(R.id.studyRed);
        radioButtonWritingYellow = findViewById(R.id.writingYellow);
        radioButtonAssignmentBlue = findViewById(R.id.assignmentBlue);
        radioButtonOtherGreen = findViewById(R.id.otherGreen);

        // IMAGE VIEW
        imageViewColorType = findViewById(R.id.workType);

        // Progress Bar
        progressBar = findViewById(R.id.progressBar);



        // INIT ARGS

        // DISABLE FOCUS
        dueDateET.setFocusable(false);
        dueTimeET.setFocusable(false);

        // GET USER ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userID = currentUser.getUid();
        }



        // FUNCTIONS

        // CLICKED ON THE BACK ARROW
        imageButtonBack.setOnClickListener(v -> finish());

        // CLICK DISCARD BTN
        discardBTN.setOnClickListener(v -> finish());

        // Click On Due Date
        dueDateET.setOnClickListener(v -> openDateChooser());

        // Click on due time
        dueTimeET.setOnClickListener(v -> openTimeChooser());


        radioButtonStudyRed.setOnClickListener(v -> imageViewColorType.setImageResource(R.drawable.template_resource_study_red));
        radioButtonAssignmentBlue.setOnClickListener(v -> imageViewColorType.setImageResource(R.drawable.template_resource_assignment_blue));
        radioButtonOtherGreen.setOnClickListener(v -> imageViewColorType.setImageResource(R.drawable.template_resource_other_green));
        radioButtonWritingYellow.setOnClickListener(v -> imageViewColorType.setImageResource(R.drawable.template_resource_writing_yellow));


        // CLICKED ON SAVE BUTTON
        saveBTN.setOnClickListener(v -> {
            String title, dueDate, dueTime, description;

            title = String.valueOf(titleET.getText());
            dueDate = String.valueOf(dueDateET.getText());
            dueTime = String.valueOf(dueTimeET.getText());
            description = String.valueOf(descriptionET.getText());

            Calendar calendar = Calendar.getInstance();
            int yearINIT = calendar.get(Calendar.YEAR);
            int monthINIT = calendar.get(Calendar.MONTH) + 1;
            int dayINIT = calendar.get(Calendar.DATE);

            dateCreated = dayINIT + "/" + monthINIT + "/" + yearINIT;


            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            timeCreated = String.valueOf(sdf.format(calendar.getTime()));


            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

            if (TextUtils.isEmpty(title)){
                makeToastSmall("Enter Title");
            }
            if (TextUtils.isEmpty(dueDate)){
                makeToastSmall("Enter Due Date");
            }
            if (TextUtils.isEmpty(dueTime)){
                makeToastSmall("Enter Due Time");
            }
            if (TextUtils.isEmpty(description)){
                makeToastSmall("Enter Description");
            }
            if (selectedRadioButtonId == -1){
                makeToastSmall("Select a Work Type!");
            }
            else{

                saveBTN.setVisibility(View.GONE);
                discardBTN.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                RadioButton selectedButton = findViewById(selectedRadioButtonId);
                workType = String.valueOf(selectedButton.getText());

                todoActivityDataStruct info = new todoActivityDataStruct();

                info.setTitle(title);
                info.setDateCreated(dateCreated);
                info.setTimeCreated(timeCreated);
                info.setDueDate(dueDate);
                info.setDueTime(dueTime);
                info.setDescription(description);
                info.setWorkType(workType);

                writeTodo(info);


            }

        });

    }



    private void writeTodo(todoActivityDataStruct info) {

        // Update DATA

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                if (task.getResult().exists()){
                    DataSnapshot dataSnapshot = task.getResult();
                    Long longValue = dataSnapshot.child("currentTaskID").getValue(Long.class);

                    if (longValue != null){
                        int newID = longValue.intValue();
                        newID++;
                        int currentID = newID;

                        reference.child("currentTaskID").setValue(currentID)
                                .addOnSuccessListener(unused -> writeData(info, currentID))
                                .addOnFailureListener(e -> makeToastSmall("Task Failed!"));

                    }
                }

                else {
                    makeToastSmall("User Doesn't Exist!");
                }

            }

            else {
                makeToastSmall("Failed to read data");
            }
        });


    }


    private void writeData(todoActivityDataStruct info, int currentID){
        // WRITE DATA

        dbFireStore = FirebaseFirestore.getInstance();

        String id = String.valueOf(currentID);
        DocumentReference userRef = dbFireStore.collection("user_data").document(userID);
        userRef.collection("todo_list").document(id).set(info).addOnCompleteListener(task -> makeToastSmall("Saved!"));

        finish();
    }

    private void openDateChooser(){

        Calendar calendar = Calendar.getInstance();
        int yearINIT = calendar.get(Calendar.YEAR);
        int monthINIT = calendar.get(Calendar.MONTH);
        int dayINIT = calendar.get(Calendar.DATE);


        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, day) ->
                dueDateET.setText(day + "/" + (month + 1) + "/" + year), yearINIT, monthINIT, dayINIT);

        dialog.show();

    }

    private void openTimeChooser(){

        Calendar calendar = Calendar.getInstance();
        int hourOfDayINIT = calendar.get(Calendar.HOUR_OF_DAY);
        int minuteINIT = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(this, (view, hours, minute)
                -> dueTimeET.setText(hours +":"+ minute),
                hourOfDayINIT, minuteINIT, true);

        dialog.show();

    }


    private void makeToastSmall(String message){
        Toast.makeText(newTaskToDo.this, message, Toast.LENGTH_SHORT).show();
    }


}