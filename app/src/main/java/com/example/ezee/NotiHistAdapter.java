package com.example.ezee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotiHistAdapter extends RecyclerView.Adapter<NotiHistAdapter.holder> {

    List<NotiClass> data;
    public NotiHistAdapter(List<NotiClass> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notification_block, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
       holder.title.setText(data.get(position).getTitle());
       holder.msg.setText(data.get(position).getMsg());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class holder extends RecyclerView.ViewHolder {

        TextView title;
        TextView msg;

        public holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.noti_title);
            msg = itemView.findViewById(R.id.noti_msg);

        }
    }
}
