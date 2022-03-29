package com.example.ezee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.os.Bundle;
import java.util.Collections;
import java.util.List;

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
            Collections.reverse(notis);
            NotiHist.addItemDecoration(new DividerItemDecoration(NotiHist.getContext(), DividerItemDecoration.VERTICAL));
            NotiHist.setAdapter(new NotiHistAdapter(notis));
        }
    }
}