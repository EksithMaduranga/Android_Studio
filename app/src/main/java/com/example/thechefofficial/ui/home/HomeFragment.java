package com.example.thechefofficial.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thechefofficial.HomeActivity;
import com.example.thechefofficial.R;
import com.example.thechefofficial.addPost;
import com.example.thechefofficial.data.model.User;
import com.example.thechefofficial.ui.MypostListRecyclerViewAdapter;
import com.example.thechefofficial.ui.slideshow.postViewAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class HomeFragment extends Fragment {


    private FirebaseFirestore firebaseFirestore;


    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    public static List<Recipe> postList;
    public RecyclerView recyclerview;
    public static postViewAdapter adapter;
    private TextView emptyList;
    FirebaseFirestore fStore;
    public static ListView postView = null;
    public static String uid;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         View root = inflater.inflate(R.layout.fragment_home, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        postView = (ListView)  root.findViewById(R.id.postList);

        System.out.println("User " + user);
        fStore = FirebaseFirestore.getInstance();


        uid = firebaseAuth.getUid();



//        Query query = FirebaseDatabase.getInstance().getReference()
//                    .child("recipe");
//
//        System.out.println("Query"  + query);
//
//        FirebaseRecyclerOptions<postModel> options =
//                    new FirebaseRecyclerOptions.Builder<postModel>()
//                            .setQuery(query, postModel.class)
//                            .build();
//        Log.w("Log ", "date ");
//        postViewAdapter = new postViewAdapter(options);
//        postView.setAdapter(postViewAdapter);


//        DocumentReference docRef = db.collection("recipe");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        System.out.println("DocumentSnapshot data: " + document.getData());
//                    } else {
////                        Log.d(TAG, "No such document");
//                        System.out.println("No such document");
//                    }
//                } else {
//                    System.out.println("No such document");
//                }
//            }
//        });

        postList = new ArrayList<Recipe>();

        db.collection("recipe")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println("DocumentSnapshot data: " + document.getData());

                                Recipe item = new Recipe();

                                item = document.toObject(Recipe.class);
                                System.out.println("Item" + item.postName);
                                postList.add(item);

                                System.out.println("POST Data" + postList);

                                adapter = new postViewAdapter(getActivity(), postList);
                                postView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            System.out.println("No such document");
                        }
                    }
                });



        return root;


    }

    @Override
    public void onStart() {
        super.onStart();
      //  postViewAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
       // postViewAdapter.stopListening();
    }
}