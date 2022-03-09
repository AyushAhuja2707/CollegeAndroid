package com.example.ezee;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{

    Button admin;
    Button student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        admin = findViewById(R.id.admin);
        student = findViewById(R.id.student);
    }

    public void admin(View view){
        Toast.makeText(this, "Logged in as Admin", Toast.LENGTH_SHORT).show();
    }

    public void student(View view){
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
    }
}