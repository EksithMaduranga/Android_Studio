package com.example.thechefofficial.ui;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thechefofficial.R;
import com.example.thechefofficial.ui.home.HomeFragment;
import com.example.thechefofficial.ui.home.postModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link postModel}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MypostListRecyclerViewAdapter extends RecyclerView.Adapter<MypostListRecyclerViewAdapter.ViewHolder> {

    private final List<postModel> postItemList;

    public MypostListRecyclerViewAdapter(HomeFragment homeFragment, List<postModel> items) {
        postItemList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        postModel post  = postItemList.get(position);
        String userUri = post.getUserPic();
        String userid = post.getUserId();
        String recipeUrl =  post.getRecipImg();
        holder.recipiname.setText(post.getRecipeName());
        holder.des.setText(post.getRecipeDes());
        holder.userName.setText(post.getUserName());

        Picasso.get().load(recipeUrl).into(holder.recipeImg);
        Picasso.get().load(userUri).into(holder.proPic);


    }

    @Override
    public int getItemCount() {
        return postItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public TextView recipiname;
        public TextView des;
        public TextView userName;
        public ImageView proPic;
        public ImageView recipeImg;
        public Button viewPost;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            recipiname = itemView.findViewById(R.id.reciname);
            des = itemView.findViewById(R.id.addrecIDes);
            proPic = itemView.findViewById(R.id.proPic);
            recipeImg = itemView.findViewById(R.id.reciPict);
            userName = itemView.findViewById(R.id.userName);
            viewPost = itemView.findViewById(R.id.viewPostBtn);
        }


    }
}