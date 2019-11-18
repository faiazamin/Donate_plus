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

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerViewAdapterEnlistedPeople extends RecyclerView.Adapter<RecyclerViewAdapterEnlistedPeople.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapterResponses";

    private ArrayList<String> responses = new ArrayList<>();
    private ArrayList<String> uid = new ArrayList<>();
    private ArrayList<String> number = new ArrayList<>();
    private ArrayList<String> postIds = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterEnlistedPeople(Context context, ArrayList<String> aResponses, ArrayList<String> uid, ArrayList<String> number, ArrayList<String> postIds) {
        responses = aResponses;
        mContext = context;
        this.uid = uid;
        this.number = number;
        this.postIds = postIds;
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
        holder.response.setText(responses.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialContactPhone(number.get(position));
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("post/list/" + postIds.get(position) + "/" + uid.get(position)).setValue(null);
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
        MaterialButton delete;

        public ViewHolder(View itemView) {
            super(itemView);
            response = itemView.findViewById(R.id.enlistedpeople_text_responses);
            delete = itemView.findViewById(R.id.enlistedpeople_button_delete);
            parentLayout = itemView.findViewById(R.id.parent_layout_enlisted_people);
        }

        public void setText(String s) {
        }
    }

    private void dialContactPhone(final String phoneNumber) {
        mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}

