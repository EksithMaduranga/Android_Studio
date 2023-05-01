package com.example.thechefofficial.ui.favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.thechefofficial.R;
import com.example.thechefofficial.ui.home.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth;
    public static List<Recipe> postList2;
    public static FavouriteAdapter adapter;
    public String UserID;
    public static ListView favouriteList = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        fAuth = FirebaseAuth.getInstance();
        UserID = fAuth.getUid();

        favouriteList = (ListView) view.findViewById(R.id.favouriteListView);

        postList2 = new ArrayList<Recipe>();

        showFavoriteData();


        return view;
    }


    public void showFavoriteData() {

        db.collection("favorite")
                .whereEqualTo("id", UserID)
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

                                                adapter = new FavouriteAdapter(getActivity(), postList2);
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


//        db.collection("recipe")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//
////                            QuerySnapshot document = task.getResult();
////                            if (document.exists()) {
////
////                            } else {
////
////                            }
//
//
////                            for (QueryDocumentSnapshot document : task.getResult()) {
////                                System.out.println("DocumentSnapshot data: " + document.getData());
////
////                                Recipe item = new Recipe();
////
////                                item = document.toObject(Recipe.class);
////
////                                postList.add(item);
////
////                                System.out.println("POST Data" + postList);
////
////                                adapter = new FavouriteAdapter(getActivity(), postList);
////                                favouriteList.setAdapter(adapter);
////                                adapter.notifyDataSetChanged();
////                            }
//                        } else {
//                            System.out.println("No such document");
//                        }
//                    }
//                });


    }
}