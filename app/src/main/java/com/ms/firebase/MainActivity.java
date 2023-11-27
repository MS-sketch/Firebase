package com.ms.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button logout;
    TextView textViewUserDetails;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.logout);
        textViewUserDetails = findViewById(R.id.userDetails);

        user = auth.getCurrentUser();

        if (user == null){
            Intent loginView = new Intent(getApplicationContext(), Login.class);
            startActivity(loginView);
            finish();
        }
        else {
            textViewUserDetails.setText(user.getEmail());
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent loginView = new Intent(getApplicationContext(), Login.class);
                startActivity(loginView);
                finish();
            }
        });
    }
}