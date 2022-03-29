package com.example.ezee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationHistory extends AppCompatActivity {

    RecyclerView NotiHist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_history);

        NotiHist = (RecyclerView) findViewById(R.id.NotiHistRclV);
        NotiHist.setLayoutManager(new LinearLayoutManager(this));

        new dbThread().start();
    }

    class dbThread extends Thread{
        @Override
        public void run() {
            super.run();

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "notifications").build();
            NotiDAO notiDao = db.userDao();
            List<NotiClass> notis =  notiDao.getallNoti();
            NotiHist.setAdapter(new NotiHistAdapter(notis));
        }
    }
}