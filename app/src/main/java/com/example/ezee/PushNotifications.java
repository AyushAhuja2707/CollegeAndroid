package com.example.ezee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PushNotifications extends AppCompatActivity {

    EditText vtitle;
    EditText vmsg;
    Button push;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_notifications);

        vtitle = findViewById(R.id.atitle);
        vmsg = findViewById(R.id.amsg);
        push = findViewById(R.id.apush);

        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = vtitle.getText().toString();
                String msg = vmsg.getText().toString();

                if(!title.isEmpty() && !msg.isEmpty()){
                    FCMSend.pushNotification(PushNotifications.this, "/topics/students", title, msg);
                }
                else{
                    Toast.makeText(PushNotifications.this, "All inputs required", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}