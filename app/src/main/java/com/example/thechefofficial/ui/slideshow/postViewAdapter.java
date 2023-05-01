package com.example.thechefofficial.ui.slideshow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import static com.example.thechefofficial.ui.home.HomeFragment.adapter;
import static com.example.thechefofficial.ui.home.HomeFragment.db;
import static com.example.thechefofficial.ui.home.HomeFragment.postView;
import static com.example.thechefofficial.ui.home.HomeFragment.postList;
import static com.example.thechefofficial.ui.home.HomeFragment.uid;

import com.example.thechefofficial.R;
import com.example.thechefofficial.ui.favorite.Favourite;
import com.example.thechefofficial.ui.home.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

//public class postViewAdapter  extends FirebaseRecyclerAdapter<Recipe,postViewAdapter.postViewHolder> {
//
//
//    /**
//     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
//     * {@link FirebaseRecyclerOptions} for configuration options.
//     *
//     * @param options
//     */
//    public postViewAdapter(@NonNull List<Recipe> options) {
//        super(options);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull postViewHolder holder, int position, @NonNull Recipe model) {
//
//
//        holder.recipiname.setText(model.getPostName());
//        holder.des.setText(model.getPostdes());
//        holder.userName.setText(model.getPostImg());
//
//    }
//    @NonNull
//    @Override
//    public postViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item,parent,false);
//        return new postViewHolder(view);
//    }
//
//    public class postViewHolder extends RecyclerView.ViewHolder{
//
//        public TextView recipiname;
//        public TextView des;
//        public TextView userName;
//        public ImageView proPic;
//        public ImageView recipeImg;
//        public Button viewPost;
//
//        public postViewHolder(@NonNull View itemView) {
//            super(itemView);
//            recipiname = itemView.findViewById(R.id.reciname);
//            des = itemView.findViewById(R.id.addrecIDes);
//            proPic = itemView.findViewById(R.id.proPic);
//            recipeImg = itemView.findViewById(R.id.reciPict);
//            userName = itemView.findViewById(R.id.userName);
//            viewPost = itemView.findViewById(R.id.viewPostBtn);
//        }
//    }
//
//
//}


public class postViewAdapter extends ArrayAdapter<Recipe> {


    private List<Recipe> itemList;
    private Activity activity;


    public postViewAdapter(Activity activity, List<Recipe> itemList) {
        super(activity, R.layout.fragment_item, itemList);
        this.itemList = itemList;
        this.activity = activity;

    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        final Recipe items = itemList.get(position);
        LayoutInflater inflater = activity.getLayoutInflater();

        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.fragment_item, null, true);


        TextView recipiname = (TextView) rowView.findViewById(R.id.recipeName);
        TextView description = (TextView) rowView.findViewById(R.id.recipeDes);
//        ImageView proPic = (ImageView) rowView.findViewById(R.id.proPic);
//        ImageView recipeImg = (ImageView) rowView.findViewById(R.id.reciPict);
//        TextView userName = (TextView) rowView.findViewById(R.id.userName);
//        Button viewPost = (Button) rowView.findViewById(R.id.viewPostBtn);

        CardView removeRecipe = (CardView) rowView.findViewById(R.id.recRemoveBtn);
        CardView addToFav = (CardView) rowView.findViewById(R.id.addToFavBtn);

        System.out.println("ID " + items.getPostdes());

        String des = items.getPostdes();
        recipiname.setText(items.getPostName());
        description.setText(des);

        removeRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                db.collection("recipe").document(items.getImgId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                db.collection("recipe")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {

                                                    postList.clear();
                                                    adapter.clear();

                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        System.out.println("DocumentSnapshot data: " + document.getData());

                                                        Recipe item = new Recipe();

                                                        item = document.toObject(Recipe.class);

                                                        postList.add(item);

                                                        System.out.println("POST Data" + postList);

                                                        adapter = new postViewAdapter(activity, postList);
                                                        postView.setAdapter(adapter);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                } else {
                                                    System.out.println("No such document");
                                                }
                                            }
                                        });


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

            }
        });

        addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Favourite favourite = new Favourite();

                favourite.setId(uid);
                favourite.setImgId(items.getImgId());

                db.collection("favorite")
                        .whereEqualTo("id", favourite.getId())
                        .whereEqualTo("imgId", favourite.getImgId())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().isEmpty()) {

                                        String id = db.collection("favorite").document().getId();
                                        db.collection("favorite").document(id).set(favourite)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {


                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });

                                    }else {
                                        Toast.makeText(getContext(), "Already Added to Favorite",Toast.LENGTH_SHORT).show();
                                    }
                                } else {



                                }
                            }
                        });


            }
        });


        return rowView;
    }


}

