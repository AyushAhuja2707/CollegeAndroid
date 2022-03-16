package com.example.ezee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    EditText uname, password;
    Button loginbtn;
    TextView regui;
    TextView forgotpass;
    Loading load;
    static String stradmin = "false";
    Boolean admin;

    FirebaseAuth mAuth;
    FirebaseFirestore fst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uname = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);
        regui = findViewById(R.id.register);
        forgotpass = findViewById(R.id.forgotpass);
        load = new Loading(MainActivity.this);

        mAuth = FirebaseAuth.getInstance();
        fst = FirebaseFirestore.getInstance();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = uname.getText().toString();
                String pwd = password.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd))
                    Toast.makeText(MainActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                else if(pwd.length()<6) Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                else{
                    load.startLoading();
                    loginuser(email, pwd);
                }
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


            DocumentReference data = fst.collection("Users").document(mAuth.getCurrentUser().getUid());
            data.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    admin = documentSnapshot.getBoolean("admin");
                    stradmin = admin+"";
                    Log.i("Ayush",stradmin);

                    if(stradmin.equals("true")){
                        startActivity(new Intent(MainActivity.this, AdminDashboard.class));
                        finishAffinity();
                    }
                    else {
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finishAffinity();
                    }
                }
            });









//            startActivity(new Intent(MainActivity.this, HomeActivity.class));
//            finish();
        }

    }

    private void loginuser(String email, String pwd) {



        mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    if(mAuth.getCurrentUser().isEmailVerified()){

                        load.dismissDialog();

                        DocumentReference data = fst.collection("Users").document(mAuth.getCurrentUser().getUid());
                        data.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                admin = documentSnapshot.getBoolean("admin");
                                stradmin = admin+"";
                                Log.i("Ayush",stradmin);

                                if(stradmin.equals("true")){
                                    startActivity(new Intent(MainActivity.this, AdminDashboard.class));
                                    finishAffinity();
                                }
                                else {
                                    Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                    finishAffinity();
                                }
                            }
                        });


                    }
                    else{
                        FirebaseAuth.getInstance().signOut();
                        load.dismissDialog();
                        Toast.makeText(MainActivity.this, "Email not verified", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                load.dismissDialog();
                Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

// TODO: 1032190111@tcetmumbai.in
// TODO: 1032190095@tcetmumbai.in
// TODO: bhagatchirag2@gmail.com