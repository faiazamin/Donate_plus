package com.sbrotee63.donate;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterNotifications extends RecyclerView.Adapter<RecyclerViewAdapterNotifications.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapterNoti";

    private ArrayList<String> notifications = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterNotifications(Context context, ArrayList<String> aNotifications) {
        notifications = aNotifications;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notifications, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Log.d(TAG, "onBindViewHolder: called.");

        final String postId = notifications.get(position).substring(2);
        if(notifications.get(position).charAt(0) == '0'){
            // notifications not seen yet
        }
        else if(notifications.get(position).charAt(0) == '1'){
            // notification seen already
        }
        if(notifications.get(position).charAt(1) == '+'){
            // normal post notification
            holder.notification.setText("New request for " + NewsFeed.user.bloodGroup + " has arrived");
        }
        else if(notifications.get(position).charAt(1) == '*'){
            // urgent repost notification
            holder.notification.setText("New request for " + NewsFeed.user.bloodGroup + " has arrived");
        }
        else if(notifications.get(position).charAt(1) == '-'){
            // response notification
            holder.notification.setText("Your post has been responsed");
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + notifications.get(position));

                Toast.makeText(mContext, notifications.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, PostDescription.class);
                intent.putExtra("postId", postId);
                intent.putExtra("notification", notifications.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    private void setText(String s) {
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView notification;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            notification = itemView.findViewById(R.id.notifications_text_notifications);

            parentLayout = itemView.findViewById(R.id.parent_layout);
        }

        public void setText(String s) {
        }
    }
}

