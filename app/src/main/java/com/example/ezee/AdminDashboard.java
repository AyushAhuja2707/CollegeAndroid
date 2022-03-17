package com.example.ezee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class AdminDashboard extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore fst;
    MaterialCardView lgt;
    MaterialCardView pushNoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        mAuth = FirebaseAuth.getInstance();
        fst = FirebaseFirestore.getInstance();
        lgt = findViewById(R.id.Lgt);
        pushNoti = findViewById(R.id.pushNoti);

        lgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("admins");
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(AdminDashboard.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminDashboard.this, MainActivity.class));
                finish();
            }
        });

        pushNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboard.this, PushNotifications.class));
            }
        });

    }
}