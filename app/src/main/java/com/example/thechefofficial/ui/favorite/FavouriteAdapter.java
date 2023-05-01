package com.example.thechefofficial.ui.favorite;

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
import static com.example.thechefofficial.ui.favorite.FavoriteFragment.adapter;
import static com.example.thechefofficial.ui.favorite.FavoriteFragment.db;
import static com.example.thechefofficial.ui.favorite.FavoriteFragment.favouriteList;
import static com.example.thechefofficial.ui.favorite.FavoriteFragment.postList2;
import static com.example.thechefofficial.ui.home.HomeFragment.postView;
import static com.example.thechefofficial.ui.home.HomeFragment.uid;

import com.example.thechefofficial.R;
import com.example.thechefofficial.ui.home.HomeFragment;
import com.example.thechefofficial.ui.home.Recipe;
import com.example.thechefofficial.ui.slideshow.postViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class FavouriteAdapter extends ArrayAdapter<Recipe> {


    private List<Recipe> itemList;
    private Activity activity;


    public FavouriteAdapter(Activity activity, List<Recipe> itemList)
    {
        super(activity, R.layout.favourite_list_item, itemList);
        this.itemList = itemList;
        this.activity = activity;

    }


    @Override
    public View getView(final int position, View view, ViewGroup parent)

    {

        final Recipe items = itemList.get(position);
        LayoutInflater inflater = activity.getLayoutInflater();

        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.favourite_list_item, null, true);


        TextView recipeFav = (TextView) rowView.findViewById(R.id.recipeNameFav);
        TextView recipeDes = (TextView) rowView.findViewById(R.id.recipeDesFav);
        CardView remove = (CardView) rowView.findViewById(R.id.btn_remove);

        recipeFav.setText(items.getPostName());
        recipeDes.setText(items.getPostdes());

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                db.collection("favorite")
                        .whereEqualTo("id", uid)
                        .whereEqualTo("imgId", items.getImgId())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        String idDelete = document.getId();
                                        db.collection("favorite").document(idDelete)
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        showFavoriteData();
                                                        Toast.makeText(getContext(), "Successfully Deleted",Toast.LENGTH_SHORT).show();

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                    }
                                                });
                                    }

                                } else {



                                }
                            }
                        });


            }
        });

        return rowView;
    }

    public void showFavoriteData() {

        postList2.clear();
        adapter.clear();

        db.collection("favorite")
                .whereEqualTo("id", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Favourite favourite = new Favourite();

                                favourite = document.toObject(Favourite.class);

                                db.collection("recipe").document(favourite.getImgId())
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {


                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {

                                                Recipe item = new Recipe();

                                                item = document.toObject(Recipe.class);

                                                postList2.add(item);

                                                System.out.println("POST Data" + postList2);

                                                adapter = new FavouriteAdapter(activity, postList2);
                                                favouriteList.setAdapter(adapter);
                                                adapter.notifyDataSetChanged();

                                            } else {

                                            }
                                        } else {

                                        }
                                    }
                                });

                            }
                        } else {

                        }
                    }
                });


    }


}
