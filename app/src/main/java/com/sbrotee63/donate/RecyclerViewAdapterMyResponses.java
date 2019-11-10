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

public class RecyclerViewAdapterMyResponses extends RecyclerView.Adapter<RecyclerViewAdapterMyResponses.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapterMyRes";

    private ArrayList<String> bloodGroups = new ArrayList<>();
    private ArrayList<String> locations = new ArrayList<>();
    private ArrayList<String> statuses = new ArrayList<>();
    private ArrayList<String> postId = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterMyResponses(Context context, ArrayList<String> aBloodGroups, ArrayList<String> aLocations, ArrayList<String> aStatuses, ArrayList<String> apostId) {
        bloodGroups = aBloodGroups;
        locations = aLocations;
        statuses = aStatuses;
        mContext = context;
        postId = apostId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_my_responses, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Log.d(TAG, "onBindViewHolder: called.");

        holder.bloodGroup.setText(bloodGroups.get(position));
        holder.location.setText(locations.get(position));
        holder.status.setText(statuses.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "onClick: clicked on: " + bloodGroups.get(position));

                Toast.makeText(mContext, bloodGroups.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, PostDescription.class);
                intent.putExtra("postId", postId.get(position));
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
            bloodGroup = itemView.findViewById(R.id.myresponses_text_blood);
            location = itemView.findViewById(R.id.myresponses_text_location);
            status = itemView.findViewById(R.id.myresponses_text_status);

            parentLayout = itemView.findViewById(R.id.parent_layout_myresponses);
        }

        public void setText(String s) {
        }
    }
}

