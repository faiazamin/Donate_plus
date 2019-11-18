package com.sbrotee63.donate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerViewAdapterPeopleWhoResponded extends RecyclerView.Adapter<RecyclerViewAdapterPeopleWhoResponded.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapterResponses";

    private ArrayList<String> responses = new ArrayList<>();
    private ArrayList<String> numbers = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();
    private ArrayList<String> isCalled = new ArrayList<>();
    private ArrayList<String> isEnlisted = new ArrayList<>();
    private ArrayList<String> postIds = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterPeopleWhoResponded(Context context, ArrayList<String> aResponses, ArrayList<String> aNumbers, ArrayList<String> ids, ArrayList<String> isCalled, ArrayList<String> isEnlisted, ArrayList<String> postIds) {
        responses = aResponses;
        numbers = aNumbers;
        mContext = context;
        this.ids = ids;
        this.isCalled = isCalled;
        this.isEnlisted = isEnlisted;
        this.postIds = postIds;
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

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "calling " + responses.get(position), Toast.LENGTH_SHORT).show();
                dialContactPhone(numbers.get(position));
                isCalled.set(position, "true");
                FirebaseDatabase.getInstance().getReference("post/response/" + postIds.get(position) + "/" + ids.get(position) + "/isCalled").setValue("true");
            }
        });

        holder.enlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, responses.get(position) + " Enlisted", Toast.LENGTH_SHORT).show();
                isEnlisted.set(position, "true");
                FirebaseDatabase.getInstance().getReference("post/response/" + postIds.get(position) + "/" + ids.get(position) + "/isEnlisted").setValue("true");
            }
        });

        if(isCalled.get(position).equals("true")){
            holder.parentLayout.setBackgroundColor(Color.parseColor("#2d2d2d"));
        }
        if(isEnlisted.get(position).equals("true")){
            holder.parentLayout.setBackgroundColor(Color.parseColor("#2d2d2d"));
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Profile.class);
                intent.putExtra("uid", ids.get(position));
                // TODO : change background to yellow : I tried so hard and got no far.
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
        MaterialButton call;
        MaterialButton enlist;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            response = itemView.findViewById(R.id.peoplewhoresponded_text_responses);
            call = itemView.findViewById(R.id.peoplewhoresponded_button_call);
            enlist = itemView.findViewById(R.id.peoplewhoresponded_button_enlist);
            parentLayout = itemView.findViewById(R.id.parent_layout_people_who_responded);
        }

        public void setText(String s) {
        }
    }
    private void dialContactPhone(final String phoneNumber) {
        mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}

