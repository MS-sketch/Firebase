package com.ms.firebase;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    // FIREBASE
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference reference;
    String userID, workType, dateCreated, timeCreated;

    FirebaseFirestore dbFireStore;
    int currentID = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_to_do);

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


            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            timeCreated = sdf.format(calendar.getTime());


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
                RadioButton selectedButton = findViewById(selectedRadioButtonId);
                workType = String.valueOf(selectedButton.getText());

                todoActivityDataStruct info = new todoActivityDataStruct(title, dueDate, dueTime,
                        workType, description, dateCreated, timeCreated);

                // TODO: CONTACT FIREBASE REALTIME DB, AND GET A UNIQUE TASK ID, STARTING WITH 0.
                //  ALSO WRITE BACK TO IT THAT THE NUMBER IS UPDATED EVERY TIME WHEN A NEW TASK IS
                //  CREATED WITH THE LATEST ID. ALSO MAKE A NEW SUB DOC IN THE FIRESTORE TO STORE ENTRIES.


                writeTodo(info);


            }


        });


    }


    private void readCurrentID(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                if (task.getResult().exists()){
                    DataSnapshot dataSnapshot = task.getResult();
                    Long longValue = dataSnapshot.child("currentTaskID").getValue(Long.class);

                    if (longValue != null){
                        int newID = longValue.intValue();
                        newID++;
                        currentID = newID;

                        reference.child("currentTaskID").setValue(currentID)
                                .addOnSuccessListener(unused -> makeToastSmall("Updated ID!"))
                                .addOnFailureListener(e -> makeToastSmall("Task Failed!"));

                        makeToastSmall(String.valueOf(currentID));

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

    private void writeTodo(todoActivityDataStruct info){
        readCurrentID();
        String id = String.valueOf(currentID);
        DocumentReference userRef = dbFireStore.collection("user_data").document(userID);
        userRef.collection(id).add(info).addOnCompleteListener(task -> makeToastSmall("Saved!"));
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

        // IMPLEMENT

    }


    private void makeToastSmall(String message){
        Toast.makeText(newTaskToDo.this, message, Toast.LENGTH_SHORT).show();
    }


}