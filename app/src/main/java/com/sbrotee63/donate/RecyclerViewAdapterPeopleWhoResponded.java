package com.sbrotee63.donate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterPeopleWhoResponded extends RecyclerView.Adapter<RecyclerViewAdapterPeopleWhoResponded.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapterResponses";

    private ArrayList<String> responses = new ArrayList<>();
    private ArrayList<String> numbers = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterPeopleWhoResponded(Context context, ArrayList<String> aResponses, ArrayList<String> aNumbers) {
        responses = aResponses;
        numbers = aNumbers;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_people_who_responded, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Log.d(TAG, "onBindViewHolder: called.");
        holder.response.setText(responses.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "onClick: clicked on: " + responses.get(position));

                Toast.makeText(mContext, "Calling " + responses.get(position), Toast.LENGTH_SHORT).show();
                dialContactPhone(numbers.get(position));

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
            response = itemView.findViewById(R.id.peoplewhoresponded_text_responses);

            parentLayout = itemView.findViewById(R.id.parent_layout_people_who_responded);
        }

        public void setText(String s) {
        }
    }
    private void dialContactPhone(final String phoneNumber) {
        mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}

