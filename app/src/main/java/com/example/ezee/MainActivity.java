package com.example.ezee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText uname, password;
    Button loginbtn;
    TextView regui;
    TextView forgotpass;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uname = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);
        regui = findViewById(R.id.register);
        forgotpass = findViewById(R.id.forgotpass);

        mAuth = FirebaseAuth.getInstance();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = uname.getText().toString();
                String pwd = password.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd))
                    Toast.makeText(MainActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                else if(pwd.length()<6) Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                else loginuser(email, pwd);
            }
        });

        regui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(reg);
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fp = new Intent(MainActivity.this,ForgotPassActivity.class);
                startActivity(fp);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }
    }

    private void loginuser(String email, String pwd) {
        mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(mAuth.getCurrentUser().isEmailVerified()){
                        Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finishAffinity();
                    }
                    else{
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(MainActivity.this, "Email not verified", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

// TODO: 1032190111@tcetmumbai.in