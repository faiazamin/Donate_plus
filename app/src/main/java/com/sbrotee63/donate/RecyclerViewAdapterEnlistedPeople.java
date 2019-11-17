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

public class RecyclerViewAdapterEnlistedPeople extends RecyclerView.Adapter<RecyclerViewAdapterEnlistedPeople.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapterResponses";

    private ArrayList<String> responses = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterEnlistedPeople(Context context, ArrayList<String> aResponses) {
        responses = aResponses;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_enlisted_people, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Log.d(TAG, "onBindViewHolder: called.");


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "onClick: clicked on: " + responses.get(position));

                Toast.makeText(mContext, responses.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, PostDescription.class);
                intent.putExtra("notification", responses.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    private void setText(String s) {
    }

    @Override
    public int getItemCount() {
        return responses.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView response;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            response = itemView.findViewById(R.id.enlistedpeople_text_responses);

            parentLayout = itemView.findViewById(R.id.parent_layout_enlisted_people);
        }

        public void setText(String s) {
        }
    }
}

