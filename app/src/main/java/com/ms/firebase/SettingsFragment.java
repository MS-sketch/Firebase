package com.ms.firebase;

import android.content.Intent;
import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SettingsFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null){
            Intent loginIntent = new Intent(getActivity(), Login.class);
            startActivity(loginIntent);
            requireActivity().finish();
        }


        Button logoutButton = view.findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(v -> {
            try {
                // Handle button click in the fragment
                // For example, you can show a toast message

                FirebaseAuth.getInstance().signOut();

                Toast.makeText(requireContext(), "Logged Out!", Toast.LENGTH_SHORT).show();

                Intent loginIntent = new Intent(getActivity(), Login.class);
                startActivity(loginIntent);
                requireActivity().finish();
            }
            catch (Exception error){
                Toast.makeText(requireContext(), "An Error Occurred! Try Again Later!",
                        Toast.LENGTH_SHORT).show();
            }

        });

        return view;
    }
}