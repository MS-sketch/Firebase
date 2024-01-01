package com.ms.firebase;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    String uid;


    FirebaseDatabase db;
    DatabaseReference reference;

    RecyclerView recyclerView;
    List<TaskDataClass> dataList;
    TaskAdapter adapter;
    TaskDataClass androidData;
    SearchView searchView;
    FloatingActionButton newTask;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Assign the UID of the current User.

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            uid = currentUser.getUid();
        }

        // Init Of Variable

        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.search);
        newTask = view.findViewById(R.id.newTask);

        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        newTask.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), newTaskToDo.class);
            startActivity(intent);
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0){
                    newTask.hide();
                }
                else{
                    newTask.show();
                }

            }
        });


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();


        // Add Data To List
        androidData = new TaskDataClass("History Homework", "19/12/2023",
                "20/12/2023", R.drawable.study_red, "Due In 1 Day");

        dataList.add(androidData);

        androidData = new TaskDataClass("History Proj", "19/12/2023",
                "20/12/2023", R.drawable.other_green, "Due In 1 Day");

        dataList.add(androidData);

        androidData = new TaskDataClass("Play", "19/12/2023",
                "20/12/2023", R.drawable.other_green, "Due In 1 Day");

        dataList.add(androidData);

        androidData = new TaskDataClass("History Homework", "19/12/2023",
                "20/12/2023", R.drawable.study_red, "Due In 1 Day");

        dataList.add(androidData);

        androidData = new TaskDataClass("History Proj", "19/12/2023",
                "20/12/2023", R.drawable.other_green, "Due In 1 Day");

        dataList.add(androidData);

        androidData = new TaskDataClass("Play", "19/12/2023",
                "20/12/2023", R.drawable.other_green, "Due In 1 Day");

        dataList.add(androidData);

        androidData = new TaskDataClass("History Homework", "19/12/2023",
                "20/12/2023", R.drawable.study_red, "Due In 1 Day");

        dataList.add(androidData);

        androidData = new TaskDataClass("History Proj", "19/12/2023",
                "20/12/2023", R.drawable.other_green, "Due In 1 Day");

        dataList.add(androidData);

        androidData = new TaskDataClass("Play", "19/12/2023",
                "20/12/2023", R.drawable.other_green, "Due In 1 Day");

        dataList.add(androidData);



        adapter = new TaskAdapter(getContext(), dataList);

        recyclerView.setAdapter(adapter);

        return view;

    }


    // Other Functions
    // Search

    private void searchList(String text){
        List<TaskDataClass> dataSearchList = new ArrayList<>();
        for (TaskDataClass data : dataList){
            if (data.getTaskTitle().toLowerCase().contains(text.toLowerCase())){
                dataSearchList.add(data);
            }
        }
        if (dataSearchList.isEmpty()){
            makeToastSmall("Not Found!");
        }
        else {
            adapter.setSearchList(dataSearchList);
        }
    }



    // Read Data From Firebase Realtime DB.

    private void readData(String id){

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                if (task.getResult().exists()){
                    DataSnapshot dataSnapshot = task.getResult();
                    String firstName = String.valueOf(dataSnapshot.child("firstName").getValue());
                    String lastName = String.valueOf(dataSnapshot.child("lastName").getValue());
                    String DOB = String.valueOf(dataSnapshot.child("dob").getValue());
                    String email = String.valueOf(dataSnapshot.child("email").getValue());


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


    private void makeToastSmall(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


}