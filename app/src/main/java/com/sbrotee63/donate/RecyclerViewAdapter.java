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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> bloodGroups = new ArrayList<>();
    private ArrayList<String> locations = new ArrayList<>();
    private ArrayList<String> statuses = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> aBloodGroups, ArrayList<String> aLocations, ArrayList<String> aStatuses ) {
        bloodGroups = aBloodGroups;
        locations = aLocations;
        statuses = aStatuses;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_newsfeed, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.bloodGroup.setText(bloodGroups.get(position));
        holder.location.setText(locations.get(position));
        holder.status.setText(statuses.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + bloodGroups.get(position));

                Toast.makeText(mContext, bloodGroups.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, PostDescription.class);
                intent.putExtra("blood_group", bloodGroups.get(position));
                intent.putExtra("location", locations.get(position));
                intent.putExtra("status", statuses.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    private void setText(String s) {
    }

    @Override
    public int getItemCount() {
        return bloodGroups.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView bloodGroup;
        TextView location;
        TextView status;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            bloodGroup = itemView.findViewById(R.id.feed_text_blood);
            location = itemView.findViewById(R.id.feed_text_location);
            status = itemView.findViewById(R.id.feed_text_status);

            parentLayout = itemView.findViewById(R.id.parent_layout);
        }

        public void setText(String s) {
        }
    }
}
