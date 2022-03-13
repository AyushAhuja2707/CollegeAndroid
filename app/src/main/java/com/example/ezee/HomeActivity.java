package com.example.ezee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        LinearLayout header = (LinearLayout) navigationView.getHeaderView(0);
        toolbar = findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        navigationView.setItemTextAppearance(R.style.Menu_text_style);
        navigationView.setCheckedItem(R.id.nav_timetable);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        DatabaseReference data =  db.child("Users").child(mAuth.getCurrentUser().getUid());
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = "";
                for(DataSnapshot ss : snapshot.getChildren()){
                    if(ss.getKey().equals("fname") || ss.getKey().equals("lname")) name = name + ss.getValue().toString() + " ";
                }
                TextView user = header.findViewById(R.id.head_user);
                user.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Error Fetching Database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menu_item) {
        switch (menu_item.getItemId()) {
            case R.id.nav_timetable:
                Toast.makeText(this, "Time-Table", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_practice:
                Toast.makeText(this, "Practice", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_syllabus:
                Toast.makeText(this, "Syllabus", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_notification:
                Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_feedback:
                Toast.makeText(this, "Feedback", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}