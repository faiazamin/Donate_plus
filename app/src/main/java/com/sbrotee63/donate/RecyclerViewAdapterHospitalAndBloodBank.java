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

import java.util.ArrayList;

public class RecyclerViewAdapterHospitalAndBloodBank extends RecyclerView.Adapter<RecyclerViewAdapterHospitalAndBloodBank.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapterHobb";

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> numbers = new ArrayList<>();
    private ArrayList<String> locations = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterHospitalAndBloodBank(Context context, ArrayList<String> names, ArrayList<String> locations, ArrayList<String> numbers) {
        this.names = names;
        this.numbers = numbers;
        this.locations = locations;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_hospital_and_blood_bank, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Log.d(TAG, "onBindViewHolder: called.");

        holder.name.setText(names.get(position));
        holder.location.setText(locations.get(position));
        holder.cellno.setText(numbers.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // NOT YET
                Toast.makeText(mContext, "Calling " + names.get(position), Toast.LENGTH_SHORT).show();
                dialContactPhone(numbers.get(position));
            }
        });
    }

    private void setText(String s) {
    }

    @Override
    public int getItemCount() {
        return names.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView cellno;
        TextView location;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hobb_text_name);
            cellno = itemView.findViewById(R.id.hobb_text_cellno);
            location = itemView.findViewById(R.id.hobb_text_location);
            parentLayout = itemView.findViewById(R.id.parent_layout_hobb);
        }

        public void setText(String s) {
        }
    }
    private void dialContactPhone(final String phoneNumber) {
        mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}

