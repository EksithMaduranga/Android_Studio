package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList diet_id, diet_title, diet_details;

    Animation translate_anim;

       CustomAdapter( Activity activity, Context context, ArrayList diet_id, ArrayList diet_title, ArrayList diet_details) {

        this. activity = activity;
        this.context = context;
        this.diet_id = diet_id;
        this.diet_title = diet_title;
        this.diet_details = diet_details;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.diet_id_txt.setText(String.valueOf(diet_id.get(position)));
        holder.diet_title_txt.setText(String.valueOf(diet_title.get(position)));
        holder.diet_details_txt.setText(String.valueOf(diet_details.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(diet_id.get(position)));
                intent.putExtra("title", String.valueOf(diet_title.get(position)));
                intent.putExtra("details", String.valueOf(diet_details.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return diet_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView diet_id_txt, diet_title_txt,diet_details_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            diet_id_txt = itemView.findViewById(R.id.diet_id_txt);
            diet_title_txt = itemView.findViewById(R.id.diet_title_txt);
            diet_details_txt = itemView.findViewById(R.id.diet_details_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animation RecyclerView
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
